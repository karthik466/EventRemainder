package com.karthik.example.calender;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.karthik.example.calender.beans.AlarmDataBean;
import com.karthik.example.calender.beans.RowItem;
import com.karthik.example.calender.database.DataBaseConstants;
import com.karthik.example.calender.database.DataBaseHandler;
import com.karthik.example.calender.service_recivers.SetRepeatAlarmService;
import com.karthik.example.calender.utilites.BoundTimePickerDialog;
import com.karthik.example.calender.utilites.Utils;

public class EventActivity extends ActionBarActivity implements OnTouchListener, OnClickListener {
   final static String TAG=EventActivity.class.getSimpleName();
   
   ImageView eventImage;
   EditText mEventTitle,mEventTime,mEventMsg;
   CheckBox mChkNotification,mChkAlarm;
   Spinner mReminderTime;
   RadioButton mProfessional,mPerson,mHoliday;
   Button mPhoto;
   LinearLayout mHeading;
   RadioGroup mThemeGroup;
   BoundTimePickerDialog mTimePicker;
   AlarmDataBean  timeBean;
   String mSelDate;
   int mNotifyID;
   DataBaseHandler handler;
   String remainderTime;
   int  recardId=0;
   int themeId;
   Context mContext;
   String rowId;
   SimpleSpinnerAdapter adapter;
   Uri mCapturedImageURI;
   final int PICK_GALLERY=1,PICK_CAMERA=2;
   byte[] image=null;
   ImageButton save,edit,delete;
   Animation zoomin,zoomout;
   Toolbar toolbar;
   TextView mToolBarText;
   
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_event);
		try{
		rowId=getIntent().getStringExtra("rowId").toString();
		init();
		setListeners();
		}
		catch(NullPointerException ex)
		{
			finish();
		}
		
		
		
		
	}
	private void init()
	{
		mContext=this;
		//Setting ToolBar Items
		toolbar= (Toolbar) findViewById(R.id.app_bar);
		mToolBarText= (TextView) findViewById(R.id.action_bar_title);		
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mEventTitle= (EditText) findViewById(R.id.event_title_edit);
		mEventTime= (EditText) findViewById(R.id.event_time_edit);
		mEventMsg= (EditText) findViewById(R.id.event_note_edit);
		
		mChkNotification= (CheckBox) findViewById(R.id.notification_type_chk);
		mChkAlarm= (CheckBox) findViewById(R.id.alarm_type_chk);
		mReminderTime= (Spinner) findViewById(R.id.spinner_time);
		mThemeGroup= (RadioGroup) findViewById(R.id.theme_type_rg);
		mProfessional= (RadioButton) findViewById(R.id.event_prof_radio);
		mPerson= (RadioButton) findViewById(R.id.event_personal_radio);
		mHoliday= (RadioButton) findViewById(R.id.event_holi_radio);
		
		save= (ImageButton) findViewById(R.id.save_btn);
	    delete= (ImageButton) findViewById(R.id.delete_btn);
	    edit= (ImageButton) findViewById(R.id.edit_btn);
		mPhoto= (Button) findViewById(R.id.event_uploadimg_btn);
		
		eventImage= (ImageView) findViewById(R.id.event_imageView);
		zoomin= AnimationUtils.loadAnimation(mContext, R.anim.anim_zoomin);
		zoomout= AnimationUtils.loadAnimation(mContext, R.anim.anim_zoomout);
		
	}
	
	private void setListeners() {
		mEventTime.setOnTouchListener(this);
		save.setOnClickListener(this);
		delete.setOnClickListener(this);
		edit.setOnClickListener(this);
		mPhoto.setOnClickListener(this);
		adapter=new SimpleSpinnerAdapter(mContext);
		mReminderTime.setAdapter(adapter);
		setDataToFields(rowId);
	}
	
	private void setDataToFields(String id) {
		// TODO Auto-generated method stub
		if(handler==null)
			handler=new DataBaseHandler(mContext);
		
		try{
			
				//JSONObject event=new JSONObject(jsonString);
				//Log.d(TAG,"JSON DATA"+ event.toString());
				
				if(rowId.equals("-1")){
					mSelDate= getIntent().getStringExtra("date").toString();
					recardId=0;
					edit.setVisibility(View.GONE);
					delete.setVisibility(View.GONE);					
					save.setVisibility(View.VISIBLE);
					save.startAnimation(zoomin);
					mToolBarText.setText("New Event");
				}
				else
				{	
					RowItem event= handler.getRowInfomation(rowId);
					setFieldEnable(false);
					mSelDate= event.getDate();
					recardId=Integer.parseInt(rowId);
					mEventTitle.setText(event.getTitle());
					mToolBarText.setText(event.getTitle());
					mEventMsg.setText(event.getMsg());
					setTime(event.getTime_hr(),event.getTime_min());
					int chk_id= event.getmChk_Id();
					int theme_id= event.getmTheme_Id();
					if(chk_id==0){
						mChkNotification.setChecked(true);
					}else if(chk_id==1){
						mChkAlarm.setChecked(true);
					}else{
							mChkNotification.setChecked(true);
							mChkAlarm.setChecked(true);						    
					}
					
					if(theme_id==0){
						mProfessional.setChecked(true);
					}else if(theme_id==1){
						mPerson.setChecked(true);
					}else{							
							mHoliday.setChecked(true);						    
					}
					
					//Settings Remainder Time
					int pos=adapter.getRemainderValue(new String()+event.getmRemianderTime());
					mReminderTime.setSelection(pos);
					//mSave.setText(getResources().getString(R.string.update_txt));
					
					//Setting Image Data
					image = event.getImageData();
					 
					 
					 if(image!=null){
							bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);				 
							 animationStart(bitmap,true);
							 }
							 else{
								 showOptions();
							 }
				}
				
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void showOptions() {
		// TODO Auto-generated method stub
		if(recardId>0){
			edit.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
			save.setVisibility(View.GONE);
			edit.startAnimation(zoomin);
			delete.startAnimation(zoomin);
			}
	}



	private void setFieldEnable(boolean bl)
	{
		mReminderTime.setEnabled(bl);
		mChkAlarm.setEnabled(bl);
		mChkNotification.setEnabled(bl);
		mEventMsg.setEnabled(bl);
		mEventTime.setEnabled(bl);
		mEventTitle.setEnabled(bl);
		mPerson.setEnabled(bl);
		mProfessional.setEnabled(bl);
		mHoliday.setEnabled(bl);
		mPhoto.setEnabled(bl);
		
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			showTimePickerDialog();
		}
		return false;
	}
	

	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		 switch(view.getId())
		 {
		   case R.id.save_btn:
			   if(mChkNotification.isChecked() && mChkAlarm.isChecked()){
					mNotifyID=2;
				}
				else if(mChkAlarm.isChecked())
				{
					mNotifyID=1;
				}
				else
				{
					mNotifyID=0;
				}
			   if(mProfessional.isChecked())
				{
					themeId=0;
				}
			   else if(mPerson.isChecked() ){
				   themeId=1;
				}
				else
				{
					themeId=2;
				}
			   
			   
			   
			   if(!(((RemainderTime) mReminderTime.getSelectedItem()).getmValue().toString()).equalsIgnoreCase("Select"))
			   {
				  remainderTime=((RemainderTime) mReminderTime.getSelectedItem()).getmValue().toString();
			   }
			   if(!mEventTitle.getText().toString().isEmpty()
					   && !mEventTime.getText().toString().isEmpty())
			   {
				   
				  ContentValues mValues=new ContentValues();			   
				   mValues.put(DataBaseConstants.TITLE, mEventTitle.getText().toString().trim());
				   mValues.put(DataBaseConstants.MSG,mEventMsg.getText().toString());
				   mValues.put(DataBaseConstants.DATE,mSelDate);			  
				   mValues.put(DataBaseConstants.TIME_HR, timeBean.getHr());
				   mValues.put(DataBaseConstants.TIME_M, timeBean.getMin());
				   mValues.put(DataBaseConstants.REMAIN_TYPE, mNotifyID);
				   mValues.put(DataBaseConstants.REMAIN_THEME, themeId);
				   mValues.put(DataBaseConstants.REMAIN_NOTIFY_TIME,
						   					remainderTime);
				   mValues.put(DataBaseConstants.EVENT_IMAGE,
		   					image);
				  // mValues.put(DataBaseConstants.TIME_AM_PM, time.getmAm_Pm());
				  
				int[] date=Utils.getTodayDate();
				
				
				StringBuilder builder=new StringBuilder();
				builder.append(date[Utils.YEAR]).append("-")
				.append(String.format("%02d", (date[Utils.MONTH]+1))).append("-")
				.append(String.format("%02d", date[Utils.DAY]));
				
				int mDbIndex=(int) handler.insertOrUpdateRecord(mValues,recardId); 
			    if(mDbIndex==1)
			    {
			    	startService(new Intent(EventActivity.this,SetRepeatAlarmService.class));
			    }
			    else if(builder.toString().compareTo(mSelDate)==0) 			    
			    {
			    	Utils.setReminder(handler.getAlamData(mDbIndex),
			    			mContext,mDbIndex);
			    }
			    finish();
			   }
			   else
			   {
				   Toast.makeText(EventActivity.this, "Event cann't create with empty fields"
						   , Toast.LENGTH_SHORT).show();
			   }
			   
			   break;
		   case R.id.cancel_btn:
			   super.onBackPressed();
			   break;
		   case R.id.event_uploadimg_btn:			  
			   uploadPic();
			   break;
		   case R.id.edit_btn:
			   setFieldEnable(true);
			   edit.startAnimation(zoomout);
			   save.startAnimation(zoomin);	
			   save.setVisibility(View.VISIBLE);
			   edit.setVisibility(View.GONE);
			   
			   break;
		   case R.id.delete_btn:   
			   showDeleteEventDialog();
			   break;
		 }
	}

	private void showDeleteEventDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Do you want to delete Event ?");
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(handler==null)
							handler=new DataBaseHandler(mContext);
						
						handler.deleteRecord(recardId);
						finish();
					}
				});

		builder.show();
	}







	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
	        return true;
			
			
		}
			return super.onOptionsItemSelected(item);
		}
	private void uploadPic()
	{
		 AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
		   
		   ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_item);
		   adapter.add("Gallery");
		   adapter.add("Camera");
		   
		   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		   
		   builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				  switch (which) {
				  case 0:
						Intent intent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						intent.setType("image/*");
						intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
						startActivityForResult(
								Intent.createChooser(intent, ""), PICK_GALLERY);
						break;
					case 1:
						String fileName = "img.jpg";
						ContentValues values = new ContentValues();
						values.put(MediaStore.Images.Media.TITLE, fileName);
						mCapturedImageURI = getContentResolver()
								.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										values);
						Intent camera = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						camera.putExtra(MediaStore.EXTRA_OUTPUT,
								mCapturedImageURI);
						startActivityForResult(camera, PICK_CAMERA);
						break;
				  }
			}
		});
		   
		   builder.setCancelable(false);
		   builder.show();
	}
	
	String IMAGE_NAME;
    String selectedImagePath=null;
    Bitmap bitmap=null;
	@SuppressWarnings("deprecation")
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
		   if(resultCode==RESULT_OK)
		   {
			   switch(requestCode)
			   {
			   case PICK_GALLERY:
				   
				 
					   Uri imgURI= data.getData();
				   Log.d(TAG, imgURI.getPath());
				   
				   if (imgURI.toString().startsWith("file:")) {

					   selectedImagePath = imgURI.toString()
								.replaceFirst("file://", "").trim();
						IMAGE_NAME = selectedImagePath.substring(
								selectedImagePath.lastIndexOf('/') + 1, selectedImagePath.length());

					} else {

						selectedImagePath = getPath(imgURI);

					}
				   Log.d(TAG, "Image Name :"+IMAGE_NAME + "," + selectedImagePath);
				   break;
				   
			   case PICK_CAMERA:
				   
				   if (mCapturedImageURI != null) {
						String[] projection = { MediaStore.Images.Media.DATA };
						Cursor cursor = managedQuery(mCapturedImageURI, projection,
								null, null, null);
						int column_index_data = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();

						selectedImagePath = cursor.getString(column_index_data);
						File imgFile = new File(selectedImagePath);
						IMAGE_NAME = imgFile.getName().trim();
						//Bitmap myBitmap1 = decodeFile(selectedImagePath);
						Log.d(TAG," Image "+ selectedImagePath);

					} 
				   
				   break;
			   }
			   
			   if(selectedImagePath!=null)
			   {
				   bitmap=BitmapFactory.decodeFile(selectedImagePath);
				   eventImage.setImageBitmap(bitmap);
				  
				    
				   ByteArrayOutputStream stream = new ByteArrayOutputStream();
				   bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				   image = stream.toByteArray();				   
				   animationStart(bitmap,false);
			   }
				   
		   }
		
		
			super.onActivityResult(requestCode, resultCode, data);
		}
	
	private void animationStart(Bitmap bmp,final  boolean animate) {
		// TODO Auto-generated method stub
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.anim_zoomin);
		 eventImage.startAnimation(anim);
		 eventImage.setImageBitmap(bmp);
		 anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {				//Show the Buttons
				
					if(animate)
					{
						showOptions();
					}
				
			}
		});
	}



	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
		cursor.moveToFirst();
		IMAGE_NAME = cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));

		return cursor.getString(column_index);
	}
	
	

	private void showTimePickerDialog() {
		// TODO Auto-generated method stub
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);   
        
		
		mTimePicker = new BoundTimePickerDialog(EventActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						//Log.d(TAG," H:MM -->"+ selectedHour+": "+selectedMinute);
						setTime(selectedHour,selectedMinute);								
					}
				}, hour, minute, false);// Yes 24 hour time
						
		mTimePicker.setTitle("Select Time");
		
		//Setting Minimum Time for DatePicker
		String dateFormat= "yyyy-MM-dd";
		
		if(mSelDate!=null){
		if(mSelDate.equals(
				DateFormat.format(dateFormat,
						mcurrentTime.getTime()))){
			Log.d(TAG," Same date Selected");
			mTimePicker.setMin(hour, minute);
		}
		else
			Log.d(TAG," Diff date Selected");
	    
		if(!mTimePicker.isShowing()){
		mTimePicker.show();
		}
		}
		//mTimePicker.show();
	}
	
	
	public void setTime(int h,int m)
	{
		  String am_pm = "";		
		  timeBean=new AlarmDataBean();
		  timeBean.setHr(h);
		  timeBean.setMin(m);	
			
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
			
			mEventTime.setText( String.format("%02d",h)+":"+ String.format("%02d",m)+" "+am_pm);  
			
			
			//time.setmAm_Pm(am_pm);		
	}
	
	class RemainderTime
	{
		String mValue;
		int mIndex;
		
		public RemainderTime(String value,int index)
		{
			this.mValue=value;
			this.mIndex=index;
		}
		public String getmValue() {
			return mValue;
		}
		public int getmIndex() {
			return mIndex;
		}
		
	}
	class SimpleSpinnerAdapter extends BaseAdapter
	{
		Context context;
	   List<RemainderTime> list;

		public SimpleSpinnerAdapter(Context mContext) {
			// TODO Auto-generated constructor stub
			this.context=mContext;
			list=getListData();
		}

		public int  getRemainderValue(String value) {
			// TODO Auto-generated method stub
			for(RemainderTime rt:list)
			{
				if(rt.getmValue().equals(value))
				{
					return rt.getmIndex();
				}
			}
			return 0;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return (list.isEmpty()?0:list.size());
			
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
			{
				convertView= LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
			}
			TextView text= (TextView) convertView.findViewById(R.id.spinner_text);
			text.setText(list.get(position).getmValue());
			text.setTag(list.get(position).getmIndex());
			return convertView;
		}
		
		private List<RemainderTime> getListData()
		{
			List<RemainderTime> data=new ArrayList<RemainderTime>();
			String[] intervals=new String[]{"Select","5","10","15","30","60"};
			for(int i=0;i<intervals.length;i++)
				data.add(new RemainderTime(intervals[i],i));			
			
			return data;
		}
	}
}
