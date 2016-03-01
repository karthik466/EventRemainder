package com.karthik.example.calender;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.karthik.example.calender.adepters.CustomListAdapter;

public class MyListFragment extends ListFragment{
	CustomListAdapter adapter; 
	String date;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		    adapter = new CustomListAdapter(getActivity(),date);
	        setListAdapter(adapter);  
	        getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Log.d("TAG"," U Clicked Me !");
					Context context=getActivity();
					Intent intent = new Intent(context, EventActivity.class);
					intent.putExtra("rowId", view.getTag().toString());
					context.startActivity(intent);
				}
			});
	}
	public MyListFragment(String date) {
		// TODO Auto-generated constructor stub
		this.date=date;
	}
}
