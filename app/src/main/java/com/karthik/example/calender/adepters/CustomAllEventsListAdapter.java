package com.karthik.example.calender.adepters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.example.calender.R;
import com.karthik.example.calender.beans.AllEventsBean;
import com.karthik.example.calender.beans.RowItem;
import com.karthik.example.calender.utilites.Utils;

public class CustomAllEventsListAdapter  extends BaseAdapter {
	 Context mContext;
     ArrayList<AllEventsBean> list=new ArrayList<AllEventsBean>();
	
	
	public CustomAllEventsListAdapter(Context context,
			ArrayList<AllEventsBean> listData) {
		this.mContext= context;
		this.list=listData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null)
	       {
	    	   convertView=LayoutInflater.from(mContext)
	    			   .inflate(R.layout.allevents_single_item, null, false);
	       }
	       View  v=  convertView.findViewById(R.id.allevents_root_layout);
	       TextView mTime=(TextView) convertView.findViewById(R.id.alltime_text);
	       TextView mTitle=(TextView) convertView.findViewById(R.id.allevent_title_txt);
	       LinearLayout ll= (LinearLayout) convertView.findViewById(R.id.allevent_list_child_layout);
	       v.setBackgroundResource(R.color.purple);
	       mTime.setVisibility(View.GONE);
	       mTitle.setTextColor(mContext.getResources().getColor(R.color.white));
	       String title=list.get(position).getmTitle();
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
	        
	        setChildView(ll,list.get(position).getListData()) ;
	        
	      return convertView;
	}

	private void setChildView(LinearLayout mLayout, List<RowItem> data) {
		// TODO Auto-generated method stub
		
		//Clearing and settingVisibilty
		
		mLayout.removeAllViews();
		mLayout.setVisibility(View.VISIBLE);
	       for(int i=0;i<data.size();i++){    	   
	      
	    	  View  v=LayoutInflater.from(mContext)
	    			   .inflate(R.layout.allevents_single_item, null, false);
	      
	      
	       RowItem item=data.get(i);
	       LinearLayout layout= (LinearLayout) v.findViewById(R.id.allevents_root_layout);
	       TextView mTime=(TextView) v.findViewById(R.id.alltime_text);
	       TextView mTitle=(TextView) v.findViewById(R.id.allevent_title_txt);
	       layout.setBackgroundResource(R.color.black);
	       mTitle.setTextColor(mContext.getResources().getColor(R.color.white));
	       mTitle.setText(item.getTitle());
	       int h= item.getTime_hr();
	       int m=item.getTime_min();
	       mTime.setTextColor(mContext.getResources().getColor(R.color.white));
	       mTime.setText(Utils.createTimeStamp(h, m));
	       
	       mLayout.addView(v);	       
	       }
	}

}
