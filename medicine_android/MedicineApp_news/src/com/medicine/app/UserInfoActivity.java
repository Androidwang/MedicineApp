package com.medicine.app;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medicine.app.adapter.AbstractSpinerAdapter.IOnItemSelectListener;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.model.UserInfoBean;
import com.medicine.app.utils.CommonConst;
import com.medicine.app.utils.CommonUtils;
import com.medicine.app.utils.PreferencesUtils;
import com.medicine.app.webservice.WebService;
import com.medicine.app.widgets.SpinerPopWindow;
/**
 * 填写用户信息 UserInfoActivity
 * 
 * @author wangyang
 *
 */
public class UserInfoActivity extends Activity implements CommonConst {
	
	private ArrayList<String> dataYear = new ArrayList<String>();
	private ArrayList<String> dataMonth = new ArrayList<String>();
	private SpinerPopWindow SpinerCountry1 = null;
	private SpinerPopWindow SpinerCountry2 = null;
	private SpinerPopWindow SpinerCountry3 = null;
	private SpinerPopWindow SpinerCountry4 = null;
	private SpinerPopWindow SpinerCountry5 = null;
	private SpinerPopWindow SpinerCountry6 = null;
	private SpinerPopWindow SpinerCountry7 = null;
	private String[] medicalHistorySpinerData; //病史数据
	private String[] receiveTreatmentSpinerData; //是否接受治疗
	private String[] takeMedicamentSpinerData; //是否接受治疗
	private String[] inrCheckSpinerData;
	private String[] year;
	private String[] month;
	private Button btNext;
	private TextView spYear;
	private TextView spMonth;
	private EditText edName;
	private EditText edPhonenum;
	private EditText edAddress;
	private TextView edLlhistory;
	private RelativeLayout rlLlhistory;
	private RelativeLayout sp_year;
	private RelativeLayout sp_month;
	private RelativeLayout rlreceivTreatment;
	private TextView edReceiveTreatment;
	private RelativeLayout rltakeMedicament;
	private TextView edtakeMedicament;
	private RelativeLayout rlmedicamentNumber;
	private TextView edmedicamentNumber;
	private RelativeLayout rlinrCheck;
	private TextView edinrCheck;
	private String manSex = "0";
	private String id;
	private String icode;
	private int ANDROID_ACCESS_CXF_WEBSERVICES = 001;
	private GetAuthorizeTask getAuthorizeTask;
	private Handler handler = new Handler(){
		    @Override
		    public void handleMessage(Message msg) {
		      String result = (String) msg.getData().get("result");
		      String obj = (String) msg.obj;//
		      System.out.println("请求结果为："+result);
		    }
		  };
	private RadioGroup radiogroupSex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		getAuthorizeTask = new GetAuthorizeTask();
		getAuthorizeTask.execute();
		initView();
		initData();
	}
	
	/**
	 * 初始化控件，以及设置出生年月
	 */
	private void initView() {
		radiogroupSex = (RadioGroup)findViewById(R.id.radiogroup_sex);
		
		edName = (EditText)findViewById(R.id.ed_name);
		edPhonenum = (EditText)findViewById(R.id.ed_phonenum);
		edAddress = (EditText)findViewById(R.id.ed_address);
		edLlhistory = (TextView)findViewById(R.id.ed_llhistory);
		rlLlhistory = (RelativeLayout)findViewById(R.id.rl_llhistory);
		rlreceivTreatment = (RelativeLayout)findViewById(R.id.rlreceive_treatment);
		edReceiveTreatment = (TextView)findViewById(R.id.ed_receive_treatment);
		
		rltakeMedicament = (RelativeLayout)findViewById(R.id.rltake_medicament);
		edtakeMedicament = (TextView)findViewById(R.id.edtake_medicament);
		
		rlmedicamentNumber = (RelativeLayout)findViewById(R.id.rlmedicament_number);
		edmedicamentNumber = (TextView)findViewById(R.id.edmedicament_number);
		
		rlinrCheck = (RelativeLayout)findViewById(R.id.rlinr_check);
		edinrCheck = (TextView)findViewById(R.id.edinr_check);
		
		
		sp_year = (RelativeLayout)findViewById(R.id.sp_year);
		sp_month = (RelativeLayout)findViewById(R.id.sp_month);
		
		spYear = (TextView) findViewById(R.id.tv_year);
		spMonth = (TextView) findViewById(R.id.tv_month);
		btNext = (Button) findViewById(R.id.bt_next);
		
		/**
		 * 
		 * 选择性别方法
		 */
		radiogroupSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton  radioButton = (RadioButton)findViewById(group.getCheckedRadioButtonId());
				if (radioButton.getText().toString().equals("男")) {
					manSex = "0";
				}else {
					manSex = "1";
				};
			}
		});
		
		
		

		/**
		 * 月选择弹出框
		 */
		sp_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpinerCountry3.setWidth(sp_month.getWidth());
				SpinerCountry3.showAsDropDown(sp_month);
				
			}
		});
		
		/**
		 * 年选择弹出框
		 */
		sp_year.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpinerCountry2.setWidth(sp_year.getWidth());
				SpinerCountry2.showAsDropDown(sp_year);
				
			}
		});
		
		/**
		 * 弹出病史选择框
		 */
		rlLlhistory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpinerCountry1.setWidth(edLlhistory.getWidth());
				SpinerCountry1.showAsDropDown(edLlhistory);
				
			}
		});
		
		/**
		 * 是否接受华林治疗
		 * 
		 */
		rlreceivTreatment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SpinerCountry4.setWidth(edReceiveTreatment.getWidth());
				SpinerCountry4.showAsDropDown(edReceiveTreatment);
			}
		});
		
		/**
		 * 当前服用剂型
		 * 
		 */
		rltakeMedicament.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SpinerCountry5.setWidth(edtakeMedicament.getWidth());
				SpinerCountry5.showAsDropDown(edtakeMedicament);
			}
		});

		/**
		 * 目前使用剂量是否稳定
		 * 
		 */
		rlmedicamentNumber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SpinerCountry6.setWidth(edmedicamentNumber.getWidth());
				SpinerCountry6.showAsDropDown(edmedicamentNumber);
			}
		});
		
		
		/**
		 * 目前INR检查间隔：
		 * 
		 */
		rlinrCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SpinerCountry7.setWidth(edinrCheck.getWidth());
				SpinerCountry7.showAsDropDown(edinrCheck);
				
			}
		});
		
		/**
		 * 保存用户信息到数据库，请求服务器，并跳转到主界面
		 */
		btNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				UserInfoBean mInfoBean = new UserInfoBean();
				PreferencesUtils.setFirstLauncher(UserInfoActivity.this, false);
				String sName = edName.getText().toString().trim();
				String sPhonenum = edPhonenum.getText().toString().trim();
				String sYear = spYear.getText().toString().trim();
				String sMonth = spMonth.getText().toString().trim();
				String sAddress= edAddress.getText().toString().trim();
				String sIllSrc= edLlhistory.getText().toString().trim();
				String sHFLbefore  = edReceiveTreatment.getText().toString().trim();
				String sHFLsrc = edtakeMedicament.getText().toString().trim();
				String sNowPace = edmedicamentNumber.getText().toString().trim();
				String sINRTime = edinrCheck.getText().toString().trim();
				mInfoBean.setID(id);
				mInfoBean.setICODE(icode);
				mInfoBean.setStartTime(CommonUtils.getCurrentDate());
				mInfoBean.setName(sName);
				mInfoBean.setPhoneNum(sPhonenum);
				mInfoBean.setSex(manSex);
				mInfoBean.setAddress(sAddress);
				mInfoBean.setBornYearMonth(sYear+"-"+sMonth);
				mInfoBean.setIllSrc(sIllSrc);
				mInfoBean.setHFLbefore(sHFLbefore);
				mInfoBean.setHFLsrc(sHFLsrc);
				mInfoBean.setNowPace(sNowPace);
				mInfoBean.setINRTime(sINRTime);
				InsertUserDB.getInstance(UserInfoActivity.this).insertUserInfo(mInfoBean);
				requestInfo(sName,sPhonenum,sYear,sMonth,sAddress,sIllSrc,sHFLbefore,sHFLsrc,sNowPace,sINRTime);
				Intent mIntent = new Intent(UserInfoActivity.this,HomeActivity.class);
				startActivity(mIntent);
				finish();
			}
		});

	}
	/**
	 * 
	 * 请求INFO APi
	 * @param sName
	 * @param sPhonenum
	 * @param sYear
	 * @param sMonth
	 * @param sAddress
	 * @param sLlhistory
	 * @param sHFLbefore
	 * @param sHFLsrc
	 * @param sNowPace
	 * @param sINRTime
	 */
	protected void requestInfo(final String sName, final String sPhonenum, final String sYear,
			final String sMonth, final String sAddress, final String sIllSrc,
			final String sHFLbefore, final String sHFLsrc, final String sNowPace, final String sINRTime) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					 Looper.prepare();
					  HashMap<String, Object> map  = new HashMap<String, Object>();
					  map.put("ID", id);
					  map.put("ICODE", icode);
					  map.put("StartTime", CommonUtils.getCurrentDate());
					  map.put("name", sName);
					  map.put("PhoneNum", sPhonenum);
					  map.put("Sex", manSex);
					  map.put("BornYearMonth", sYear+"-"+sMonth);
					  map.put("Address", sAddress);
					  map.put("illSrc", sIllSrc);
					  map.put("HFLbefore", sHFLbefore);
					  map.put("HFLsrc", sHFLsrc);
					  map.put("NowPace",sNowPace);
					  map.put("INRTime",sINRTime);
					  SoapObject soapObject = WebService.common(SOAP_USER_INFO, METHOD_USER_INFO, map, NAME_SPACE, END_POINT_SALE);
					  String result = soapObject.getProperty(0).toString();
					  Log.d("result", result+"");
					  Message message = new Message();
					  Bundle bundle = new Bundle();
					  bundle.putString("result", result);
					  message.what = ANDROID_ACCESS_CXF_WEBSERVICES;//设置消息标示
					  message.obj = "zxn";
					  message. setData(bundle);//消息内容
					  handler.sendMessage(message);//发送消息
					  Looper.loop();
				}
			}).start();
	}

	private void initData() {
		// 年份设定为当年的前后20年
	Calendar cal = Calendar.getInstance();
	for (int i = 0; i < 60; i++) {
		dataYear.add("" + (cal.get(Calendar.YEAR) - 20 + i));
	}

	// 12个月
	for (int i = 1; i <= 12; i++) {
		dataMonth.add("" + (i < 10 ? "0" + i : i));
		
	}
		
	year = new String[dataYear.size()];
	for (int i = 0; i < dataYear.size(); i++) {
		year[i] = dataYear.get(i);
	}
	
	month = new String[dataMonth.size()];
	for (int i = 0; i < dataMonth.size(); i++) {
		month[i] = dataMonth.get(i);
	}
	
	
	//病史数据初始化	
	medicalHistorySpinerData = getResources().getStringArray(R.array.userinfo_medicalhistory_spinersource);	
	SpinerCountry1 = new SpinerPopWindow(this);
	SpinerCountry1.setSpinnerAdatper(medicalHistorySpinerData);
	SpinerCountry1.setItemListener(new IOnItemSelectListener() {
		@Override
		public void onItemClick(int pos) {
			setCountry1(pos);
				
			}
	});
		
	SpinerCountry2 = new SpinerPopWindow(this);
	SpinerCountry2.setSpinnerAdatper(year);
	SpinerCountry2.setItemListener(new IOnItemSelectListener() {
		@Override
	public void onItemClick(int pos) {
			setCountry2(pos);
			}
		});
	SpinerCountry3 = new SpinerPopWindow(this);
	SpinerCountry3.setSpinnerAdatper(month);
	SpinerCountry3.setItemListener(new IOnItemSelectListener() {
		@Override
	public void onItemClick(int pos) {
			setCountry3(pos);
			}
		});

	//是否接受治疗数据初始化
	receiveTreatmentSpinerData = getResources().getStringArray(R.array.userinfo_receivetreatment_spinersource);	
	SpinerCountry4= new SpinerPopWindow(this);
	SpinerCountry4.setSpinnerAdatper(receiveTreatmentSpinerData);
	SpinerCountry4.setItemListener(new IOnItemSelectListener() {
		@Override
	public void onItemClick(int pos) {
			setCountry4(pos);
			}
		});
	
	
	//当前服用剂型
	takeMedicamentSpinerData = getResources().getStringArray(R.array.userinfo_takeMedicament_spinersource);	
	SpinerCountry5= new SpinerPopWindow(this);
	SpinerCountry5.setSpinnerAdatper(takeMedicamentSpinerData);
	SpinerCountry5.setItemListener(new IOnItemSelectListener() {
			@Override
		public void onItemClick(int pos) {
				setCountry5(pos);
				}
			});
	
	//目前使用剂量是否稳定
		receiveTreatmentSpinerData = getResources().getStringArray(R.array.userinfo_receivetreatment_spinersource);	
		SpinerCountry6= new SpinerPopWindow(this);
		SpinerCountry6.setSpinnerAdatper(receiveTreatmentSpinerData);
		SpinerCountry6.setItemListener(new IOnItemSelectListener() {
			@Override
		public void onItemClick(int pos) {
				setCountry6(pos);
				}
			});
	//目前INR检查间隔：
		inrCheckSpinerData = getResources().getStringArray(R.array.userinfo_inrCheck_spinersource);	
		SpinerCountry7= new SpinerPopWindow(this);
		SpinerCountry7.setSpinnerAdatper(inrCheckSpinerData);
		SpinerCountry7.setItemListener(new IOnItemSelectListener() {
			@Override
		public void onItemClick(int pos) {
				setCountry7(pos);
				}
			});
		
	}
	private void setCountry1(int pos) {
		if (pos >= 0 && pos <= medicalHistorySpinerData.length) {
			// CustemObject value = nameList.get(pos);
			// workid = String.valueOf((pos+1));
			edLlhistory.setText(medicalHistorySpinerData[pos]);
		}
	}
	private void setCountry2(int pos) {
		if (pos >= 0 && pos <= year.length) {
			// CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			spYear.setText(year[pos]);
		}
	}
	private void setCountry3(int pos) {
		if (pos >= 0 && pos <= month.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			spMonth.setText(month[pos]);
		}
	}
	private void setCountry4(int pos) {
		if (pos >= 0 && pos <= receiveTreatmentSpinerData.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			edReceiveTreatment.setText(receiveTreatmentSpinerData[pos]);
		}
	}
	private void setCountry5(int pos) {
		if (pos >= 0 && pos <= takeMedicamentSpinerData.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			edtakeMedicament.setText(takeMedicamentSpinerData[pos]);
		}
	}
	private void setCountry6(int pos) {
		if (pos >= 0 && pos <= receiveTreatmentSpinerData.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			edmedicamentNumber.setText(receiveTreatmentSpinerData[pos]);
		}
	}
	private void setCountry7(int pos) {
		if (pos >= 0 && pos <= inrCheckSpinerData.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			edinrCheck.setText(inrCheckSpinerData[pos]);
		}
	}
	
	/**
	 * 获取授权码
	 * @author WangLin
	 *
	 */
	class GetAuthorizeTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			id = CommonUtils.getUUID();
			map.put("ID", id);
			SoapObject soapObject = WebService.common(SOAP_GET_AUTHORIZE, METHOD_GET_AUTHORIZE, map, NAME_SPACE, END_POINT_SALE);
			String result = soapObject.getProperty(0).toString();
			Log.d("GetAuthorizeTask", "webservice return: "+result);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			icode = result;
		}
		
	}

}
