package com.medicine.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.medicine.app.R;
import com.medicine.app.adapter.HistoryListAdapter;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.model.HistoryItemBean;
import com.medicine.app.utils.CommonConst;
import com.medicine.app.webservice.WebService;
/**
 * 知识界面
 * @author wangyang
 *
 */

public class KnowledgeFragment extends Fragment implements CommonConst {
    private ListView listKnowledge;
    private HistoryListAdapter historyAdapter;
    private List<HistoryItemBean> listData;
	private LinearLayout llKonwdetails;
	private Button konwdetailsBack;
	private Button btnSync; // 知识同步按钮
	private SyncDataTask syncDataTask;
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
    	btnSync = (Button) getView().findViewById(R.id.btn_sync);
		 listKnowledge = (ListView)getView().findViewById(R.id.list_knowledge); 
		 llKonwdetails = (LinearLayout)getView().findViewById(R.id.lin_konwdetails);
		 konwdetailsBack = (Button)getView().findViewById(R.id.konwdetails_back);
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
    	listData = new ArrayList<HistoryItemBean>();
		for (int i = 1; i < 11; i++) {
			listData.add(new HistoryItemBean(i + "", "本系统使用说明",
					"心血管疾病如何饮食 心血管疾病严重危害人们的健康，被医学界称为健康的第一杀手。我国心血管疾病的发病率有逐年增高的趋势。目前，全社会将..."));
		}

		
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
				listKnowledge.setVisibility(View.GONE);
				llKonwdetails.setVisibility(View.VISIBLE);
				
			}
		});
	}

	class SyncDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ID", InsertUserDB.getInstance(getActivity()).getColumnValue("ID"));
			SoapObject soapObject = WebService.common(SOAP_TB_KNO_LIST, METHOD_TB_KNO_LIST, map, NAME_SPACE, END_POINT_SALE);
			String result = soapObject.getProperty(0).toString();
		    Log.d("SyncDataTask", result+"");
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null) {
				
			}
		}
	}
}
