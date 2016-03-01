package com.karthik.example.calender.service_recivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootHandler extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	   
       context.startService(new 
    		   Intent(context,SetRepeatAlarmService.class));
		//context.startActivity(new Intent(context,Home.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}

}
