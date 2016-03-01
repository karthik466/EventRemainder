package com.karthik.example.calender.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	public DataBaseHelper(Context context) {
		super(context, DataBaseConstants.DATABASE_NMAE, null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        
		//Log.d("Testing ",DataBaseConstants.CREATE_TABLE_EVENTS );
		 db.execSQL(DataBaseConstants.CREATE_TABLE_EVENTS);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		

	}

}
