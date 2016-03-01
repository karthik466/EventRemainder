package com.karthik.example.calender;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.karthik.example.calender.beans.AlarmDataBean;
import com.karthik.example.calender.database.DataBaseHandler;

public class EventsFragement extends Fragment implements OnClickListener{
 final String TAG=EventsFragement.class.getSimpleName();
	final String fragmentName=TAG; 	
    String selectDate,strDate;
	ListFragment fragment;
	Context context;
	
	Fragment eventsFragment;
	ScrollView newEventLayout;
	DataBaseHandler handler;	
	//SwipeRefreshLayout refreshLayout;
	AlarmDataBean timeBean;
	Button addEventImage;
	//TextView dateText;
	MyListFragment listFragment=null;
	Toolbar toolbar;
  @Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//setContentView(R.layout.events_layout);
	//init();
	
	Log.d(TAG,"OnCreate");
	
}
  @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
    View v= inflater.inflate(R.layout.events_layout, container,false);
    init(v);
    Log.d(TAG,"OnCreateView");
	  return v;
}
  
  public EventsFragement(Context context,android.support.v7.widget.Toolbar mActionBar) {
	  this.context=context;
	  this.toolbar= mActionBar;
	}
	
	public String getFragmentName() {
		return fragmentName;
	}
  public void setData(String date,boolean isNew)
  {
	this.strDate= date;  
	//this.isNew=isNew;
  }
  private void init(View v)
  {
	   
	  // dateText= (TextView) v.findViewById(R.id.event_date);
		newEventLayout= (ScrollView) v.findViewById(R.id.new_event_layout);
		newEventLayout.setVisibility(View.GONE);
		addEventImage= (Button) v.findViewById(R.id.add_event_img);
		addEventImage.setOnClickListener(this);
		if(handler==null)
		{
			handler=new DataBaseHandler(context);
		}		
		//convert given date to DataBase Date Format
		String[] date=strDate.split("-");
		selectDate = date[2]+"-"+fill(date[1])+"-"+fill(date[0]);		
		Log.d(TAG," Date"+ selectDate);
		//Log.d(TAG," List Size::"+ fragment.getListView().getCount());
		// setListeners();
		//dateText.setText(date[0]+"-"+fill(date[1])+"-"+fill(date[2]));
  }
  


public void onDestroyView()
{
            /*FragmentManager mFragmentMgr= getFragmentManager();
    FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
            Fragment childFragment =mFragmentMgr.findFragmentById(R.id.events_list_fragment);
    mTransaction.remove(childFragment);
    mTransaction.commit();*/
    super.onDestroyView();
    Log.d(TAG,"OnDestoryView");
}

@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG,"onResume");	
		updateFragmentData();
	}


private void updateFragmentData() {
	// TODO Auto-generated method stub
	//layout.removeAllViewsInLayout();
	((TextView)toolbar.findViewById(R.id.action_bar_title)).setText(strDate);
	listFragment=new MyListFragment(selectDate);
	FragmentTransaction ft = getFragmentManager().beginTransaction(); 
	ft.replace(R.id.events_list_fragment, listFragment, "NewFragmentTag"); 
	ft.commit(); 
	
	
}
@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	Log.d(TAG,"onResume");	
}
@Override
public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	Log.d(TAG,"onResume");	
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
}
@Override
public void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	Log.d(TAG,"onStop");	
}
@SuppressLint("DefaultLocale")
private String fill(String str)
{
	String s=null;
	try
	{
		int i= Integer.parseInt(str);
		s=String.format("%02d", i);		
	}
	catch(NumberFormatException ex)
	{
		ex.printStackTrace();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	
	return  s;
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch(v.getId())
	{
	case R.id.add_event_img:
		
		
		Intent intent=new Intent(context,EventActivity.class);
		intent.putExtra("rowId","-1");
		intent.putExtra("date", selectDate);
		context.startActivity(intent);		
		
		
		break;
	}
}
}
