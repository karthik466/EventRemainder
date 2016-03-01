package com.karthik.example.calender.database;

public class DataBaseConstants {

	public static final String DATABASE_NMAE="events.db";
	public static final String TABLE_NAME="tbl_events";
	public static final String ID="_Id";
	public static final String TITLE="_Title";
	public static final String MSG="_Msg";
	public static final String DATE="_Date";
	public static final String TIME_HR="_Time_Hr";
	public static final String TIME_M="_Time_Min";
	public static final String REMAIN_TYPE="_RemainType";
	public static final String REMAIN_THEME="_Theme";
	public static final String REMAIN_NOTIFY_TIME="_NotifyTime";
	public static final String IS_TRIGGERED="_Trigger";
	//public static final String TIME_AM_PM="_Time_Am_Pm";
	public static final String EVENT_IMAGE="_Image";
	
	public static String CREATE_TABLE_EVENTS="CREATE  TABLE  IF NOT EXISTS " +
			TABLE_NAME +
			"( " +
			 ID  +" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
			TITLE +" VARACHAR(150) ," +
			MSG +" VARCAHRA(150) ," +
			DATE +" VARCHAR(12) ,"+   
 			TIME_HR +" INTEGER ," +
 			TIME_M +" INTEGER ," +
 			REMAIN_TYPE +" INTEGER DEFAULT 0,"+
 			//REMAIN_ALARM +" INTEGER  ,"+
 			REMAIN_THEME +" INTEGER, "+
 			REMAIN_NOTIFY_TIME + " INTEGER DEFAULT 0 , " +
 			EVENT_IMAGE + " BLOB ,"+
 		    IS_TRIGGERED +" BOOLEAN DEFAULT 0 )";
	
	
	         
	
}
