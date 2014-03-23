package com.example.bujo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubTask implements Parcelable{

	private String subTaskName, subTaskDescription , isDone;
    private int _id, parentTask;
	private long date;
	
	
	public SubTask(){}
	
	public SubTask(String subTaskName, String subTaskDescription, int parentTask, long date){
		super();
		this.subTaskName = subTaskName;
		this.subTaskDescription = subTaskDescription;
		this.parentTask = parentTask;
		this.date = date;
		this.isDone = "No";
	}
	
	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getParentTask() {
		return parentTask;
	}

	public void setParentTask(int parentTask) {
		this.parentTask = parentTask;
	}

	public SubTask(Parcel in){
		String[] data = new String[6];
		in.readStringArray(data);
		this._id = Integer.valueOf(data[0]);
		this.subTaskName = data[1];
		this.subTaskDescription = data[2];
		this.parentTask = Integer.valueOf(data[3]);
		this.date = Long.valueOf(data[4]);
		this.isDone = data[5];
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeStringArray(new String[] {Integer.toString(this._id), this.subTaskName, this.subTaskDescription, Integer.toString(this.parentTask), Long.toString(this.date), this.isDone});
    }

	public static final Parcelable.Creator<SubTask> CREATOR = new Parcelable.Creator<SubTask>() {
		public SubTask createFromParcel(Parcel in){
			return new SubTask(in);			
		}
		
		public SubTask[] newArray(int size){
			return new SubTask[size];
		}
	}; 
	
	
	public String getSubTaskName() {
		return subTaskName;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}

	public String getSubTaskDescription() {
		return subTaskDescription;
	}

	public void setSubTaskDescription(String subTaskDescription) {
		this.subTaskDescription = subTaskDescription;
	}

	public String getIsDone() {
		return isDone;
	}

	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
