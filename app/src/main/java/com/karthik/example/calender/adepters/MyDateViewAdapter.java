package com.karthik.example.calender.adepters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.karthik.example.calender.EventsFragement;
import com.karthik.example.calender.R;
import com.karthik.example.calender.beans.RowItem;
import com.karthik.example.calender.database.DataBaseHandler;
import com.karthik.example.calender.utilites.Utils;

public class MyDateViewAdapter extends BaseAdapter {
	private String TAG = MyDateViewAdapter.class.getSimpleName();
	Context context;
	List<String> dateList;
	Calendar mCal;
	int mDate, mMonth, mYear, day_of_week, today_date;
	DataBaseHandler handler;
	int mCalMonth;
	FragmentManager manager;
	Toolbar mToolbar;

	public MyDateViewAdapter(Context context, int month, int year,
			int today_date, FragmentManager fragmentManager, Toolbar bar) {
		this.context = context;
		this.today_date = today_date;
		this.manager= fragmentManager;
		this.mToolbar=bar;
		setCurrentDate();
		setMonthData(month, year);
		handler = new DataBaseHandler(context);
		mCalMonth=month;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dateList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dateList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "NewApi", "ResourceAsColor" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button btn;
		ImageView iv1,iv2,iv3;
		if (convertView == null) {
			convertView = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.mydate, parent, false);
		}
		btn = (Button) convertView.findViewById(R.id.date_txt);
		iv1 = (ImageView) convertView.findViewById(R.id.rem_img1);
		iv2 = (ImageView) convertView.findViewById(R.id.rem_img2);
		iv3 = (ImageView) convertView.findViewById(R.id.rem_img3);
		iv1.setVisibility(View.GONE);
		iv2.setVisibility(View.GONE);
		iv3.setVisibility(View.GONE);
		String[] data = dateList.get(position).split("-");
		// DateStyle ds = dateList.get(position);
		btn.setText(data[0]);
		// Log.d(TAG, "List Value ::"+ ds.getValue() + ",Color "+
		// ds.getColor());
		int pos = Integer.parseInt(data[1]);
		btn.setTextColor(Utils.colors[pos]);
		if (position < 7) {
			btn.setBackgroundColor(android.R.color.transparent);
			btn.setPadding(15, 0, 0, 0);
			btn.setEnabled(false);
		} else {
			int month = Integer.parseInt(data[2]);
			// Log.d(TAG, " Date "+
			// data[0]+"-"+MONTH_NAMES[month]+"-"+data[3]);
			btn.setTag(data[0] + "-" + month + "-" + data[3]);
			btn.setEnabled(true);
			String strDate = data[3] + "-" + String.format("%02d",(month + 1)) + "-" + String.format("%02d",Integer.parseInt(data[0]));
			/*//int size = handler.getCount(strDate);			
			//Log.d(TAG, " Events Count " + size);
			if (size > 0) {
				iv1.setVisibility(View.VISIBLE);
			}*/
			
		
			
			if((position%7==0 || position%7==6) && month==mCalMonth)
			{
				//btn.setBackgroundColor(R.color.purple);
				btn.setTextColor(context.getResources().getColor(R.color.purple));
			}
			List<RowItem> listItems= handler.selectRecords(strDate);
			if(listItems!=null){
			for (RowItem rowItem : listItems) {
				if(rowItem.getmTheme_Id()==0){
					setIconVisible(iv1);
				} else if(rowItem.getmTheme_Id()==1){
					setIconVisible(iv2);
				}
				else{
					//btn.setBackgroundResource(R.drawable.circle_blue);
				}
			}
			}

		}

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCalEVentsData(v.getTag().toString(), context);
				// Toast.makeText(ctx, "Clicked",
				// Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	private void setIconVisible(ImageView iView)
	{
		if(iView.getVisibility()==View.GONE)
		{
			iView.setVisibility(View.VISIBLE);
		}
	}
	
