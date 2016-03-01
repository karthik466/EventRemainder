package com.karthik.example.calender.beans;

import java.util.Date;




public class RowItem {   
	private int mId;
    private String mTitle;
    private String mNote;   
    private String mDate;
    private int mTime_H;
    private int mTime_Min;
    private Date mTime; 
    private int mChk_Id;
    private int mTheme_Id;
    private int mRemianderTime; 
    private byte[] imageData;
    public void setmRemianderTime(int mRemianderTime) {
		this.mRemianderTime = mRemianderTime;
	}

	public RowItem()
    { 
    	
    } 
    
   /* public RowItem(int id,String title,String msg,
    		String date,int  time_hr,int time_min,
    		int checkboxId,int themeId,byte[] image)
    {
    	this.mId=id;
    	this.mTitle=title;
    	this.mNote=msg;
    	this.mDate=date;
    	this.mTime_H=time_hr;
    	this.mTime_Min=time_min;
    	this.mChk_Id= checkboxId;
    	this.mTheme_Id=themeId;
    	this.imageData=image;
    }*/
    
    public int getTime_hr() {
		return mTime_H;
	}

	public void setTime_hr(int time_hr) {
		this.mTime_H = time_hr;
	}

	public int getTime_min() {
		return mTime_Min;
	}

	public void setTime_min(int time_min) {
		this.mTime_Min = time_min;
	}	
    
      
    

	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		this.mTitle = title;
	}
	public String getMsg() {
		return mNote;
	}
	public void setMsg(String msg) {
		this.mNote = msg;
	}	
	public String getDate() {
		return mDate;
	}
	public void setDate(String date) {
		this.mDate = date;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public Date getmTime() {
		return mTime;
	}

	public void setmTime(Date mTime) {
		this.mTime = mTime;
	}

	public int getmChk_Id() {
		return mChk_Id;
	}

	public void setmChk_Id(int mChk_Id) {
		this.mChk_Id = mChk_Id;
	}

	public int getmTheme_Id() {
		return mTheme_Id;
	}

	public void setmTheme_Id(int mTheme_Id) {
		this.mTheme_Id = mTheme_Id;
	}   

	public int getmRemianderTime() {
		return mRemianderTime;
	}
	
	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	/*public String getJsonString()
	{
		JSONObject output=new JSONObject();
		try{	
			output.put("message", "O");
			output.put("id", this.getId());
			output.put("title", this.getTitle());
			output.put("msg", this.getMsg());
			output.put("date", this.getDate());
			//output.put("time", this.getmTime());
			output.put("time_hr", this.getTime_hr());			
			output.put("time_min", this.getTime_min());
			output.put("reminder", this.getmRemianderTime());
			output.put("chk_id", this.getmChk_Id());
			output.put("theme_id", this.getmTheme_Id());
			output.put("img", new String(getImageData()));
		}
		catch (JSONException ex)
		{
			ex.printStackTrace();
		}		
		//Log.d(RowItem.class.getName(), ""+ output.toString());
		
		return output.toString();
	}*/

	
}
