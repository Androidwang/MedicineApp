package com.medicine.app.fragment;

import java.util.ArrayList;
import java.util.List;

import com.medicine.app.R;
import com.medicine.app.adapter.HistoryListAdapter;
import com.medicine.app.model.HistoryItemBean;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 历史界面
 * 
 * @author wangyang
 *
 */
public class HistoryFragment extends Fragment {
	private ListView listHistory;
	private List<HistoryItemBean> listData;
	private HistoryListAdapter historyAdapter;
	private LinearLayout llHistory;
	private Button historydetailsBack;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_history, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listHistory = (ListView) getView().findViewById(R.id.list_history);
		llHistory = (LinearLayout)getView().findViewById(R.id.lin_history);
		historydetailsBack = (Button)getView().findViewById(R.id.historydetails_back);
		historydetailsBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listHistory.setVisibility(View.VISIBLE);
				llHistory.setVisibility(View.GONE);
			}
		});
		initData();
		setData();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		listData = new ArrayList<HistoryItemBean>();
		for (int i = 1; i < 11; i++) {
			listData.add(new HistoryItemBean(i + "", "2013年5月21日12店23分23秒",
					"目标INR值2-2.5，本次检测INR值1.5，检测前华法林用量3mg, 给药建议：调整计算－1mg,请服用2mg并于三天后复查INR。"));
		}

	}

	/**
	 * 设置数据显示
	 */
	private void setData() {
		historyAdapter = new HistoryListAdapter(getActivity(), listData);
		listHistory.setAdapter(historyAdapter);
		listHistory.setOnItemClickListener(new OnItemClickListener() {
			@Override
	   public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
				listHistory.setVisibility(View.GONE);
				llHistory.setVisibility(View.VISIBLE);
				
			}
		});
	}

}
