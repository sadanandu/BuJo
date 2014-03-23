package com.example.bujo.model;

public abstract class Bullet {
    public int _id;
    public String name;
    public long date;
    
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

	
}
