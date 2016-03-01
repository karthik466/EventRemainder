package com.karthik.example.calender;

import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.example.calender.adepters.MyDateViewAdapter;
import com.karthik.example.calender.beans.RowItem;
import com.karthik.example.calender.database.DataBaseHandler;
import com.karthik.example.calender.utilites.Utils;

public class CalendarFragment extends Fragment implements OnClickListener,
		OnLongClickListener {
	Context context ;
	final String fragmentName="Calender";
	DataBaseHandler handler;
	public String TAG =fragmentName;
	GridView calender;
	int day_of_week, today_date;;
	int month, year,mMonth,mYear,mDay;
	//Calendar cal;
	Button preMonth, nextMonth;
	TextView monthYear,txtDate,txtTitle,txtMsg,txtTime;
	public static TextView selectedDate;
	@SuppressLint("SimpleDateFormat")
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";	
	LinearLayout layout ;
	boolean mFirstEventFound = false;
	MyDateViewAdapter adapter;
	//FragmentManager fManager;
	int[] date;
	View v;
	Toolbar bar;
	TextView currentDay;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 v= LayoutInflater.from(getActivity()).inflate(R.layout.calender, null, false);
	 	init(v);
	 	
	 	setGridAdapter(month, year);
	 	getNextEvent();
	}
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
	 	
	 	return v;
		
	};
		
 public CalendarFragment(Context context, Toolbar toolbar) {
		  this.context=context;
		  this.bar=toolbar;	
		  this.currentDay= (TextView) bar.findViewById(R.id.action_bar_curr_date);
		  currentDay.setVisibility(View.INVISIBLE);
		  currentDay.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setGridAdapter(date[Utils.MONTH], date[Utils.YEAR]);
			}
		});
		}
		
		public String getFragmentName() {
			return fragmentName;
		}
	
	private void init(View v)
	{
		layout= (LinearLayout) v.findViewById(R.id.eve_ll);
		calender = (GridView) v.findViewById(R.id.calender_grid);
		preMonth = (Button) v.findViewById(R.id.preMonth);
		nextMonth = (Button) v.findViewById(R.id.nextMonth);
		monthYear = (TextView) v.findViewById(R.id.month_yy_textview);
		monthYear.setTextColor(Color.BLUE);
		selectedDate = (TextView) v.findViewById(R.id.sel_date);
		selectedDate.setTextColor(Color.MAGENTA);
		selectedDate.setText("Selected :");
		selectedDate.setVisibility(View.GONE);
		//cal = Calendar.getInstance(Locale.getDefault());
		date=Utils.getTodayDate();
		month = date[Utils.MONTH];
		year = date[Utils.YEAR];
		today_date = date[Utils.DAY];
		/*
		 * Log.d("Test In Main  "+TAG," MONTH YEAR ::"+
		 * DateFormat.format(dateTemplate, cal.getTime()));
		 */
		//setGridAdapter(month, year);

		// Setting Listeners for Button
		preMonth.setOnClickListener(this);
		nextMonth.setOnClickListener(this);
		preMonth.setOnLongClickListener(this);
		nextMonth.setOnLongClickListener(this);

		if (handler == null) {
			handler = new DataBaseHandler(context);
		}
		layout.setVisibility(View.GONE);
		
		/*Intent intent=new Intent(context,MyAlarmReciver.class);
		intent.putExtra("time", "Time");
		sendBroadcast(intent);*/
		
		//Next Event Data
		 txtDate = (TextView) v.findViewById(R.id.text_date);
		 txtTitle = (TextView) v.findViewById(R.id.text_title);
		 txtMsg = (TextView) v.findViewById(R.id.text_msg);
		 txtTime = (TextView) v.findViewById(R.id.text_time);
		 
		
	}

	Handler repeatUpdateHandler = new Handler();

	boolean mAutoIncrement = false;
	boolean mAutoDecrement = false;

	public int mValue;

	class RptUpdater implements Runnable {
		Button button;

		public RptUpdater(Button btn) {
			this.button = btn;
		}

		public void run() {
			if (button.isPressed()) {
				button.performClick();
				repeatUpdateHandler.postDelayed(new RptUpdater(button), 50);
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		repeatUpdateHandler.post(new RptUpdater((Button) v));

		return true;
	}

	@Override
	public void onClick(View v) {		
		if(currentDay.getVisibility()==View.INVISIBLE)
		{
			currentDay.setVisibility(View.VISIBLE);
			currentDay.setText(""+today_date);
		}
		
		if (v == preMonth) {
			if (month <= 0) {
				month = 11;
				year--;
			} else {
				month--;
			}
			/*Log.d(TAG, "Setting Prev Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);*/
			setGridAdapter(month, year);
		}
		if (v == nextMonth) {
			if (month >= 11) {
				month = 0;
				year++;
			} else {
				month++;
			}
			/*Log.d(TAG, "Setting Next Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);*/
			setGridAdapter(month, year);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// Log.d(TAG," onStrat");
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG," onResume");
		bar.findViewById(R.id.home_btn_layout).setVisibility(View.INVISIBLE);		
		getNextEvent();
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//Log.d(TAG," onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// Log.d(TAG," onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Log.d(TAG," onDestroy");
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d(TAG," onDetach");
		 //bar.findViewById(R.id.home_btn_layout).setVisibility(View.VISIBLE);
		 bar.findViewById(R.id.action_bar_curr_date).setVisibility(View.INVISIBLE);
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		 Log.d(TAG," onDestroyView");
	}
	private void setGridAdapter(int mm, int yy) {
		/*Log.d(TAG, " Current Date" + today_date + "::" + Utils.MONTH_NAMES[mm]
				+ "::" + yy);*/		
		
		
		adapter = new MyDateViewAdapter(context, mm, yy,today_date,getFragmentManager(),
				bar);
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.set(yy, mm, 1);
		/*
		 * Log.d("Test   "+TAG," MONTH YEAR ::"+ DateFormat.format(dateTemplate,
		 * c.getTime()));
		 */
		monthYear.setText(DateFormat.format(dateTemplate, c.getTime()));
		adapter.notifyDataSetChanged();
		calender.setAdapter(adapter);
		
	}	
	   protected void getNextEvent()
	   {   		   
              
		   if(handler==null)
		   {
			   handler= new DataBaseHandler(context);
		   }
		  // RowItem item=null;
		   RowItem item = handler.getNextEvent(date[Utils.YEAR] + "-"
						+ String.format("%02d", (date[Utils.MONTH] + 1)) + "-"
						+ String.format("%02d", date[Utils.DAY]));
				
				Log.d(TAG,""+ item);
				if (item!=null) {
					setNextEventLayout(item);
				} else {
					Log.d(TAG, " No Next Event");
					layout.setVisibility(View.GONE);
				}
	   }
	
		private void setNextEventLayout(RowItem item) {			

			// setting the image resource and title
			String[] mDate = item.getDate().split("-");
			// int dd=Integer.parseInt(mDate[2]);
			int mon = Integer.parseInt(mDate[1]);
			// int yy=Integer.parseInt(mDate[0]);
			txtDate.setText(mDate[2] + "-"
					+ Utils.MONTH_NAMES[(mon - 1)].substring(0, 3) + "-"
					+ mDate[0]);
			//Log.d(TAG, mDate[2] + Utils.MONTH_NAMES[(mon - 1)] + mDate[0]);
			txtDate.setTextSize(20);
			txtDate.setTypeface(Typeface.DEFAULT_BOLD);
			txtDate.setVisibility(View.VISIBLE);

			txtTime.setText(Utils.createTimeStamp(item.getTime_hr(),
							item.getTime_min()));
			txtTime.setTextSize(15);

			txtMsg.setText(item.getMsg());
			txtTitle.setText(item.getTitle());
			layout.setVisibility(View.VISIBLE);
		}

	

}
