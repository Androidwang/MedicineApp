package com.medicine.app.fragment;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
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

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.medicine.app.R;
import com.medicine.app.db.database.HistoryDB;
import com.medicine.app.model.HistoryBean;
import com.medicine.app.utils.CommonUtils;
import com.medicine.app.utils.DateUtils;
import com.medicine.app.widgets.CustemUseMedicine;
import com.medicine.app.widgets.CustemUseMedicine.onSelectListener;
/**
 * 用药界面
 * @author wangyang
 *
 */
public class UseMedicineFragment extends Fragment implements   SpeechSynthesizerListener{
	TextToSpeech mSpeech;
	private ImageView speakTextview;
	private TextView tvSuggest;
	private List<String> data1;
	private SpeechSynthesizer speechSynthesizer;
	private CheckBox cbBlood;
	private ImageView ivSubmit;
	private CustemUseMedicine medicine1;
    private CustemUseMedicine medicine2;
    private CustemUseMedicine medicine3;
    private CustemUseMedicine medicine4;
	  /** 指定license路径，需要保证该路径的可读写权限 */
    private static final String LICENCE_FILE_NAME = Environment.getExternalStorageDirectory()
            + "/tts/baidu_tts_licence.dat";


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usemedicine, container, false);
    }



	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.loadLibrary("bd_etts");
	    System.loadLibrary("bds");
        speakTextview = (ImageView)getView().findViewById(R.id.speak_textview);
        tvSuggest = (TextView)getView().findViewById(R.id.tv_suggest);
        medicine1  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine01);
        medicine2  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine02);
        medicine3  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine03);
        medicine4  = (CustemUseMedicine)getView().findViewById(R.id.cu_medicine04);
        cbBlood = (CheckBox) getView().findViewById(R.id.cb_blood);
        ivSubmit = (ImageView) getView().findViewById(R.id.iv_submit);
        initData();
        initSpeechData();
        
        
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
    	
    	
    	ivSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submit();
				
			}
		});
        
    }
    
    /**
     * 初始化百度语音参数
     */
    private void initSpeechData() {
    	  speechSynthesizer =SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, getActivity(),
                  "holder", this);
          // 请替换为开放平台上申请的apikey和secretkey
          speechSynthesizer.setApiKey("8MAxI5o7VjKSZOKeBzS4XtxO", "Ge5GXVdGQpaxOmLzc8fOM8309ATCz9Ha");
          // 设置授权文件路径
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, LICENCE_FILE_NAME);
          // TTS所需的资源文件，可以放在任意可读目录，可以任意改名
          String ttsTextModelFilePath =
        		  getActivity().getApplicationInfo().dataDir + "/lib/libbd_etts_text.dat.so";
          String ttsSpeechModelFilePath =
        		  getActivity().getApplicationInfo().dataDir + "/lib/libbd_etts_speech_female.dat.so";
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, ttsTextModelFilePath);
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, ttsSpeechModelFilePath);
       
          //getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
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
					speechSynthesizer.speak("你好");
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
			}
		});
		
	}

	@Override
	public void onBufferProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(SpeechSynthesizer arg0, SpeechError arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechPause(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechResume(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechStart(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartWorking(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSynthesizeFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}
   
	/**
	 * 给药计算
	 */
	public void submit() {
		String isBlood = "否";
		if(cbBlood.isChecked()) {
			isBlood = "是";
		}
		try {
			boolean isInsert = HistoryDB.getInstance(getActivity()).insert(new HistoryBean(DateUtils.getNowTime(), "low", "up", "now", "last", isBlood, ""));
			if(CommonUtils.isNetworkAvailable(getActivity())) {
				//TODO 1.请求 Get_HFL_history_IDindex，获取服务器端最后一次上传数据的LastID
				//TODO 2.查询本地History表中ID>LastID的数据集合List<History>
				//TODO 3.逐一上传List中的数据,一条数据上传失败，等待5s重新上传这条数据，然后再上传剩下的数据。 Inter_HFL_history
			}
		} catch (Exception e) {
			Log.e("To calculate error", e.getMessage());
		}
	}
}
