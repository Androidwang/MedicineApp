package com.medicine.app.fragment;
import java.math.BigDecimal;
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
import android.widget.ImageButton;
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
import com.medicine.app.utils.PreferencesUtils;
import com.medicine.app.webservice.WebService;
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
	private CheckBox cbBlood;
	private ImageView ivSubmit;
    private String low;
    private String up;
    private String now;
    private String last;
    private ITTSControl mTTSPlayer;
    private static final int MESSAGE_UPLOAD = 1;
    private String advice;
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
	private ImageButton btUsemedicineAdd1;
	private ImageButton btUsemedicineAdd2;
	private ImageButton btUsemedicineAdd3;
	private ImageButton btUsemedicineAdd4;
	private ImageButton btUsemedicineMin1;
	private ImageButton btUsemedicineMin2;
	private ImageButton btUsemedicineMin3;
	private ImageButton btUsemedicineMin4;
	private TextView tvUsemedicineNum1;
	private TextView tvUsemedicineNum2;
	private TextView tvUsemedicineNum3;
	private TextView tvUsemedicineNum4;
	
	private double sum1 = 0.0;
	private double sum2 = 0.0;
	private double sum3 = 0.0;
	private double sum4 = 0.0;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usemedicine, container, false);
    }



	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btUsemedicineAdd1 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_add1);
        btUsemedicineAdd2 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_add2);
        btUsemedicineAdd3 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_add3);
        btUsemedicineAdd4 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_add4);
        btUsemedicineMin1 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_min1);
        btUsemedicineMin2 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_min2);
        btUsemedicineMin3 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_min3);
        btUsemedicineMin4 = (ImageButton)getView().findViewById(R.id.bt_usemedicine_min4);
        
        tvUsemedicineNum1 = (TextView)getView().findViewById(R.id.tv_usemedicine_num1);
        tvUsemedicineNum2 = (TextView)getView().findViewById(R.id.tv_usemedicine_num2);
        tvUsemedicineNum3 = (TextView)getView().findViewById(R.id.tv_usemedicine_num3);
        tvUsemedicineNum4 = (TextView)getView().findViewById(R.id.tv_usemedicine_num4);
        
        tvUsemedicineNum1.setText(sum1+"");
        tvUsemedicineNum2.setText(sum2+"");
        tvUsemedicineNum3.setText(sum3+"");
        tvUsemedicineNum4.setText(sum4+"");
        
        
        speakTextview = (ImageView)getView().findViewById(R.id.speak_textview);
        tvSuggest = (TextView)getView().findViewById(R.id.tv_suggest);
        cbBlood = (CheckBox) getView().findViewById(R.id.cb_blood);
        ivSubmit = (ImageView) getView().findViewById(R.id.iv_submit);
        
        initData();
        initSpeechData();
        
        /**
         * 
         * 加的处理
         */
        btUsemedicineAdd1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.0<=sum1 && sum1 < 10) {
					sum1 = sum1 + 0.1;
					BigDecimal bd = new BigDecimal(sum1);
					sum1 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					tvUsemedicineNum1.setText(sum1+"");
					low = sum1 +"";
					
				}
			}
		});
        btUsemedicineAdd2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.0<= sum2 && sum2 < 10) {
					sum2 = sum2 + 0.1;
					BigDecimal bd = new BigDecimal(sum2);
					sum2 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					tvUsemedicineNum2.setText(sum2 +"");
					up = sum2+"";
					
				}
			}
		});
        btUsemedicineAdd3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.0<=sum3 && sum3 < 10) {
					sum3 = sum3 + 0.1;
					BigDecimal bd = new BigDecimal(sum3);
					sum3 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					tvUsemedicineNum3.setText(sum3 + "");
					now = sum3 + "";
					
				}
			}
		});
        btUsemedicineAdd4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.0 <= sum4 && sum4 < 10) {
					if (PreferencesUtils.getHFLsrc(getActivity()).equals("3mg/片")) {
						sum4 = sum4 + 0.75;
						BigDecimal bd = new BigDecimal(sum4);
						sum4 = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					}else {
						sum4 = sum4 + 0.5;
						BigDecimal bd = new BigDecimal(sum4);
						sum4 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					tvUsemedicineNum4.setText(sum4+"");
					last = sum4+"";
					
				}
			}
		});
        
        /**
         * 
         * 减的处理
         */
        btUsemedicineMin1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.0 < sum1 && sum1 < 10 && sum1 != -0.1) {
					sum1 = sum1 - 0.1;
					BigDecimal bd = new BigDecimal(sum1);
					sum1 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					tvUsemedicineNum1.setText(sum1+"");
					low = sum1 +"";
					
				}
			}
		});
        
        btUsemedicineMin2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (0.0 < sum2 && sum2 < 10) {
					sum2 = sum2 - 0.1;
					BigDecimal bd = new BigDecimal(sum2);
					sum2 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					tvUsemedicineNum2.setText(sum2+"");
					up = sum2+"";
				}
				
				
				
			}
		});
        btUsemedicineMin3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.0 < sum3 && sum3 < 10) {
					sum3 = sum3 - 0.1;
					BigDecimal bd = new BigDecimal(sum3);
					sum3 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					tvUsemedicineNum3.setText(sum3 +"");
					now = sum3 +"";
					
				}
			}
		});
        
        btUsemedicineMin4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (0.00 <sum4 && sum4 < 10) {
					if (PreferencesUtils.getHFLsrc(getActivity()).equals("3mg/片")) {
						sum4 = sum4 - 0.75;	
						BigDecimal bd = new BigDecimal(sum4);
						sum4 = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					}else {
						sum4 = sum4 - 0.5;
						BigDecimal bd = new BigDecimal(sum4);
						sum4 = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					tvUsemedicineNum4.setText(sum4+"");
					last = sum4 + "";
					
				}
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
		 speakTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {  
					mTTSPlayer.play(tvSuggest.getText().toString());
					
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
		ivSubmit.setImageResource(R.drawable.usemedicine_07);
		ivSubmit.setEnabled(false);
		if(low == null) {
			low = tvUsemedicineNum1.getText().toString();
		}
		if(up == null) {
			up = tvUsemedicineNum2.getText().toString();
		}
		if(now == null) {
			now = tvUsemedicineNum3.getText().toString();
		}
		if(last == null) {
			last = tvUsemedicineNum4.getText().toString();
		}
		String isBlood = "否";
		boolean blood = false;
		if(cbBlood.isChecked()) {
			isBlood = "是";
			blood = true;
		}
		advice = getUseAdvice(Float.parseFloat(low), Float.parseFloat(up), Float.parseFloat(now), Float.parseFloat(last), blood);
		try {
			boolean isInsert = HistoryDB.getInstance(getActivity()).insert(new HistoryBean(DateUtils.getNowTime(), low, up, now, last, isBlood, advice));
			tvSuggest.setText(advice);
			mTTSPlayer.play("目标INR值"+low+"-"+up+",本次检测INR值"+now+",上次服用量"+last+"毫克,给药建议："+advice);
			if(CommonUtils.isNetworkAvailable(getActivity())) {
				//TODO 1.请求 Get_HFL_history_IDindex，获取服务器端最后一次上传数据的LastID
				//TODO 2.查询本地History表中ID>LastID的数据集合List<History>
				//TODO 3.逐一上传List中的数据,一条数据上传失败，等待5s重新上传这条数据，然后再上传剩下的数据。 Inter_HFL_history
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... params) {
						String result = null;
						HashMap<String, Object> map = new HashMap<String, Object>();
						try {
							map.put("ID", InsertUserDB.getInstance(getActivity()).getColumnValue("ID"));
							map.put("IDIndex", HistoryDB.getInstance(getActivity()).getLastDataId());
							SoapObject soapObject = WebService.common(SOAP_HFL_HISTORY_IDINDEX, METHOD_HFL_HISTORY_IDINDEX, map, NAME_SPACE, END_POINT_SALE);
							result = soapObject.getProperty(0).toString();
							Log.d(TAG, "Get_HFL_history_IDindex return:"+result);
						} catch (Exception e) {
							Log.e(TAG, "Get_HFL_history_IDindex doInBackground error", e);
						}
						
						return result;
					}
					
					@Override
					protected void onPostExecute(String result) {
						try {
							Message msg = new Message();
							msg.what = MESSAGE_UPLOAD;
							Bundle bundle = new Bundle();
							bundle.putString("lastUploadId", result);
							msg.setData(bundle);
							mHandler.sendMessage(msg);
						} catch (Exception e) {
							Log.e(TAG, "Get_HFL_history_IDindex onPostExecute error", e);
						}
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
		
		@Override
		protected void onPostExecute(Void result) {
			try {
				ivSubmit.setEnabled(true);
				ivSubmit.setImageResource(R.drawable.usemedicine_06);
			} catch (Exception e) {
				Log.e(TAG, "UploadHistoryTask onPostExecute error", e);
			}
		}
		
	}
	
	/**
	 * 根据参数计算给药建议
	 * @param low
	 * @param up
	 * @param now
	 * @param last
	 * @param isBlood
	 * @return 给药建议
	 */
	public String getUseAdvice(float low, float up, float now, float last, boolean isBlood) {
		int count = HistoryDB.getInstance(getActivity()).getCount();
		String advice = "";
		String src = InsertUserDB.getInstance(getActivity()).getColumnValue("HFLsrc");
		if(last > 6) {
			advice = "请保持当前服用量，并尽快咨询医生";
			return advice;
		}
		if(now <= (low - 0.5)) {
			String dose = "请保持当前服用量";
			if (2 == count) { //第3次使用
				if("2mg".equals(src)) {
					float s = (float) (last + 0.5);
					int n = (int) (s/2);
					dose = "建议服用剂量为"+s+"毫克，即"+n+"片";
				} else {
					float s = (float) (last + 0.75);
					int n = (int) (s/3);
					dose = "建议服用剂量为"+s+"毫克，即"+n+"片";
				}
			} 
			advice = dose+"，并在3天之后复查";
			return advice;
		}else if((low-0.5) < now && now <= low) {
			String time = "";
			if(0<= count && count <= 1) { //第1~2次使用
				time = "3天";
			} else if (2 <= count && count <= 5) { //第3~6次使用
				time = "1周";
			} else { //7次以上
				time = "1个月";
			}
			advice = "请保持当前服用量，并在"+time+"之后复查";
			return advice;
		}else if(low < now && now < (up + 0.5)) {
			String dose = "请保持当前服用量";
			if (2 == count) { //第3次使用
				if("2mg".equals(src)) {
					float s = (float) (last - 0.5);
					int n = (int) (s/2);
					dose = "建议服用剂量为"+s+"毫克，即"+n+"片";
				} else {
					float s = (float) (last - 0.75);
					int n = (int) (s/3);
					dose = "建议服用剂量为"+s+"毫克，即"+n+"片";
				}
			} 
			advice = dose+"，并在3天之后复查";
			return advice;
		}else if((up + 0.5) <= now && now <= 5) {
			String dose = "请保持当前服用量";
			if (2 == count) { //第3次使用
				if("2mg".equals(src)) {
					float s = (float) (last - 0.5);
					int n = (int) (s/2);
					dose = "服用剂量调整为"+s+"毫克，即"+n+"片";
				} else {
					float s = (float) (last - 0.75);
					int n = (int) (s/3);
					dose = "服用剂量调整为"+s+"毫克，即"+n+"片";
				}
			} 
			advice = "停药1天，"+dose+"，并在3天之后复查";
			return advice;
		}else if(now > 5 || isBlood) {
			advice = "请立即停药，并尽快咨询医生";
			return advice;
		}else{
			advice = "请保持当前服用量，并尽快咨询医生";
			return advice;
		}
	}

}
