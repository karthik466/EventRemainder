package com.karthik.example.calender.service_recivers;


import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.karthik.example.calender.beans.AlarmDataBean;
import com.karthik.example.calender.database.DataBaseHandler;
import com.karthik.example.calender.utilites.Utils;

//public class SetAlarmService extends IntentService {
public class SetAlarmService extends Service {
final static String TAG=SetAlarmService.class.getSimpleName();
DataBaseHandler handler;
String selectDate;
	/*public SetAlarmService() {
		super(SetAlarmService.class.getName());		
	}*/

	/*@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG," Services Stated");
		this.selectDate=intent.getStringExtra("date");
		setMultipleAlrams();
	    Log.d(TAG, "Service Stopping!");
        this.stopSelf();
	}*/

	@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
		 Log.d(TAG," OnCreate");
		}
	@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// TODO Auto-generated method stub
		Log.d(TAG," Services Stated");
		Log.d(TAG," onStartCommand" + intent.getStringExtra("date"));
		selectDate=intent.getStringExtra("date");
		setMultipleAlrams();
	    Log.d(TAG, "Service Stopping!");
        this.stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
	
private void setMultipleAlrams()
	{
		
		if(handler==null)
		{
			handler=new DataBaseHandler(this);
		}	
		Log.d(TAG,"  Test  "+selectDate );
		List<AlarmDataBean> list=handler.getAlamsData(selectDate);
		if(list!=null){		
	    for(int i=0;i<list.size();i++)
	    {
	    	
	    	Utils.setReminder(list.get(i),this,i);
	    	
	    	/*Log.d("Testing ", "Sys Time: "+ System.currentTimeMillis()+", Setting Time::"+ tarCal.getTimeInMillis()+
	    				" \n\tTesting HOUR_OF_DAY "+tarCal.get(Calendar.HOUR_OF_DAY)+", HOUR ::"+tarCal.get(Calendar.HOUR));*/
	    	
	    }
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
