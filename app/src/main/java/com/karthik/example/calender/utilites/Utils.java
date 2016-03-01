package com.karthik.example.calender.utilites;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.karthik.example.calender.ShowAlarm;
import com.karthik.example.calender.beans.AlarmDataBean;
import com.karthik.example.calender.service_recivers.MyAlarmReciver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class Utils {
	public static int TONE_SETTING=0;
	public static int ALL_EVENTS=1;
	public static int YEAR=0;
	public static int MONTH=1;
	public static int DAY=2;
	public static String ROWID="_rowId";

	public static final String[] MONTH_NAMES = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	public static final int[] DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			                          30, 31 };
    public static final int[] colors={Color.GREEN,Color.LTGRAY,Color.GRAY,Color.RED};
    /*public final static String[] WEEK_DAYS = {"Sun", "Mon", "Tue", "Wen", "Thu",
		"Fri", "Sat" };*/
    public static final String[] WEEK_DAYS = {"Su", "Mo", "Tu", "We", "Th",
		"Fr", "Sa" };
    public static final int DAY_OFFSET = 1;
   // public static String date="";
    public static final String tone=null;
    
    public static String createTimeStamp(int h,int m)
	{
		String am_pm = "";
		if (h > 12) {
			h -= 12;
			am_pm = "PM";
    } else if (h == 0) {
    	h += 12;
    	am_pm = "AM";
    } else if (h == 12){
    	am_pm = "PM";}
    else{
    	am_pm = "AM";} 
		
		return (String.format("%02d", h) + ":"
			   + String.format("%02d", m)+ " "+ am_pm );		
		
	}
    
    public static int[] getTodayDate()
    {
    	
    	
    	Calendar cal = Calendar.getInstance(Locale.getDefault());
    	return new int[]{cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.get(Calendar.DAY_OF_MONTH)};
    	
    }
    
    public static  void setReminder(AlarmDataBean bean,Context context, int id){
    	Context mContext=context;
    	AlarmManager manager;
    	PendingIntent piIntent;
    	
    	int h=bean.getHr();
    	int m=bean.getMin();
    	/*if(bean.getmAm_Pm().equalsIgnoreCase("PM")){
    		h+=12;
    	}*/
    	
    	//Log.d("Testing ::"," DB--> H:"+ bean.getHr()+" , Updated --> H: "+ h);
    	
    		
    	
    	
    	Calendar cal=Calendar.getInstance();
    	Calendar tarCal= new GregorianCalendar(cal.get(Calendar.YEAR),
    			cal.get(Calendar.MONTH),
    			cal.get(Calendar.DAY_OF_MONTH), 
    			h,
    			m);
    	
    	//Log.d("Testing ::"," HOF:"+ cal.get(Calendar.HOUR_OF_DAY)+" , Updated --> H: "+ h);
    	//tarCal.set(Calendar.HOUR_OF_DAY, h);
    	//tarCal.set(Calendar.MINUTE,m);
    	manager= (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    	if(!tarCal.before(cal)){
    		
    		
    		Intent brodcastIntent=new Intent(mContext,MyAlarmReciver.class);
			brodcastIntent.putExtra("title", bean.getTitle());
			brodcastIntent.putExtra("msg", bean.getMessage());
			brodcastIntent.putExtra(Utils.ROWID, bean.getId());
			
			Intent mIntent=new Intent(mContext,ShowAlarm.class);
		    	mIntent.putExtra("hours", h);
		    	mIntent.putExtra("min", m);
		    	mIntent.putExtra("title", bean.getTitle());
		    	mIntent.putExtra("msg", bean.getMessage());
    		
		    	
    		if(bean.getAlarmType()==0) //Notification Broadcast
	    	{   		
	    		piIntent=PendingIntent.getBroadcast(mContext, id,
	    				brodcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    		manager.set(AlarmManager.RTC_WAKEUP, tarCal.getTimeInMillis(), piIntent);
	    	}
    		else if(bean.getAlarmType()==1){   //AlramActivity
    	    piIntent=PendingIntent.getActivity(mContext, id, mIntent,
    	    		PendingIntent.FLAG_UPDATE_CURRENT);
    	    manager.set(AlarmManager.RTC_WAKEUP, tarCal.getTimeInMillis(), piIntent);
    		}
    		else
    		{ //Setting Notification 	    		
	    		piIntent=PendingIntent.getBroadcast(mContext, id,
	    				brodcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    		manager.set(AlarmManager.RTC_WAKEUP, tarCal.getTimeInMillis(), piIntent);		    		 		
	    		//Setting CustomAlarm				    		    	
		    	    piIntent=PendingIntent.getActivity(mContext, id,
		    	    		mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		    	    manager.set(AlarmManager.RTC_WAKEUP, tarCal.getTimeInMillis(), piIntent);
		    	    
    		}   	
    	}
    	else  if(!bean.isTriggered())
    	{
    		Intent brodcastIntent=new Intent(mContext,MyAlarmReciver.class);
			brodcastIntent.putExtra("title", "Your Missed Alerts!"+ "\n"+ bean.getTitle());
			brodcastIntent.putExtra("msg", bean.getMessage());
			brodcastIntent.putExtra(Utils.ROWID, bean.getId());
			
			
			piIntent=PendingIntent.getBroadcast(mContext, id,
    				brodcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    		manager.set(AlarmManager.RTC_WAKEUP, 0, piIntent);
    		
    	
    	}
    }
    
}
