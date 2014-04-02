package com.example.bujo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event extends Bullet implements Parcelable {
	private long reminderDate;
	
	public Event() {
		// TODO Auto-generated constructor stub
	}

	public Event(String eventName, String eventDescription, long date, long reminderDate){
		super();
		this.name = eventName;
		this.description = eventDescription;		
		this.createDate = date;
		this.setReminderDate(reminderDate);
	}
	
	public Event(Parcel in){
		String[] data = new String[5];
		in.readStringArray(data);
		this._id = Integer.valueOf(data[0]);
		this.name = data[1];
		this.description = data[2];
		this.createDate = Long.valueOf(data[3]);
		this.reminderDate = Long.valueOf(data[4]);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeStringArray(new String[] {Integer.toString(this._id), this.name, this.description, Long.toString(this.createDate), Long.toString(this.reminderDate)});
    }

	public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
		public Event createFromParcel(Parcel in){
			return new Event(in);			
		}
		
		public Event[] newArray(int size){
			return new Event[size];
		}
	};

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
