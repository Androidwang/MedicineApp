package com.medicine.app.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.medicine.app.R;
import com.medicine.app.adapter.HistoryListAdapter;
import com.medicine.app.db.database.HistoryDB;
import com.medicine.app.model.HistoryBean;
import com.medicine.app.model.HistoryItemBean;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 历史界面
 * 
 * @author wangyang
 * 
 */
public class HistoryFragment extends Fragment {
	private static final String TAG = "HistoryFragment";
	private PullToRefreshListView listHistory;
	private List<HistoryItemBean> listData;
	private HistoryListAdapter historyAdapter;
	private LinearLayout llHistory;
	private TextView tvNum;
	private TextView detailTitle;
	private TextView detailContent;
	private Button historydetailsBack;
	private int page = 0;
	private int count = 0;
	private int currentPage = 0;
	private static final int LIMIT = 4;
	private RefreshDataTask refreshDataTask;
	private int itemNum = 1;
	private static final int MESSAGE_LOAD = 2;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_LOAD:
				new RefreshDataTask().execute();
				break;

			default:
				break;
			}
		}
	};
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_history, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listHistory = (PullToRefreshListView) getView().findViewById(R.id.list_history);
		tvNum = (TextView) getView().findViewById(R.id.tv_num);
		llHistory = (LinearLayout) getView().findViewById(R.id.lin_history);
		historydetailsBack = (Button) getView().findViewById(R.id.historydetails_back);
		detailContent = (TextView) getView().findViewById(R.id.historydetails_content);
		detailTitle = (TextView) getView().findViewById(R.id.historydetails_title);
		historydetailsBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listHistory.setVisibility(View.VISIBLE);
				llHistory.setVisibility(View.GONE);
			}
		});
		refreshDataTask = new RefreshDataTask();
		initData();
		setData();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		currentPage = 0;
		try {
			count = HistoryDB.getInstance(getActivity()).getCount();
			if(count != 0) {
				page = (int) Math.ceil((double)count/(double)LIMIT);
			}
		} catch (Exception e) {
			Log.e(TAG, "db getCount error", e);
		}
		Log.d(TAG, "page num:"+page);
		tvNum.setText("共有记录"+count+"条");
		listData = new ArrayList<HistoryItemBean>();
		refreshDataTask = new RefreshDataTask();
		refreshDataTask.execute();
	}

	/**
	 * 设置数据显示
	 */
	private void setData() {
		historyAdapter = new HistoryListAdapter(getActivity(), listData);
		listHistory.setAdapter(historyAdapter);
		listHistory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HistoryItemBean data = listData.get(--position);
				detailTitle.setText(data.getHistoryTime());
				detailContent.setText(data.getHistoryContent());
				listHistory.setVisibility(View.GONE);
				llHistory.setVisibility(View.VISIBLE);

			}
		});
		listHistory.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				//下拉
				Log.d(TAG, "listView onPullDownToRefresh");
				new AsyncTask<Void, Void, Void>(){

					@Override
					protected Void doInBackground(Void... params) {
						currentPage = 0;
						try {
							count = HistoryDB.getInstance(getActivity()).getCount();
							if(count != 0) {
								page = (int) Math.ceil((double)count/(double)LIMIT);
							}
						} catch (Exception e) {
							Log.e(TAG, "db getCount error", e);
						}
						listData.clear();
						itemNum = 1;
						return null;
					}
					
					protected void onPostExecute(Void result) {
						tvNum.setText("共有记录"+count+"条");
						handler.sendEmptyMessage(MESSAGE_LOAD);
					};
				}.execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				//上拉
				Log.d(TAG, "listView onPullUpToRefresh");
				if(page >= 1 && currentPage <= page) {
					new RefreshDataTask().execute();
				}else{
					listHistory.onRefreshComplete();
				}
				
			}
		});
	}
	class RefreshDataTask extends AsyncTask<Void, Void, Boolean> {
		List<HistoryBean> list = new ArrayList<HistoryBean>();
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				if(page >= 1 && currentPage < page) {
					list = HistoryDB.getInstance(getActivity()).getListByLimit(currentPage, LIMIT);
					currentPage++;
					if (list != null && list.size() > 0) {
						return true;
					}else{
						return false;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				Log.e(TAG, "RefreshDataTask doInBackground error", e);
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			listHistory.onRefreshComplete();
			if(result) {
				for (HistoryBean historyBean : list) {
					String str = "目标INR值"+historyBean.getLow()+"-"+historyBean.getUp()+"，本次检测INR值"+historyBean.getNow()+"，检测前华法林用量"+historyBean.getLast()+"mg。";
					listData.add(new HistoryItemBean(itemNum+"", historyBean.getTime(), historyBean.getRecord(), str));
					++itemNum;
				}
				historyAdapter.notifyDataSetChanged();
			}
		}
		
	}
}
