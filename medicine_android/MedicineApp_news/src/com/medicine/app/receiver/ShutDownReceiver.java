package com.medicine.app.receiver;

import java.io.DataOutputStream;
import java.io.IOException;

import com.medicine.app.utils.PreferencesUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ShutDownReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(PreferencesUtils.getShurtDown(context)){
			shutdown();
		}
	}
	/**
	 * 命令行执行关机
	 */
	public void shutdown() {
		try { 
            Process process = Runtime.getRuntime().exec("su"); 
            DataOutputStream out = new DataOutputStream( 
                    process.getOutputStream()); 
            out.writeBytes("reboot -p\n"); 
            out.writeBytes("exit\n"); 
            out.flush(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
	}
}
