package com.medicine.app.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.medicine.app.R;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.model.UserInfoBean;
import com.medicine.app.utils.PreferencesUtils;

/**
 * 我的信息界面
 * 
 * @author wangyang
 *
 */
public class MyInfoFragment extends Fragment {
	
	private List<UserInfoBean> mUserList ;
	private EditText myinfo_name;
	private EditText myinfo_phone;
	private EditText myinfo_id;
	private EditText myinfo_idcode;
	private TextView myinfo_year;
	private TextView myinfo_month;
	private CheckBox checkBox;
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
		
		return inflater.inflate(R.layout.fragment_myinfo, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myinfo_name  = (EditText)getView().findViewById(R.id.myinfo_name);
		myinfo_phone  = (EditText)getView().findViewById(R.id.myinfo_phone);
		myinfo_id  = (EditText)getView().findViewById(R.id.myinfo_id);
		myinfo_idcode  = (EditText)getView().findViewById(R.id.myinfo_idcode);
		myinfo_year = (TextView)getView().findViewById(R.id.myinfo_year);
		myinfo_month = (TextView)getView().findViewById(R.id.myinfo_month);
		checkBox = (CheckBox) getView().findViewById(R.id.checkBox1);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferencesUtils.setShurtDown(getActivity(), isChecked);
			}
		});
		initData();
	}
	/**
	 * 从数据库查询用户输入信息
	 */
	private void initData() {
		UserInfoBean mBean = new UserInfoBean();
		mUserList = InsertUserDB.getInstance(getActivity()).selectUserInfo();
		String name = null;
		String phone = null;
		for (int i = 0; i < mUserList.size(); i++) {
			mBean = mUserList.get(i);
			name = mBean.getName();
			phone = "12";
		}
		myinfo_name.setText(name);
		myinfo_phone.setText(phone);
	}

}
