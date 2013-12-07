package com.ijustyce.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UsbService extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction(); 
		Log.d("name : ", action);
		if (Constants.START_ACTION.equalsIgnoreCase(action)) { 
			context.startService(new Intent(context, MainActivity.class));
		}
	}
    
}
