package com.example.bujo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task extends Bullet implements Parcelable{
	private String taskName;
	private String taskDescription;
	private String isDone;

	public Task(){}
	
	public Task(String taskName, String taskDescription, long date){
		super();
		this.taskName = taskName;
		this.taskDescription = taskDescription;		
		this.date = date;
		this.isDone = "No";
	}
	
	public Task(Parcel in){
		String[] data = new String[5];
		in.readStringArray(data);
		this._id = Integer.valueOf(data[0]);
		this.taskName = data[1];
		this.taskDescription = data[2];
		this.date = Long.valueOf(data[3]);
		this.isDone = data[4];
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeStringArray(new String[] {Integer.toString(this._id), this.taskName, this.taskDescription, Long.toString(this.date), this.isDone});
    }

	public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
		public Task createFromParcel(Parcel in){
			return new Task(in);			
		}
		
		public Task[] newArray(int size){
			return new Task[size];
		}
	}; 
	
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
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
