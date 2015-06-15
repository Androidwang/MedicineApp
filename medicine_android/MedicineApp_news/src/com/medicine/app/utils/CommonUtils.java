package com.medicine.app.utils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 公共方法类
 * 
 * @author wangyang
 *
 */
@SuppressLint("SimpleDateFormat")
public class CommonUtils implements CommonConst{
	// AlertDialog共用弹出信息
	public static void showDialogMessage(Context mContext, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext)
				.setMessage(msg).setPositiveButton("OK", null);
		dialog.create();
		dialog.show();
	}

	// 共同提示信息 Toast
	public static void showToastMessage(Context mContext, String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	// 检查并退出键盘
	public static void exitKeyboard(final Activity mActivity) {
		if (mActivity.getCurrentFocus() != null) {
			((InputMethodManager) mActivity
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(mActivity.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	// 获取当前时间
	public static String getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	//
	public static String getCurrentTime() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(new Date()).toString();
	}

	
	
	 //获取唯一的序列号
	public static final String getUUID() {
		   String serial = null;
	       try {
	           Class<?> c = Class.forName("android.os.SystemProperties");
	           Method get = c.getMethod("get", String.class);
	           serial = (String) get.invoke(c,"ro.serialno");
	       } catch (Exception ignored) {
	    	   Log.e("CommonUtils", "getUUID() error", ignored);
	       }
	       return serial;
		
		}

	/**
	 * 获取当前网络状态，判断是否可用
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity activity) {

		Context context = activity.getApplicationContext();

		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
