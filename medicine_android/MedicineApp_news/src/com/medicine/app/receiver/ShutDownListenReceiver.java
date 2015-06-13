package com.medicine.app.receiver;

import com.medicine.app.service.ShutDownService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ShutDownListenReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, ShutDownService.class);
		context.startService(service);
	}

}
