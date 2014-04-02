package com.example.bujo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task extends Bullet implements Parcelable{
	private String isDone;
	private long reminderDate;
	public Task(){}
	
	public Task(String taskName, String taskDescription, long createDate, long reminderDate){
		super();
		this.name = taskName;
		this.description = taskDescription;		
		this.createDate = createDate;
		this.setReminderDate(reminderDate);
		this.isDone = "No";
	}
	
	public Task(Parcel in){
		String[] data = new String[6];
		in.readStringArray(data);
		this._id = Integer.valueOf(data[0]);
		this.name = data[1];
		this.description = data[2];
		this.createDate = Long.valueOf(data[3]);
		this.reminderDate = Long.valueOf(data[4]);
		this.isDone = data[5];
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeStringArray(new String[] {Integer.toString(this._id), this.name, this.description, Long.toString(this.createDate), Long.toString(this.reminderDate), this.isDone});
    }

	public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
		public Task createFromParcel(Parcel in){
			return new Task(in);			
		}
		
		public Task[] newArray(int size){
			return new Task[size];
		}
	}; 
	
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

	public long getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(long reminderDate) {
		this.reminderDate = reminderDate;
	}
}
