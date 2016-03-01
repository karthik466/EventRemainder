package com.karthik.example.calender.adepters;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.example.calender.R;
import com.karthik.example.calender.beans.AllEventsBean;
import com.karthik.example.calender.utilites.Utils;

public class CustomAllEventsAdapter extends BaseExpandableListAdapter {
     Context mContext;
     ArrayList<AllEventsBean> list=new ArrayList<AllEventsBean>();
	public CustomAllEventsAdapter(Context context,ArrayList<AllEventsBean> list) {
		// TODO Auto-generated constructor stub
		this.mContext= context;
		this.list=list;
		
	}	

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getListData().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getListData().get(childPosition).getId();
	}

	@SuppressLint("InflateParams") @Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {				
		// TODO Auto-generated method stub
		
		if(convertView==null)
	       {
	    	   convertView=LayoutInflater.from(mContext)
	    			   .inflate(R.layout.allevents_single_item, null, false);
	       }
	       LinearLayout layout= (LinearLayout) convertView.findViewById(R.id.allevents_root_layout);
	       TextView mTime=(TextView) convertView.findViewById(R.id.alltime_text);
	       TextView mTitle=(TextView) convertView.findViewById(R.id.allevent_title_txt);
	       layout.setBackgroundResource(R.color.black);
	       mTitle.setTextColor(mContext.getResources().getColor(R.color.white));
	       mTitle.setText(list.get(groupPosition).getListData().get(childPosition).getTitle());
	       int h= list.get(groupPosition).getListData().get(childPosition).getTime_hr();
	       int m= list.get(groupPosition).getListData().get(childPosition).getTime_min();
	       mTime.setTextColor(mContext.getResources().getColor(R.color.white));
	       mTime.setText(Utils.createTimeStamp(h, m));
	       
	       
	       return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		  int i = 0;
	        try {
	        i = list.get(groupPosition).getListData().size();

	        } catch (Exception e) {
	        }

	        return i;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		  return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getmId();
	}

	@SuppressLint("InflateParams") @Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	       if(convertView==null)
	       {
	    	   convertView=LayoutInflater.from(mContext)
	    			   .inflate(R.layout.allevents_single_item, null, false);
	       }
	       LinearLayout layout= (LinearLayout) convertView.findViewById(R.id.allevents_root_layout);
	       TextView mTime=(TextView) convertView.findViewById(R.id.alltime_text);
	       TextView mTitle=(TextView) convertView.findViewById(R.id.allevent_title_txt);
	       layout.setBackgroundResource(R.color.purple);
	       mTime.setVisibility(View.GONE);
	       mTitle.setTextColor(mContext.getResources().getColor(R.color.white));
	       String title=list.get(groupPosition).getmTitle();
	       String[] splits= title.split("-");
	       int d=Integer.parseInt(splits[2]);
	       int m=Integer.parseInt(splits[1]);
	       int y=Integer.parseInt(splits[0]);
	       String date="";
	      switch(d)
	      {
	      case 1: date= "1 st "; break;
	      case 2: date= "2 nd "; break;
	      case 3: date= "3 rd "; break;
	      default : date= d+" th "; break;
	      
	      }
	       mTitle.setText(date + Utils.MONTH_NAMES[m-1].substring(0, 3)+ " " + y);
	       
	        ExpandableListView exListView=(ExpandableListView) parent;
	        exListView.expandGroup(groupPosition);
	        
	        
	        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	/*@SuppressLint({ "ResourceAsColor", "RtlHardcoded" })
	public TextView getGenericView() {
        // Layout parameters for the ExpandableListView
        @SuppressWarnings("deprecation")
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT, 64);

        TextView textView = new TextView(mContext);
        textView.setLayoutParams(lp);
        // Center the text vertically
        textView.setGravity(Gravity.CENTER);
        //textView.setTextColor(android.R.color.black);
        // Set the text starting position
        textView.setPadding(36, 0, 0, 0);
        return textView;
    }
	*/
	
	
}