package com.karthik.example.calender.adepters;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.karthik.example.calender.R;
import com.karthik.example.calender.beans.RowItem;
import com.karthik.example.calender.database.DataBaseHandler;
import com.karthik.example.calender.utilites.Utils;
public class CustomListAdapter  extends BaseAdapter{
   Context ctx;
   String date;
   String TAG=CustomListAdapter.class.getSimpleName(); 
   DataBaseHandler handler;   
   List<RowItem> list;;
	public CustomListAdapter(Context  context,String date) {
		this.ctx=context;
		this.date=date;
		getListData(date);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub	
		
		return (list!=null ?list.size():0);		
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

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater) ctx
	                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.single_list_item, null);
	        }

	      
		    TextView txtTitle = (TextView) convertView.findViewById(R.id.text_title);
	        TextView txtMsg = (TextView) convertView.findViewById(R.id.text_msg);
	        TextView txtTime= (TextView) convertView.findViewById(R.id.text_time);

	        RowItem row_pos = list.get(position);
	        // setting the image resource and title	       
	        txtTitle.setText(row_pos.getTitle());
	        txtMsg.setText(row_pos.getMsg());
	        txtTime.setText(Utils.createTimeStamp(row_pos.getTime_hr(),
	        		row_pos.getTime_min()));
	        //Storing the CheckBok,RadioButon data
	        txtTitle.setTag(row_pos.getmChk_Id());
	       txtTime.setTag(row_pos.getmTheme_Id());
	       convertView.setTag(row_pos.getId());
	       // txtTitle.setTag(2);
	       // txtTime.setTag(1);
	       // imgIcon.setVisibility(View.GONE);
	        //convertView.setTag(row_pos.getJsonString());
	        /*convertView.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    Toast.makeText(ctx, "Pressed on Item "+ v.getTag() , Toast.LENGTH_SHORT)
	                .show();
				}
			});*/
	       

	        return convertView;
	}
	
	
	private void getListData(String date) {
		
		if(handler==null)
		{
			handler=new DataBaseHandler(ctx);
		}	  
		
		list=handler.selectRecords(date);
	  //Log.d("RowItem "," List Size"+ (list!=null ?list.size():0));
	  /*  for(RowItem i:list)
	    {
	    	Log.d(TAG," H:M -->"+ i.getTime_hr()+":"+ i.getTime_min());
	    }*/
		
		if(list!=null)
		{
		Collections.sort(list,new Comparator<RowItem>() {
			@Override
			public int compare(RowItem item1, RowItem item2) {
				/*Log.d(TAG, "Date 1:"+ item1.getmTime()
						+" ,Date2 :"+ item2.getmTime());*/
				return item1.getmTime().compareTo(item2.getmTime());
			}
		});
		}
		/*Log.d(TAG," After Sort");
	    for(RowItem i:list)
	    {
	    	Log.d(TAG," H:M -->"+ i.getTime_hr()+":"+ i.getTime_min());
	    }*/
		//list=handler.selectRecords();
	}
}
