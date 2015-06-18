package com.medicine.app.fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.yunzhisheng.tts.offline.TTSPlayerListener;
import cn.yunzhisheng.tts.offline.basic.ITTSControl;
import cn.yunzhisheng.tts.offline.basic.TTSFactory;

import com.medicine.app.R;
import com.medicine.app.db.database.HistoryDB;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.model.HistoryBean;
import com.medicine.app.utils.CommonConst;
import com.medicine.app.utils.CommonUtils;
import com.medicine.app.utils.Config;
import com.medicine.app.utils.DateUtils;
import com.medicine.app.webservice.WebService;
import com.medicine.app.widgets.CustemUseMedicine;
import com.medicine.app.widgets.CustemUseMedicine.onSelectListener;
/**
 * 用药界面
 * @author wangyang
 *
 */

public class UseMedicineFragment extends Fragment implements  CommonConst{
	private static final String TAG = "UseMedicineFragment";
	TextToSpeech mSpeech;
	private ImageView speakTextview;
	private TextView tvSuggest;
	private List<String> data1;
	private CheckBox cbBlood;
	private ImageView ivSubmit;
	private CustemUseMedicine medicine1;
    private CustemUseMedicine medicine2;
    private CustemUseMedicine medicine3;
    private CustemUseMedicine medicine4;
    private String low;
    private String up;
    private String now;
    private String last;
    private ITTSControl mTTSPlayer;
    private UploadHistoryTask uploadHistoryTask;
    private static final int MESSAGE_UPLOAD = 1;
    private  Handler mHandler = new Handler() {
    	@Override
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case MESSAGE_UPLOAD:
				new UploadHistoryTask().execute(msg.getData().getString("lastUploadId"));
				break;

			default:
				break;
			}
    	}
    };
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usemedicine, container, false);
    }



	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        speakTextview = (ImageView)getView().findViewById(R.id.speak_textview);
        tvSuggest = (TextView)getView().findViewById(R.id.tv_suggest);
        medicine1  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine01);
        medicine2  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine02);
        medicine3  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine03);
        medicine4  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine04);
        cbBlood = (CheckBox) getView().findViewById(R.id.cb_blood);
        ivSubmit = (ImageView) getView().findViewById(R.id.iv_submit);
        uploadHistoryTask = new UploadHistoryTask();
        initData();
        initSpeechData();
        
        
    	medicine1.setData(data1);
    	medicine1.setOnSelectListener(new onSelectListener()
		{

			@Override
			public void onSelect(String text)
			{
				low = text;
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
				up = text;
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
				now = text;
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
				last = text;
				Toast.makeText(getActivity(), "选择了 " + text + " 分",
						Toast.LENGTH_SHORT).show();
			}
		});  
    	
    	
    	ivSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submit();
				
			}
		});
        
    }
    
	/**
	 * 初始化本地离线TTS
	 */
    private void initSpeechData() {
		

		// 初始化语音合成对象
		mTTSPlayer = TTSFactory.createTTSControl(getActivity(), Config.appKey);
		mTTSPlayer.setStreamType(AudioManager.STREAM_SYSTEM);
		
		// 设置回调监听
		mTTSPlayer.setTTSListener(new TTSPlayerListener() {
			@Override
			public void onPlayEnd() {
				// 播放完成回调
			}

			@Override
			public void onPlayBegin() {
				// 开始播放回调
			}

			@Override
			public void onInitFinish() {
				// 初始化成功回调
			}

			@Override
			public void onError(cn.yunzhisheng.tts.offline.common.USCError arg0) {
				// 语音合成错误回调
				toastMessage(arg0.toString());
			}

			@Override
			public void onCancel() {
				// 取消播放回调
				log_i("onCancel");
			}

			@Override
			public void onBuffer() {
				// 开始缓冲回调
				log_i("onBuffer");

			}
		});
		// 初始化合成引擎
		mTTSPlayer.init();
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
					mTTSPlayer.play("这里是北京天安门");
					
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
			}
		});
		
	}
	private void log_i(String log) {
		Log.i("demo", log);
	}
    private void toastMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 给药计算
	 */
	public void submit() {
		if(low == null) {
			low = medicine1.defaultSelectData();
		}
		if(up == null) {
			up = medicine2.defaultSelectData();
		}
		if(now == null) {
			now = medicine3.defaultSelectData();
		}
		if(last == null) {
			last = medicine4.defaultSelectData();
		}
		String isBlood = "否";
		if(cbBlood.isChecked()) {
			isBlood = "是";
		}
		try {
			boolean isInsert = HistoryDB.getInstance(getActivity()).insert(new HistoryBean(DateUtils.getNowTime(), low, up, now, last, isBlood, ""));
			if(CommonUtils.isNetworkAvailable(getActivity())) {
				//TODO 1.请求 Get_HFL_history_IDindex，获取服务器端最后一次上传数据的LastID
				//TODO 2.查询本地History表中ID>LastID的数据集合List<History>
				//TODO 3.逐一上传List中的数据,一条数据上传失败，等待5s重新上传这条数据，然后再上传剩下的数据。 Inter_HFL_history
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... params) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("ID", InsertUserDB.getInstance(getActivity()).getColumnValue("ID"));
						map.put("IDIndex", HistoryDB.getInstance(getActivity()).getLastDataId());
						SoapObject soapObject = WebService.common(SOAP_HFL_HISTORY_IDINDEX, METHOD_HFL_HISTORY_IDINDEX, map, NAME_SPACE, END_POINT_SALE);
						String result = soapObject.getProperty(0).toString();
						Log.d(TAG, "Get_HFL_history_IDindex return:"+result);
						return result;
					}
					
					@Override
					protected void onPostExecute(String result) {
						Message msg = new Message();
						msg.what = MESSAGE_UPLOAD;
						Bundle bundle = new Bundle();
						bundle.putString("lastUploadId", result);
						msg.setData(bundle);
						mHandler.sendMessage(msg);
					}
				}.execute();
			}
		} catch (Exception e) {
			Log.e("To calculate error", e.getMessage());
		}
	}
	
	/**
	 * 上传计算数据
	 * @author WangLin
	 *
	 */
	class UploadHistoryTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				String ID = InsertUserDB.getInstance(getActivity()).getColumnValue("ID");
				List<HistoryBean> list = HistoryDB.getInstance(getActivity()).selectAfterById(Integer.parseInt(params[0]));
				if(list != null && list.size() > 0) {
					SoapObject soapObject;
					for (HistoryBean historyBean : list) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("ID", ID);
						map.put(HistoryBean.ID, historyBean.getId()+"");
						map.put(HistoryBean.TIME, historyBean.getTime());
						map.put(HistoryBean.INR_LOW, historyBean.getLow());
						map.put(HistoryBean.INR_UP, historyBean.getUp());
						map.put(HistoryBean.NOW_INR, historyBean.getNow());
						map.put(HistoryBean.LAST_HFL, historyBean.getLast());
						map.put(HistoryBean.BLOOD, historyBean.getBlood());
						map.put(HistoryBean.RECORD, "1");
						boolean result = false;
						while (!result) {
							soapObject = WebService.common(SOAP_HFL_HISTORY, METHOD_HFL_HISTORY, map, NAME_SPACE, END_POINT_SALE);
							result = (Boolean) soapObject.getProperty(0);
						}
					}
				}
			} catch (Exception e) {
				Log.e(TAG, "UploadHistoryTask", e);
			}
			
			return null;
		}
		
	}
}
