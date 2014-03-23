package com.example.bujo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note extends Bullet implements Parcelable{
	
	public Note() {
		// TODO Auto-generated constructor stub
	}
	
	public Note(String noteName, String noteDescription, long date){
		super();
		this.name = noteName;
		this.description = noteDescription;		
		this.date = date;
	}
	
	public Note(Parcel in){
		String[] data = new String[4];
		in.readStringArray(data);
		this._id = Integer.valueOf(data[0]);
		this.name = data[1];
		this.description = data[2];
		this.date = Long.valueOf(data[3]);

	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeStringArray(new String[] {Integer.toString(this._id), this.name, this.description, Long.toString(this.date)});
    }

	public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
		public Note createFromParcel(Parcel in){
			return new Note(in);			
		}
		
		public Note[] newArray(int size){
			return new Note[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


}
