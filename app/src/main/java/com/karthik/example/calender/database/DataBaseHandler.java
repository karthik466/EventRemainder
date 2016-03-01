package com.karthik.example.calender.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.text.format.DateFormat;
import android.util.Log;

import com.karthik.example.calender.beans.AlarmDataBean;
import com.karthik.example.calender.beans.RowItem;

public class DataBaseHandler {
	Context context;
	DataBaseHelper helper;
	SQLiteDatabase sdb;
	final String TAG = DataBaseHandler.class.getSimpleName();

	public DataBaseHandler(Context context) {
		this.context = context;
		helper = new DataBaseHelper(context);
		sdb=helper.getWritableDatabase();;
	}

	
    public SQLiteDatabase getInstance()
    {
    	if(helper==null)
		{
			try {
				helper = new DataBaseHelper(context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return helper.getWritableDatabase();		    	
    	
    }	

	public long insertOrUpdateRecord(ContentValues cv,int id) {
		
		long status;
		if(id==0)
		{
		status = sdb.insert(DataBaseConstants.TABLE_NAME, null, cv);
		}
		else
		{
			status= sdb.update(DataBaseConstants.TABLE_NAME, cv,
					DataBaseConstants.ID+" = "+ id,null);
		}
		//Log.d(TAG," Status "+ status);
		return status;
	}

	/*
	 * public List<RowItem> selectRecords() { List<RowItem> rowsList= new
	 * ArrayList<RowItem>();
	 * 
	 * Cursor c=sdb.rawQuery("SELECT * FROM "+ DataBaseConstants.TABLE_NAME,
	 * null); Log.d(TAG, "Count ::"+ c.getCount());
	 * 
	 * c.moveToFirst(); while(!c.isAfterLast()) { Log.d(TAG,c.getString(1));
	 * RowItem item=new RowItem(c.getString(1).toString(), 1);
	 * rowsList.add(item); c.moveToNext(); }
	 * 
	 * 
	 * return rowsList; }
	 */
	/*
	 * public int getRecordsCount() { int s; String strQuerey = "SELECT * FROM "
	 * + DataBaseConstants.TABLE_NAME; Cursor c = sdb.rawQuery(strQuerey, null);
	 * s = c.getCount(); c.close(); return s; }
	 */
	public List<RowItem> selectRecords(String selectDate) {
		// TODO Auto-generated method stub
		List<RowItem> rowsList = null;

		// SELECT * FROM tbl_events where _Date='29/12/2014'
		String strQuery = "SELECT * FROM " + DataBaseConstants.TABLE_NAME
				+ " WHERE _Date = " + "'" + selectDate + "'" +
				"ORDER BY "+ DataBaseConstants.TIME_HR;
		
		//Log.d(TAG, "Query "+ strQuery);
		Cursor c = sdb.rawQuery(strQuery, null);
		if (c.getCount() > 0) {
			c.moveToFirst();
			rowsList=new ArrayList<RowItem>();
			// Log.d(TAG," Count Value  "+ c.getCount()+"   "+ selectDate);
			while (!c.isAfterLast()) {
				//String mTime=createTimeStamp(c.getInt(4),c.getInt(5));
				// Log.d(TAG,c.getString(1));
				/*String mTime = String.format("%02d", c.getInt(4)) + ":"
						+ String.format("%02d", c.getInt(5)) 
						+ "  "
						+ c.getString(6).toString();*/
                  int hr=c.getInt(4);
                  int min=c.getInt(5);
                 // Log.d(TAG,"  H:"+ hr +", Min:"+ min);
				RowItem item = fillCursorData(hr, min, c);
				rowsList.add(item);
				c.moveToNext();
			}
		}
				c.close();
		return rowsList;
	}

	public int getCount(String selectDate) {
		int s;
		String strQuerey = "SELECT * FROM " + DataBaseConstants.TABLE_NAME
				+ " WHERE _Date = " + "'" + selectDate + "'";
		//Log.d(TAG, " getCount() ::" + strQuerey);
		Cursor c = sdb.rawQuery(strQuerey, null);
		s = c.getCount();
		c.close();
		return s;
	}

	// public List<RowItem> sort() {
	public RowItem getNextEvent(String strDate) {
		RowItem item = null;		
		final String dateTemplate = "yyyy-MM-dd";
		Calendar calendar=Calendar.getInstance();
		String toDayDate=(String) DateFormat.format(dateTemplate, calendar.getTime());
		
		String strQuerey = "SELECT * FROM " + DataBaseConstants.TABLE_NAME
				+ " WHERE  Date(_Date) = date('" + strDate
				+ "')   ORDER  BY  _Time_Hr ";
		/*
		 * String strQuerey ="SELECT * FROM " + DataBaseConstants.TABLE_NAME +
		 * " WHARE  DATE(_Date) >=  DATE("+"'"+ strDate +"')"+
		 * " ORDER BY DATE(_Date) ";
		 */		
		//Log.d(TAG, " Next event sql  " + strQuerey);
		Cursor c = sdb.rawQuery(strQuerey, null);
		if (c.moveToFirst()) {
			
			while (!c.isAfterLast()) {
				
				int hr=c.getInt(4);
				int min=c.getInt(5);
			    String date=c.getString(3);
				Log.d(TAG," Status date.equals(toDayDate)"+ date.equals(toDayDate));
				if (calendar.get(Calendar.HOUR_OF_DAY)<= hr 						
						&& calendar.get(Calendar.MINUTE)<=  min 
						&& date.equals(toDayDate)){
				item=fillCursorData(hr,min,c);		
				break;
				}				
				c.moveToNext();
			}
		}
		//c.close();
		
		//If No Record Found in Current Date
		if(item==null)
		{
			//Log.d(TAG," No Record Found");
			strQuerey = "SELECT * FROM " + DataBaseConstants.TABLE_NAME
					+ " WHERE  Date(_Date) > date('" + strDate
					+ "')   ORDER  BY  _Time_Hr ";
			//Log.d(TAG, " Next event sql  " + strQuerey);
			 c = sdb.rawQuery(strQuerey, null);
			 c.moveToFirst();
				while (!c.isAfterLast()) {					
					int hr=c.getInt(4);
					int min=c.getInt(5);
				    					
					item=fillCursorData(hr,min,c);												
					c.moveToNext();
					break;	
				}
		}
		
		c.close();
		return item;
	}

	@SuppressLint("SimpleDateFormat")
	private RowItem fillCursorData( int hr, int min, Cursor c) {
		RowItem item;
		//String mTime = createTimeStamp(hr,min);
		String mTime=hr+":"+min;
		
		//Log.d(TAG," TIME :" + hr +":"+ min);
		item=new RowItem();
		item.setId(c.getInt(c.getColumnIndexOrThrow(DataBaseConstants.ID)));
		item.setTitle(c.getString(c.getColumnIndexOrThrow(DataBaseConstants.TITLE)));
		item.setMsg(c.getString(c.getColumnIndexOrThrow(DataBaseConstants.MSG)));
		item.setDate(c.getString(c.getColumnIndexOrThrow(DataBaseConstants.DATE)));
		item.setTime_hr(hr);
		item.setTime_min(min);
		item.setmChk_Id(c.getInt(c.getColumnIndexOrThrow(DataBaseConstants.REMAIN_TYPE)));
		item.setmTheme_Id(c.getInt(c.getColumnIndexOrThrow(DataBaseConstants.REMAIN_THEME)));
		item.setmRemianderTime(c.getInt(c.getColumnIndexOrThrow(DataBaseConstants.REMAIN_NOTIFY_TIME)));
		item.setImageData((c.getBlob(c.getColumnIndexOrThrow(DataBaseConstants.EVENT_IMAGE))));
		try{
		SimpleDateFormat dt = new SimpleDateFormat("HH:mm"); //for 24 Hr formate
		Date d=null;
		try {
			 d = dt.parse(mTime);
			 item.setmTime(d);
			// Log.d(TAG, " Time "+ d.toString());
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return item;		
	}

	public List<AlarmDataBean> getAlamsData(String strDate) {
		List<AlarmDataBean> list =null;
		
		String[] columns = new String[] { DataBaseConstants.TIME_HR,
				DataBaseConstants.TIME_M/*, DataBaseConstants.TIME_AM_PM*/
				,DataBaseConstants.TITLE,
				DataBaseConstants.MSG,
				DataBaseConstants.REMAIN_TYPE,
				DataBaseConstants.ID};
		Cursor c = sdb.query(DataBaseConstants.TABLE_NAME, columns,
				DataBaseConstants.DATE + "=?", new String[] { strDate }, null,
				null, null);
		
		if (c.getCount() > 0) {
			c.moveToFirst();
			list = new ArrayList<AlarmDataBean>();
			while (!c.isAfterLast()) {
				AlarmDataBean bean = new AlarmDataBean();
				bean.setHr(Integer.parseInt(c.getString(0)));
				bean.setMin(Integer.parseInt(c.getString(1)));
				//bean.setmAm_Pm(c.getString(2));
				bean.setTitle(c.getString(2));
				bean.setMessage(c.getString(3));
				bean.setAlarmType(c.getInt(4));
				bean.setId(c.getInt(5));
				list.add(bean);
				c.moveToNext();
			}
		}
		c.close();
		return list;
	}

	public AlarmDataBean getAlamData(int mDbIndex) {
		// TODO Auto-generated method stub
		AlarmDataBean bean=null;
		String[] columns = new String[] { DataBaseConstants.TIME_HR,
				DataBaseConstants.TIME_M/*, DataBaseConstants.TIME_AM_PM*/
				,DataBaseConstants.TITLE,
				DataBaseConstants.MSG,
				DataBaseConstants.REMAIN_TYPE};
		Cursor c = sdb.query(DataBaseConstants.TABLE_NAME, columns,
				DataBaseConstants.ID + "=?", new String[] { String.valueOf(mDbIndex) }, null,
				null, null);
		
		if (c.moveToFirst()){
			
			while (!c.isAfterLast()) {
				 bean = new AlarmDataBean();
				bean.setHr(Integer.parseInt(c.getString(0)));
				bean.setMin(Integer.parseInt(c.getString(1)));
				//bean.setmAm_Pm(c.getString(2));
				bean.setTitle(c.getString(2));
				bean.setMessage(c.getString(3));
				bean.setAlarmType(c.getInt(4));				
				c.moveToNext();
			}
		}
		c.close();
		return bean;
	}
	public int getCount() {
		Cursor cursor = sdb.query(DataBaseConstants.TABLE_NAME, null, null,
				null, null, null, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	/*private String createTimeStamp(int h,int m)
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
		
	}*/

	public long  deleteRecord(int recordId) {
		
		long status= sdb.delete(DataBaseConstants.TABLE_NAME, DataBaseConstants.ID+" =? " ,
				new String[]{String.valueOf(recordId)});		
		
		
		return status;
	}

	public RowItem getRowInfomation(String rowId) {
         
		RowItem row=null;
		String strQuery = "SELECT * FROM " + DataBaseConstants.TABLE_NAME
				+ " WHERE  "+DataBaseConstants.ID + " = '" + rowId+"'" ;
				
		
		//Log.d(TAG, "Query "+ strQuery);
		Cursor c = sdb.rawQuery(strQuery, null);
		if (c.moveToFirst()) {			
			// Log.d(TAG," Count Value  "+ c.getCount()+"   "+ selectDate);
			while (!c.isAfterLast()) {
				//String mTime=createTimeStamp(c.getInt(4),c.getInt(5));
				// Log.d(TAG,c.getString(1));
				/*String mTime = String.format("%02d", c.getInt(4)) + ":"
						+ String.format("%02d", c.getInt(5)) 
						+ "  "
						+ c.getString(6).toString();*/
                  int hr=c.getInt(4);
                  int min=c.getInt(5);
                 // Log.d(TAG,"  H:"+ hr +", Min:"+ min);
				row = fillCursorData(hr, min, c);
				c.moveToNext();
			}
		}
				c.close();
		
		return row;
	}


	
	
	
}
