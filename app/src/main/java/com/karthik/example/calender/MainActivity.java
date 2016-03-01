package com.karthik.example.calender;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {
DrawerLayout mDrawer;
Toolbar toolbar;
LinearLayout mContainer;
ActionBarDrawerToggle mDrawerToggle;
TextView mAllEvents,mSettingQuit,mFindAge,mTitle;
FragmentManager fManager;
FragmentTransaction transaction;
AllEventsFragment allEventsFragment;
CalendarFragment calenderFragment;
EventsFragement eventsFragment;
Fragment fragmentObj;
Context context;
LinearLayout zoomLayout;
ImageButton home;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 fManager=getFragmentManager();

		init();
		setListeners();
		setDefaultFragemt();
		
	}
	
	private void init()
	{
		this.context=this;
		toolbar= (Toolbar) findViewById(R.id.app_bar);
		mDrawer= (DrawerLayout) findViewById(R.id.drawerlayout);
		//Setting Home Button Visiblity
		mContainer= (LinearLayout) findViewById(R.id.fragment_layout);
		mTitle= (TextView) findViewById(R.id.action_bar_title);
		home= (ImageButton) toolbar.findViewById(R.id.home_btn);
		mAllEvents=(TextView) findViewById(R.id.all_events_view);
		mSettingQuit=(TextView) findViewById(R.id.close_txt);
		mFindAge= (TextView) findViewById(R.id.age_finder_txt);		
		zoomLayout= (LinearLayout) findViewById(R.id.mainView);
		setSupportActionBar(toolbar);	
		
		
	}
	private void setListeners(){
		mDrawerToggle=new ActionBarDrawerToggle(this, mDrawer, toolbar,0,0 ){

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
			}
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				// TODO Auto-generated method stub
				super.onDrawerSlide(drawerView, slideOffset);
				/*if(slideOffset<0.6f)
				{
					zoomLayout.setScaleX(1-slideOffset);
					zoomLayout.setScaleY(1-slideOffset);
				}*/
				
			}
			
		};
		mDrawer.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
		
		  mAllEvents.setOnClickListener(this);
		  mSettingQuit.setOnClickListener(this);
		  mFindAge.setOnClickListener(this);
		  home.setOnClickListener(this);
	}

private void setDefaultFragemt() {	
		
		if(calenderFragment==null)
		{
			calenderFragment=new CalendarFragment(context,toolbar);
			
		}
		 transaction=fManager.beginTransaction();
		 transaction.replace(R.id.fragment_layout, calenderFragment, calenderFragment.getFragmentName());
		 //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		 transaction.commit();			
		 mTitle.setText(calenderFragment.getFragmentName());
		 
		
	}
	@Override
	public void onClick(View v) {		
		
		switch(v.getId())
		{
		case R.id.home_btn:
			int mCount= fManager.getBackStackEntryCount();
			if(mCount>0)
			{		
				onBackPressed();
			}
			break;
		case R.id.all_events_view:
			 if(allEventsFragment==null)
			 {
				 allEventsFragment=new AllEventsFragment(context,toolbar);		 
			 }		 
				 transaction=fManager.beginTransaction();
				 transaction.replace(R.id.fragment_layout, allEventsFragment, allEventsFragment.getFragmentName());
				 transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				 transaction.addToBackStack(allEventsFragment.getFragmentName());
				 transaction.commit();	
				 mTitle.setText(allEventsFragment.getFragmentName());
			break;
		case R.id.close_txt:
			this.finish();
			break;			
		}
		mDrawer.closeDrawers();
		
	}
	
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		
		
		}
	@Override
		public void onBackPressed() {
		//super.onBackPressed();
		
		
		 // getFragmentManager().popBackStack();
//		   fManager=getFragmentManager();
//		  fragmentObj = fManager.findFragmentById(R.id.fragment_layout);
		 
			
			/*if(fragmentObj.getTag().equals(mainFragment.getFragmentName()))
			{
				super.onBackPressed();
			}			
			else
			{				 
				fManager.popBackStack();
			}*/
			int mCount= fManager.getBackStackEntryCount();
			if(mCount>0)
			{
				calenderFragment=null;
				setDefaultFragemt();
			}
			else
			{
				super.onBackPressed();
			}
		}
}
