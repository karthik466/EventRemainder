package com.karthik.example.calender.service_recivers;


import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SetRepeatAlarmService extends Service {

  Context context=SetRepeatAlarmService.this;	
  String mDate;
@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}
@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("Settings OnCreated", " SetRepeatAlarmService  strated");
	}
@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		Log.d("Settings onStart", " SetRepeatAlarmService  strated");
	}
@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	Log.d("Settings", " SetRepeatAlarmService  strated");
	/*Calendar cal=Calendar.getInstance();
	Log.d("Set"," "+ cal.get(Calendar.HOUR_OF_DAY)+":"+ cal.get(Calendar.MINUTE));*/
	/*Calendar calendar=new GregorianCalendar(
			cal.get(Calendar.YEAR),
			cal.get(Calendar.MONTH),
			cal.get(Calendar.DAY_OF_MONTH),
			11,
			30);	
 manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
		calendar.getTimeInMillis(), piIntent);
 
  manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 
		  calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY,
		  AlarmManager.INTERVAL_DAY, piIntent);
  Log.d("Set"," "+ System.currentTimeMillis()+":"+ calendar.getTimeInMillis());*/
	
  // Set the alarm to start at approximately 11:35 p.m.
  Calendar calendar = Calendar.getInstance();
  calendar.setTimeInMillis(System.currentTimeMillis());
  Log.d("Current Time"," "+ calendar.get(Calendar.HOUR_OF_DAY)+":"+ calendar.get(Calendar.MINUTE));
  calendar.set(Calendar.HOUR_OF_DAY, 0);
  calendar.set(Calendar.MINUTE,0);
  calendar.set(Calendar.SECOND,0);
  
  
  //Setting putExtra values
  mDate= calendar.get(Calendar.YEAR)
		  +"-"+String.format("%02d",(calendar.get(Calendar.MONTH)+1))
		  +"-"+String.format("%02d",(calendar.get(Calendar.DAY_OF_MONTH)));
  Log.d("Set "," date ::"+ mDate);
  Intent serIntent=new Intent(context,SetAlarmService.class);	
  serIntent.putExtra("date", mDate);
	PendingIntent piIntent=PendingIntent.
			getService(context, 1155, serIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	
AlarmManager manager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
  
  
  manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
         AlarmManager.INTERVAL_DAY/*60000*/, piIntent);
  
  Log.d("Settings "," "+ calendar.get(Calendar.HOUR_OF_DAY)+":"+ calendar.get(Calendar.MINUTE));
  
  this.stopSelf();		 
		 return super.onStartCommand(intent, flags, startId);
   }


}
