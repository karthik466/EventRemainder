package com.karthik.example.calender;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.karthik.example.calender.adepters.CustomAllEventsListAdapter;
import com.karthik.example.calender.beans.AllEventsBean;
import com.karthik.example.calender.beans.RowItem;
import com.karthik.example.calender.database.DataBaseConstants;
import com.karthik.example.calender.database.DataBaseHandler;
import com.karthik.example.calender.utilites.Utils;

public class AllEventsFragment extends Fragment {

	Context context;
	private String fragementName= "Event(s)";
	//private ExpandableListView allEvents_listview;
	//private CustomAllEventsAdapter adapter;
	private CustomAllEventsListAdapter adapter;
	View v;
	DataBaseHandler dataBaseHandler;
	ArrayList<AllEventsBean> listData=null;
	TextView restultText;
	ListView mListView;
	int mIndex=0;
	ImageView img;
	Toolbar toolbar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v= LayoutInflater.from(context).inflate(R.layout.allevents_list_layout, container,false);
		init(v);
		setAdapters();
		moveToNextUpcommingEvent();
		return  v;
	}
	
	
	private void setAdapters() {
		// TODO Auto-generated method stub
		
		listData = fillData();
		if (!listData.isEmpty()) {
			mListView.setVisibility(View.VISIBLE);
			img.setVisibility(View.VISIBLE);
			adapter = new CustomAllEventsListAdapter(context, listData);
			mListView.setAdapter(adapter);
			restultText.setVisibility(View.GONE);
		} else {
			mListView.setVisibility(View.GONE);
			restultText.setVisibility(View.VISIBLE);
			img.setVisibility(View.GONE);
			restultText.setText("No Events Found");
		}
		
	}
	//User Defined Functions 	
	public AllEventsFragment(Context context,Toolbar bar) {
		  this.context=context;
		  this.toolbar= bar;		  
		}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		 toolbar.findViewById(R.id.home_btn_layout).setVisibility(View.INVISIBLE);
	}
	
	private void moveToNextUpcommingEvent()
	{
		//Calendar cal = Calendar.getInstance(Locale.getDefault());
		int[] date= Utils.getTodayDate();
		int month = date[Utils.MONTH]+1;
		int year =  date[Utils.YEAR];
		int day =  date[Utils.DAY];
		String todayDate=year+"-"+String.format("%02d", month)+"-"+String.format("%02d", day);
       
		for(AllEventsBean bean:listData)
		{
			if(todayDate.compareTo(bean.getmTitle().toString())<=0)
					{
				     Log.e("Test", bean.getmTitle().toString()+","+ bean.getmId());
				     mIndex=bean.getmId();
				      break;
					}
		}
		
		mListView.setSelection(mIndex-1);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		toolbar.findViewById(R.id.home_btn_layout).setVisibility(View.VISIBLE);
		
	}
		
		public String getFragmentName() {
			return fragementName;
		}
		
		private void init(View v)
		{
			mListView= (ListView) v.findViewById(R.id.show_data_list);
			restultText= (TextView) v.findViewById(R.id.result_txt);
			img= (ImageView) v.findViewById(R.id.allevents_curr_event_img);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mListView.setSelection(mIndex-1);
				}
			});
				
		}
	
		
		private ArrayList<AllEventsBean> fillData() {
					
				listData=new ArrayList<AllEventsBean>();
			SQLiteDatabase sdb=null;
			try{
			if(dataBaseHandler==null)
			{
				dataBaseHandler=new DataBaseHandler(context);
			}
			
			sdb=dataBaseHandler.getInstance();
			sdb.beginTransaction();
			String query="SELECT DISTINCT  "+ 
							DataBaseConstants.DATE +"  FROM "+ DataBaseConstants.TABLE_NAME +" ORDER BY "+ 
									DataBaseConstants.DATE ;
			//Log.d("edit", query);
			Cursor cursor=sdb.rawQuery(query, null);
			cursor.moveToFirst();
			List<String> DatesList=new ArrayList<String>();
			while(!cursor.isAfterLast())
			{
				DatesList.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
		    int i=1;	  
		    
			for (String str : DatesList) {
				 AllEventsBean eventsBean=new AllEventsBean();
				eventsBean.setmId(i);
				eventsBean.setmTitle(str);
				
				String q= " SELECT * FROM "+ DataBaseConstants.TABLE_NAME
						+" WHERE " + DataBaseConstants.DATE +" = '"+ str + "' ORDER BY "+ 
						DataBaseConstants.TIME_HR;
				
				//Log.d(CustomAllEventsAdapter.class.getSimpleName(), " "+ q);
				
				cursor=sdb.rawQuery(q, null);
				cursor.moveToFirst();
				List<RowItem> rowItems=new ArrayList<RowItem>();
				while (!cursor.isAfterLast()) {
					RowItem item=new RowItem();
					item.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ID)));
					item.setTitle(cursor.getString(cursor.
							getColumnIndex(DataBaseConstants.TITLE)));
					item.setTime_hr(cursor.getInt(cursor.
							getColumnIndex(DataBaseConstants.TIME_HR)));
					item.setTime_min(cursor.getInt(cursor.
							getColumnIndex(DataBaseConstants.TIME_M)));
					rowItems.add(item);
					cursor.moveToNext();
				}
				cursor.close();
				eventsBean.setListData(rowItems);
				listData.add(eventsBean);
				i++;
				
				 
			}
			
			
			
			
			
			
			sdb.setTransactionSuccessful();
			/*for(int i=0;i<=2;i++)
			{
				AllEventsBean bean= new AllEventsBean();
				bean.setmId(i);
				bean.setmTitle(" Tiltle "+ (i+1));
				List<RowItem> listRows=new ArrayList<RowItem>();
				for(int j=1;j<=3;j++)
				{
					RowItem item=new RowItem();
					item.setId(j);
					item.setTitle(" Child "+ j);
					listRows.add(item);				
				}
				bean.setListData(listRows);
				
				listData.add(bean);
			}
			return listData;*/
		}catch(SQLiteException ex){
		   ex.printStackTrace();
		}
	    finally{
	    	if(sdb!=null)
	    		sdb.endTransaction();
	    }
		
			return listData;
	}
}
