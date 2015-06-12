package com.medicine.app;

import com.medicine.app.utils.PreferencesUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 启动界面 SplashActivity
 * 
 * @author wangyang
 *
 */
public class SplashActivity extends Activity {
	private TimeCount time;
	private TextView tvSplashTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tvSplashTime = (TextView) findViewById(R.id.tv_splash_time);
		time = new TimeCount(3000, 1000);// 构造CountDownTimer对象
		time.start();
	}

	/**
	 * 定义一个倒计时的内部类
	 * 
	 * @author wangyang
	 *
	 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {
			// 计时完毕时触发,跳转,如果第一次进入，就跳转到时间效验。否则跳转到主界面
			Intent intent;
			if (PreferencesUtils.getFirstLauncher(SplashActivity.this)) {
				intent = new Intent(SplashActivity.this,
						TimeValidationActivity.class);
				
			} else {
				intent = new Intent(SplashActivity.this, HomeActivity.class);
			}
			startActivity(intent);
			finish();
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			tvSplashTime.setClickable(false);
			tvSplashTime.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}
