package com.medicine.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;
/**
 * 倒计时 CountdownUtil
 * 
 * @author wangyang
 *
 */
public class CountdownUtil extends CountDownTimer {
	private TextView mTextTime; // 倒计时显示时间
	private String captcha; // 存储服务器返回的验证码
	private Context mContext;

	public CountdownUtil(long millisInFuture, long countDownInterval,
			Context mContext, TextView mTextTime) {
		super(millisInFuture, countDownInterval);
		this.mTextTime = mTextTime;
		this.mContext = mContext;
	}

	@Override
	public void onFinish() {
		captcha = null;
		mTextTime.setText("");
	}

	@Override
	public void onTick(long millisUntilFinished) {
		mTextTime.setTextColor(Color.WHITE);
		// mTextTime.setText(millisUntilFinished / 1000
		// + mContext.getString(R.string.authcod_send_time));
	}

}
