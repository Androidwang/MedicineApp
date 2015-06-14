package com.medicine.app;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.medicine.app.utils.CommonUtils;
import com.medicine.app.widgets.CalendarDatePicker;
import com.medicine.app.widgets.CalendarTimePicker;
/**
 * 日期，时间效验TimeValidationActivity
 * 
 * @author wangyang
 *
 */
public class TimeValidationActivity extends Activity {
	private RelativeLayout btSettingDate;
	private RelativeLayout btSettingTime;
	private Button btNext;
	private TextView tvDate;
	private TextView tvTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timevalidation);
		initView();
	}

	/**
	 * 初始化控件。以及监听事件得处理
	 */
	private void initView() {
		btSettingDate = (RelativeLayout) findViewById(R.id.bt_setting_date);
		btSettingTime = (RelativeLayout) findViewById(R.id.bt_setting_time);
		tvDate = (TextView) findViewById(R.id.tv_date);
		tvTime = (TextView) findViewById(R.id.tv_time);
		tvDate.setText(CommonUtils.getCurrentDate());
		tvTime.setText(CommonUtils.getCurrentTime());
		btNext = (Button) findViewById(R.id.bt_next);

		/**
		 * 跳转到填写用户信息界面
		 */
		btNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(TimeValidationActivity.this,
						UserInfoActivity.class);
				startActivity(mIntent);
				finish();
			}
		});

		/**
		 * 设置日期
		 */
		btSettingDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new CalendarDatePicker(TimeValidationActivity.this, tvDate)
						.show(v, TimeValidationActivity.this);
			}
		});

		/**
		 * 设置时间
		 */
		btSettingTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new CalendarTimePicker(TimeValidationActivity.this, tvTime)
						.show(v, TimeValidationActivity.this);
			}
		});

	}

}
