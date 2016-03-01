package com.karthik.example.calender.beans;

import java.util.List;

public class AllEventsBean {
    int mId;
	String mTitle;
	List<RowItem> listData;
	
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public List<RowItem> getListData() {
		return listData;
	}
	public void setListData(List<RowItem> listData) {
		this.listData = listData;
	}
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	
	
}
