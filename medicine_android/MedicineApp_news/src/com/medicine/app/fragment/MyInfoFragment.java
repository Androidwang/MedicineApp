package com.medicine.app.fragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medicine.app.R;
import com.medicine.app.adapter.AbstractSpinerAdapter.IOnItemSelectListener;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.model.UserInfoBean;
import com.medicine.app.utils.PreferencesUtils;
import com.medicine.app.widgets.SpinerPopWindow;
/**
 * 我的信息界面
 * 
 * @author wangyang
 *
 */
public class MyInfoFragment extends Fragment {
	
	private ArrayList<String> dataYear = new ArrayList<String>();
	private ArrayList<String> dataMonth = new ArrayList<String>();
	private List<UserInfoBean> mUserList ;
	private EditText myinfo_name;
	private EditText myinfo_phone;
	private EditText myinfo_id;
	private EditText myinfo_idcode;
	private CheckBox checkBox;
	private RadioButton radioMan;
	private RadioButton radioWoman;
	private String[] year;
	private String[] month;
	private RelativeLayout sp_year;
	private RelativeLayout sp_month;
	private TextView spYear;
	private TextView spMonth;
	private SpinerPopWindow SpinerCountry1 = null;
	private SpinerPopWindow SpinerCountry2 = null;
	private SpinerPopWindow SpinerCountry3 = null;
	private RelativeLayout rlmyinfoType;
	private TextView myinfoType;
	private String[] takeMedicamentSpinerData;
	private Button myinfoUpdate;
	private RadioGroup groupSex;
	private String manSex = "0";
	
	public MyInfoFragment(List<UserInfoBean> mUserList) {
		this.mUserList = mUserList;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_myinfo, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myinfoUpdate = (Button)getView().findViewById(R.id.myinfo_updateinfo);
		rlmyinfoType = (RelativeLayout)getView().findViewById(R.id.rlmyinfo_type);
		myinfoType = (TextView)getView().findViewById(R.id.myinfo_type);
		myinfo_name  = (EditText)getView().findViewById(R.id.myinfo_name);
		myinfo_phone  = (EditText)getView().findViewById(R.id.myinfo_phone);
		myinfo_id  = (EditText)getView().findViewById(R.id.myinfo_id);
		myinfo_idcode  = (EditText)getView().findViewById(R.id.myinfo_idcode);
		spYear = (TextView)getView().findViewById(R.id.myinfo_year);
		spMonth = (TextView)getView().findViewById(R.id.myinfo_month);
		radioMan = (RadioButton)getView().findViewById(R.id.radio_man);
		radioWoman = (RadioButton)getView().findViewById(R.id.radio_woman);
		sp_year = (RelativeLayout)getView().findViewById(R.id.sp_year);
		sp_month = (RelativeLayout)getView().findViewById(R.id.sp_month);
		groupSex = (RadioGroup) getView().findViewById(R.id.radiogroup_sex);
		
		checkBox = (CheckBox) getView().findViewById(R.id.checkBox1);
		checkBox.setChecked(PreferencesUtils.getShurtDown(getActivity()));
		
		initDataAndSet();
		
		/**
		 * 
		 * 选择性别方法
		 */
		groupSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton  radioButton = (RadioButton)getView().findViewById(group.getCheckedRadioButtonId());
				if (radioButton.getText().toString().equals("男")) {
					manSex = "0";
				}else {
					manSex = "1";
				};
				
			}
		});
		
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferencesUtils.setShurtDown(getActivity(), isChecked);
			}
		
		});
		
		/**
		 * 月选择弹出框
		 */
		sp_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpinerCountry2.setWidth(sp_month.getWidth());
				SpinerCountry2.showAsDropDown(sp_month);
				
			}
		});
		
		/**
		 * 年选择弹出框
		 */
		sp_year.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpinerCountry1.setWidth(sp_year.getWidth());
				SpinerCountry1.showAsDropDown(sp_year);
				
			}
		});
		
		rlmyinfoType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpinerCountry3.setWidth(myinfoType.getWidth());
				SpinerCountry3.showAsDropDown(myinfoType);
			}
		});
		
		myinfoUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserInfoBean mInfoBean = new UserInfoBean();
				String sName = myinfo_name.getText().toString().trim();
				String sPhonenum = myinfo_phone.getText().toString().trim();
				String sYear = spYear.getText().toString().trim();
				String sMonth = spMonth.getText().toString().trim();
				String sMyinfoType = myinfoType.getText().toString().trim();
				mInfoBean.setName(sName);
				mInfoBean.setPhoneNum(sPhonenum);
				mInfoBean.setBornYearMonth(sYear+"-"+sMonth);
				mInfoBean.setSex(manSex);
				mInfoBean.setHFLsrc(sMyinfoType);
				InsertUserDB.getInstance(getActivity()).insertUserInfo(mInfoBean);
				
				
			}
		});
	}
	
	
	/**
	 * 从数据库查询用户输入信息
	 */
	private void initDataAndSet() {
		UserInfoBean mBean = new UserInfoBean();
		String name = null;
		String phone = null;
		String sex = null;
		String id = null;
		String idCode = null;
		String bornYearMonth = null;
		
		for (int i = 0; i < mUserList.size(); i++) {
			mBean = mUserList.get(i);
			name = mBean.getName();
			phone = mBean.getPhoneNum();
			sex = mBean.getSex();
			id = mBean.getID();
			idCode = mBean.getICODE();
			bornYearMonth = mBean.getBornYearMonth();
		}
		myinfo_name.setText(name);
		myinfo_phone.setText(phone);
		myinfo_id.setText(id);
		myinfo_idcode.setText(idCode);
		spYear.setText(bornYearMonth);
		if (sex.equals("0")) {
			radioMan.setChecked(true);;;
		}else {
			radioWoman.setChecked(true);
		}
		
		// 年份设定为当年的前后20年
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < 60; i++) {
			dataYear.add("" + (cal.get(Calendar.YEAR) - 50 + i));
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
		
		
		takeMedicamentSpinerData = getResources().getStringArray(R.array.userinfo_takeMedicament_spinersource);	
		SpinerCountry3= new SpinerPopWindow(getActivity());
		SpinerCountry3.setSpinnerAdatper(takeMedicamentSpinerData);
		SpinerCountry3.setItemListener(new IOnItemSelectListener() {
				@Override
			public void onItemClick(int pos) {
					setCountry3(pos);
					}
				});
		
		
		
		
		SpinerCountry1 = new SpinerPopWindow(getActivity());
		SpinerCountry1.setSpinnerAdatper(year);
		SpinerCountry1.setItemListener(new IOnItemSelectListener() {
			@Override
		public void onItemClick(int pos) {
				setCountry1(pos);
				}
			});
		SpinerCountry2 = new SpinerPopWindow(getActivity());
		SpinerCountry2.setSpinnerAdatper(month);
		SpinerCountry2.setItemListener(new IOnItemSelectListener() {
			@Override
		public void onItemClick(int pos) {
				setCountry2(pos);
				}
			});
		
	
	}
	private void setCountry1(int pos) {
		if (pos >= 0 && pos <= year.length) {
			// CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			spYear.setText(year[pos]);
		}
	}
	private void setCountry2(int pos) {
		if (pos >= 0 && pos <= month.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			spMonth.setText(month[pos]);
		}
	}
	private void setCountry3(int pos) {
		if (pos >= 0 && pos <= takeMedicamentSpinerData.length) {
			 //CustemObject value = nameList.get(pos);
			//workid = String.valueOf((pos+1));
			myinfoType.setText(takeMedicamentSpinerData[pos]);
		}
	}
}
