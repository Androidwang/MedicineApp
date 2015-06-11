package com.medicine.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	//
	public static String getCurrentTime() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(new Date()).toString();
	}

	
	
	public static void copyDBToSDcrad()
	{
		
		String oldPath = DB_PATH + DATABASE_NAME;
		String newPath = Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME;
		
		copyFile(oldPath, newPath);
	}

	public static void copyFile(String oldPath, String newPath)
	{
		try
		{
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newfile = new File(newPath);
			if (!newfile.exists())
			{
				newfile.createNewFile();
			}
			if (oldfile.exists())
			{ // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1)
				{
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e)
		{
			
			e.printStackTrace();

		}

	}
	
	
}