	private void setCurrentDate() {
		mCal = Calendar.getInstance();
		mYear = mCal.get(Calendar.YEAR);
		mMonth = mCal.get(Calendar.MONTH);
		mDate = mCal.get(Calendar.DAY_OF_MONTH);
	}

	private void setMonthData(int month, int year) {
		int currMonth = 0;
		int currYear = 0;
		int preMonth = 0;
		int nextMonth = 0;
		int preYear = 0;
		int nextYear = 0;
		int trialingSpace = 0;
		int daysInCurrentMonth = 0;
		int daysInPreMonth = 0;
		int daysInNextMonth = 0;

		// Calendar newCalendar=Calendar.getInstance();
		// Log.d(TAG," New Calender ::"+ newCalendar.getTime().toString());
		currMonth = month;
		currYear = year;

		GregorianCalendar myCalander = new GregorianCalendar(currYear,
				currMonth, 1);

		// Log.d(TAG, "Gregorian Calendar:= " +
		// myCalander.getTime().toString());
		if (currMonth == 11) {
			preMonth = currMonth - 1;
			preYear = currYear;
			daysInPreMonth = getDaysOfMonth(preMonth);
			nextMonth = 0;
			nextYear = currYear + 1;
			/*
			 * Log.d(TAG, "*->PreYear: " + preYear + " PreMonth:" +
			 * Utils.MONTH_NAMES[preMonth] + " NextMonth: " +
			 * Utils.MONTH_NAMES[nextMonth] + " NextYear: " + nextYear);
			 */
		} else if (currMonth == 0) {
			preMonth = 11;
			preYear = currYear - 1;
			daysInPreMonth = getDaysOfMonth(preMonth);

			nextMonth = 1;
			nextYear = currYear;
			/*
			 * Log.d(TAG, "*->PreYear: " + preYear + " PreMonth:" +
			 * Utils.MONTH_NAMES[preMonth] + " NextMonth: " +
			 * Utils.MONTH_NAMES[nextMonth] + " NextYear: " + nextYear);
			 */
		}

		else {
			preMonth = currMonth - 1;
			nextMonth = currMonth + 1;
			preYear = currYear;
			nextYear = currYear;
			daysInPreMonth = getDaysOfMonth(preMonth);
			/*
			 * Log.d(TAG, "*->PreYear: " + preYear + " PreMonth:" +
			 * Utils.MONTH_NAMES[preMonth] + " NextMonth: " +
			 * Utils.MONTH_NAMES[nextMonth] + " NextYear: " + nextYear);
			 */
		}

		day_of_week = myCalander.get(Calendar.DAY_OF_WEEK) - 1;

		trialingSpace = day_of_week;
		/*
		 * Log.d(TAG, " Trial Days::" + trialingSpace + ",Week Day ::" +
		 * Utils.WEEK_DAYS[day_of_week]);
		 */
		daysInCurrentMonth = getDaysOfMonth(currMonth);

		if (myCalander.isLeapYear(myCalander.get(Calendar.YEAR))) {
			if (currMonth == 1) {
				++daysInCurrentMonth;
			} else if (currMonth == 2) {
				++daysInPreMonth;
			}
		}

		// nextMonth=currMonth+1;
		dateList = new ArrayList<String>();
		// DateStyle date;
		StringBuffer date;
		for (String week : Utils.WEEK_DAYS) {
			// date=new DateStyle();
			// date.setValue(week);
			// date.setColor(Color.GREEN);
			date = new StringBuffer();
			date.append(week).append("-").append("0").append("-").append("MM")
					.append("-").append("YY");
			dateList.add(date.toString());
		}
		// Fill the Previous Days of Month

		for (int i = trialingSpace; i > 0; i--) {
			// date=new DateStyle();
			// date.setValue(String.valueOf(daysInPreMonth-i+DAY_OFFSET));
			// date.setColor(Color.LTGRAY);
			// dateList.add(date);

			date = new StringBuffer();
			date.append(String.valueOf(daysInPreMonth - i + Utils.DAY_OFFSET))
					.append("-").append("1").append("-").append(preMonth)
					.append("-").append(preYear);
			dateList.add(date.toString());

		}

		// Fill the Current Days of Month

		for (int i = 1; i <= daysInCurrentMonth; i++) {
			/*
			 * //date=new DateStyle(); //date.setValue(String.valueOf(i));
			 * if(i==today_date){ //date.setColor(Color.RED); } else{
			 * //date.setColor(Color.GRAY); } dateList.add(date);
			 */
			date = new StringBuffer();
			String color = String.valueOf(2);
			if (i == today_date) {

				if (currMonth == mMonth && currYear == mYear) {
					color = String.valueOf(3);
				} else {
					color = String.valueOf(2);
				}
			}
			date.append(String.valueOf(i)).append("-").append(color)
					.append("-").append(currMonth).append("-").append(currYear);
			dateList.add(date.toString());

		}
		// Fill the Next Days of Month
		if (dateList.size() % 7 > 0) {
			daysInNextMonth = 7 - dateList.size() % 7;
			/*
			 * Log.d(TAG, "Days list::" + dateList.size() +
			 * ", Days in Next Month View" + daysInNextMonth);
			 */
			for (int i = 1; i <= daysInNextMonth; i++) {
				/*
				 * //date=new DateStyle(); //date.setValue(String.valueOf(i+1));
				 * //date.setColor(Color.LTGRAY); dateList.add(date);
				 */
				date = new StringBuffer();
				date.append(String.valueOf(i)).append("-").append("1")
						.append("-").append(nextMonth).append("-")
						.append(nextYear);
				dateList.add(date.toString());
			}
		}
	}

