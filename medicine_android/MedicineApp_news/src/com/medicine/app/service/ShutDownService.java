package com.medicine.app.service;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class ShutDownService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		long time = SystemClock.elapsedRealtime() + 120000; //2分钟
        Intent i = new Intent("com.medicine.app.receiver.DO_SHUR_DOWN");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
		return super.onStartCommand(intent, flags, startId);
	}
}
