package com.medicine.app;
import java.util.ArrayList;
import java.util.List;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import com.medicine.app.adapter.FragmentTabAdapter;
import com.medicine.app.fragment.HistoryFragment;
import com.medicine.app.fragment.KnowledgeFragment;
import com.medicine.app.fragment.MyInfoFragment;
import com.medicine.app.fragment.UseMedicineFragment;
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
	  
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 动态注册BroadcastReceiver 监听SCREEN_OFF
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.SCREEN_OFF");
		ShutDownListenReceiver receiver = new ShutDownListenReceiver();
		registerReceiver(receiver, intentFilter);
		
		
		setContentView(R.layout.activity_home);
		fragments.add(new UseMedicineFragment());
		fragments.add(new HistoryFragment());
		fragments.add(new KnowledgeFragment());
		fragments.add(new MyInfoFragment());
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
}
