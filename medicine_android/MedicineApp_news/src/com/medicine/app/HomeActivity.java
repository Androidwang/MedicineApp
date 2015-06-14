package com.medicine.app;
import java.util.ArrayList;
import java.util.List;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.medicine.app.adapter.FragmentTabAdapter;
import com.medicine.app.db.database.InsertUserDB;
import com.medicine.app.fragment.HistoryFragment;
import com.medicine.app.fragment.KnowledgeFragment;
import com.medicine.app.fragment.MyInfoFragment;
import com.medicine.app.fragment.UseMedicineFragment;
import com.medicine.app.model.UserInfoBean;
import com.medicine.app.receiver.ShutDownListenReceiver;

/**
 * HomeActivity主界面
 * 
 * @author wangyang
 *
 */
public class HomeActivity extends FragmentActivity {
	private RadioGroup rgs;
	public List<Fragment> fragments = new ArrayList<Fragment>();
	private List<UserInfoBean> mUserList;
	private TextView leftUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerReceiver();
		setContentView(R.layout.activity_home);
		leftUserName = (TextView)findViewById(R.id.left_userName);
		getSqlUserInfo();
		fragments.add(new UseMedicineFragment());
		fragments.add(new HistoryFragment());
		fragments.add(new KnowledgeFragment());
		fragments.add(new MyInfoFragment(mUserList));
		rgs = (RadioGroup) findViewById(R.id.tabs_rg);
		FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments,
				R.id.tab_content, rgs);
		tabAdapter
				.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
					@Override
					public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
							int checkedId, int index) {
					}
				});
	}
	
	/**
	 * 获取用户信息
	 * 
	 */
	
	private void getSqlUserInfo() {
		mUserList = InsertUserDB.getInstance(HomeActivity.this).selectUserInfo();
        String name = null;
    	UserInfoBean mBean = new UserInfoBean();
    	for (int i = 0; i < mUserList.size(); i++) {
			mBean = mUserList.get(i);
			name = mBean.getName();
		}
    	leftUserName.setText(name+"");
		
	}

	public void registerReceiver() {
			// 动态注册BroadcastReceiver 监听SCREEN_OFF
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction("android.intent.action.SCREEN_OFF");
			ShutDownListenReceiver receiver = new ShutDownListenReceiver();
			registerReceiver(receiver, intentFilter);
	}
}
