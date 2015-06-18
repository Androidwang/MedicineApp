package com.medicine.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.medicine.app.R;
import com.medicine.app.adapter.HistoryListAdapter;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.db.database.KnowledgeDB;
import com.medicine.app.model.HistoryItemBean;
import com.medicine.app.model.KnowledgeBean;
import com.medicine.app.utils.CommonConst;
import com.medicine.app.webservice.WebService;
/**
 * 知识界面
 * @author wangyang
 *
 */

public class KnowledgeFragment extends Fragment implements CommonConst {
	private static final String TAG = "KnowledgeFragment";
    private PullToRefreshListView listKnowledge;
    private HistoryListAdapter historyAdapter;
    private List<HistoryItemBean> listData;
	private LinearLayout llKonwdetails;
	private Button konwdetailsBack;
	private Button btnSync; // 知识同步按钮
	private SyncDataTask syncDataTask;
	private TextView tvNum;
	private TextView detailTitle;
	private TextView detailContent;
	private int page = 0;
	private int count = 0;
	private int currentPage = 0;
	private static final int LIMIT = 4;
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
		};
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
        return inflater.inflate(R.layout.fragment_knowledge, container, false);
       
        
    }
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 initView();
		 initData();
		 setData();
		
    }
    private void initView() {
    	tvNum = (TextView) getView().findViewById(R.id.tv_list_count);
    	btnSync = (Button) getView().findViewById(R.id.btn_sync);
		 listKnowledge = (PullToRefreshListView)getView().findViewById(R.id.list_knowledge); 
		 llKonwdetails = (LinearLayout)getView().findViewById(R.id.lin_konwdetails);
		 konwdetailsBack = (Button)getView().findViewById(R.id.konwdetails_back);
		 detailContent = (TextView) getView().findViewById(R.id.konwdetails_content);
		 detailTitle = (TextView) getView().findViewById(R.id.konwdetails_title);
		 konwdetailsBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listKnowledge.setVisibility(View.VISIBLE);
				llKonwdetails.setVisibility(View.GONE);
			}
		});
		 btnSync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				syncDataTask = new SyncDataTask();
				syncDataTask.execute();
			}
		});
		
	}

	/**
     * 初始化数据
     */
    private void initData() {
    	currentPage = 0;
    	try {
			count = KnowledgeDB.getInstance(getActivity()).getCount();
			if(count != 0) {
				page = (int) Math.ceil((double)count/(double)LIMIT);
			}
		} catch (Exception e) {
			Log.e(TAG, "db getCount error", e);
		}
    	tvNum.setText("共有记录"+count+"条");
    	listData = new ArrayList<HistoryItemBean>();
		new RefreshDataTask().execute();
	}
    /**
     * 设置数据显示
     */
	private void setData() {
		historyAdapter = new HistoryListAdapter(getActivity(), listData);
		listKnowledge.setAdapter(historyAdapter);
		listKnowledge.setOnItemClickListener(new OnItemClickListener() {
			@Override
	   public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
				HistoryItemBean data = listData.get(--position);
				detailContent.setText(data.getHistoryContent());
				detailTitle.setText(data.getHistoryTime());
				listKnowledge.setVisibility(View.GONE);
				llKonwdetails.setVisibility(View.VISIBLE);
				
			}
		});
		listKnowledge.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				new AsyncTask<Void, Void, Void>(){

					@Override
					protected Void doInBackground(Void... params) {
						currentPage = 0;
						try {
							count = KnowledgeDB.getInstance(getActivity()).getCount();
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
				if(page >= 1 && currentPage <= page) {
					new RefreshDataTask().execute();
				}else{
					listKnowledge.onRefreshComplete();
				}
			}
		});
	}
	
	/**
	 * 同步数据
	 * @author WangLin
	 *
	 */
	class SyncDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ID", InsertUserDB.getInstance(getActivity()).getColumnValue("ID"));
			map.put("KNO_Start_ID", KnowledgeDB.getInstance(getActivity()).getLastDataId());
			map.put("Count", "20");
			SoapObject soapObject = WebService.common(SOAP_TB_KNO_INFO, METHOD_TB_KNO_INFO, map, NAME_SPACE, END_POINT_SALE);
			String result = soapObject.getProperty(0).toString();
		    Log.d("SyncDataTask", result+"");
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONArray jsonArray = jsonObject.getJSONArray("KNO");
					if(jsonArray!=null && jsonArray.length() > 0) {
						List<KnowledgeBean> list = new ArrayList<KnowledgeBean>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject data = jsonArray.getJSONObject(i);
							list.add(new KnowledgeBean(data.getString("ID"), 
									data.getString(KnowledgeBean.DATA_TIME), 
									data.getString(KnowledgeBean.HEADSTR), 
									data.getString(KnowledgeBean.CONTENT_M), 
									data.getString("ContentC")));
						}
						if(list != null && list.size() > 0) {
							KnowledgeDB.getInstance(getActivity()).insert(list);
						}
					}
				} catch (JSONException e) {
					Log.e(TAG, "SyncDataTask onPostExecute error", e);
				}
			}
		}
	}
	
	class RefreshDataTask extends AsyncTask<Void, Void, Boolean> {
		List<KnowledgeBean> list = new ArrayList<KnowledgeBean>();
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				if(page >= 1 && currentPage < page) {
					list = KnowledgeDB.getInstance(getActivity()).getListByLimit(currentPage, LIMIT);
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
			listKnowledge.onRefreshComplete();
			if(result) {
				for (KnowledgeBean knowledgeBean : list) {
					listData.add(new HistoryItemBean(itemNum+"", knowledgeBean.getHeadStr(), knowledgeBean.getShortConent(), knowledgeBean.getContent()));
					++itemNum;
				}
				historyAdapter.notifyDataSetChanged();
			}
		}
		
	}
}
