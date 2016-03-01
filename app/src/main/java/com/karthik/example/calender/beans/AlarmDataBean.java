package com.karthik.example.calender.beans;

public class AlarmDataBean {
int hr,min,alarmType;
String title,message;
int id;
boolean triggered;
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
//String mAm_Pm;
public int getHr() {
	return hr;
}
public void setHr(int hr) {
	this.hr = hr;
}
public int getMin() {
	return min;
}
public void setMin(int min) {
	this.min = min;
}
/*public String getmAm_Pm() {
	return mAm_Pm;
}
public void setmAm_Pm(String mAm_Pm) {
	this.mAm_Pm = mAm_Pm;
}*/
public int getAlarmType() {
	return alarmType;
}
public void setAlarmType(int alarmType) {
	this.alarmType = alarmType;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public boolean isTriggered() {
	return triggered;
}
public void setTriggered(boolean triggered) {
	this.triggered = triggered;
}
}
