package com.medicine.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences公共保存数据类
 * 
 * @author wangyang
 *
 */
public class PreferencesUtils {

	/**
	 * 判断是否已经登录
	 * 
	 * @param context
	 * @param hasLogin
	 */
	public static void saveBooleanToSP(Context context, boolean hasLogin) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		preferences.edit().putBoolean("hasLogin", hasLogin).commit();
	}

	public static boolean getBooleanFromSP(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getBoolean("hasLogin", false);
	}

	/**
	 * 判断是否是第一次进入应用
	 * 
	 * @param context
	 * @param firstLauncher
	 */
	public static void setFirstLauncher(Context context, boolean hasLogin) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		preferences.edit().putBoolean("firstLauncher", hasLogin).commit();
	}

	public static boolean getFirstLauncher(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getBoolean("firstLauncher", true);
	}

	/**
	 * 是否执行屏幕熄灭后2分钟自动关机
	 * @param context
	 * @param shurtDown
	 */
	public static void setShurtDown(Context context, boolean shurtDown) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		preferences.edit().putBoolean("shurtDown", shurtDown).commit();
	}
	
	public static boolean getShurtDown(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getBoolean("shurtDown", false);
	}
	
	/**
	 * 授权码有效期
	 * @param context
	 * @param icodeValidity
	 */
	public static void setIcodeValidity(Context context, String icodeValidity) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		preferences.edit().putString("icodeValidity", icodeValidity).commit();
	}
	public static String getIcodeValidity(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getString("icodeValidity", null);
	}
	
	
}