	private int getDaysOfMonth(int i) {
		// TODO Auto-generated method stub
		return Utils.DAYS_IN_MONTH[i];
	}

private void showCalEVentsData(String str, Context ctx) {
		try {
			String date_month_year = str;

			// Date parsedDate = dateFormatter.parse(date_month_year);
			// Log.d(TAG, "Parsed Date: " + parsedDate.toString());
			String[] mDate = date_month_year.split("-");
			// Fetching Selected Date
			int mSelDay = Integer.parseInt(mDate[0]);
			int mSelMon = Integer.parseInt(mDate[1]);
			int mSelYY = Integer.parseInt(mDate[2]);
			boolean isNew = false;
			String mSelDate = mSelDay + "-" + (mSelMon + 1) + "-" + mSelYY;
			//MainFragment.selectedDate.setText("Selected : " + mSelDate);
			//MainFragment.selectedDate.setVisibility(View.VISIBLE);
			// getting Current Date
			Calendar mCal = Calendar.getInstance();
			int toDay = mCal.get(Calendar.DATE);
			int thisMonth = mCal.get(Calendar.MONTH);
			int thisYear = mCal.get(Calendar.YEAR);
			if (thisYear == mSelYY) {
				if ( thisMonth == mSelMon) {
					Log.d("Test", " Same Month");
					if (toDay <= mSelDay) {
						isNew = true;
					} else {
						isNew = false;
					}
				}
				else if( thisMonth < mSelMon)
				{
					isNew = true;
				}
			} else if (mSelYY > thisYear) {
				isNew = true;
			} else {
				isNew = false;
			}
			EventsFragement eventsFragment = new EventsFragement(context,mToolbar);
			//FragmentManager fManager = MainFragment.fManager ;			
			eventsFragment.setData(mSelDate, isNew);
			/*Utils.date = mSelYY + "-" + String.format("%02d", (mSelMon + 1))
					+ "-" + String.format("%02d", mSelDay);
			Log.d(TAG, " Main Fragment date ::" + Utils.date);*/
			
			FragmentTransaction transaction = manager.beginTransaction();

			transaction.replace(R.id.fragment_layout, eventsFragment,
					eventsFragment.getFragmentName());
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.addToBackStack(null);
			transaction.commit();

		} catch (Exception e) { // (ParseException e)

			e.printStackTrace();
		}
	}
}
