package com.medicine.app.fragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medicine.app.HomeActivity;
import com.medicine.app.R;
import com.medicine.app.widgets.CustemUseMedicine;
import com.medicine.app.widgets.CustemUseMedicine.onSelectListener;
/**
 * 用药界面
 * @author wangyang
 *
 */
public class UseMedicineFragment extends Fragment{
	TextToSpeech mSpeech;
	private ImageView speakTextview;
	private TextView tvSuggest;
	private List<String> data1;
	
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
        return inflater.inflate(R.layout.fragment_usemedicine, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        speakTextview = (ImageView)getView().findViewById(R.id.speak_textview);
        tvSuggest = (TextView)getView().findViewById(R.id.tv_suggest);
        CustemUseMedicine medicine1  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine01);
        CustemUseMedicine medicine2  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine02);
        CustemUseMedicine medicine3  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine03);
        CustemUseMedicine medicine4  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine04);
        initData();
    	medicine1.setData(data1);
    	medicine1.setOnSelectListener(new onSelectListener()
		{

			@Override
			public void onSelect(String text)
			{
				Toast.makeText(getActivity(), "选择了 " + text + " 分",
						Toast.LENGTH_SHORT).show();
			}
		});  
        
    	medicine2.setData(data1);
    	medicine2.setOnSelectListener(new onSelectListener()
		{

			@Override
			public void onSelect(String text)
			{
				Toast.makeText(getActivity(), "选择了 " + text + " 分",
						Toast.LENGTH_SHORT).show();
			}
		});  
        
    	medicine3.setData(data1);
    	medicine3.setOnSelectListener(new onSelectListener()
		{

			@Override
			public void onSelect(String text)
			{
				Toast.makeText(getActivity(), "选择了 " + text + " 分",
						Toast.LENGTH_SHORT).show();
			}
		});  
    	medicine4.setData(data1);
    	medicine4.setOnSelectListener(new onSelectListener()
		{

			@Override
			public void onSelect(String text)
			{
				Toast.makeText(getActivity(), "选择了 " + text + " 分",
						Toast.LENGTH_SHORT).show();
			}
		});  
    	
        
    }
    /**
     * 
     * 初始化数据
     * 
     * 
     */
    private void initData() {
    	String[] medicalHistorySpinerData = getResources().getStringArray(R.array.date1);
    	data1 = new ArrayList<String>();
    	for (int i = 0; i < medicalHistorySpinerData.length; i++) {
    		data1.add(medicalHistorySpinerData[i]);
		}
		
		 speakTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
			}
		});
		
	}
   
}
