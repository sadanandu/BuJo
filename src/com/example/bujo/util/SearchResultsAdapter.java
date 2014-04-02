package com.example.bujo.util;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bujo.R;
import com.example.bujo.activity.Today;
import com.example.bujo.fragment.BulletFragment;

public class SearchResultsAdapter extends CursorAdapter{
	LayoutInflater inflater;
	public final Context context;
	private final BulletFragment fragment;

	public SearchResultsAdapter(Context context, Cursor c, BulletFragment fragment) {
		// TODO Auto-generated constructor stub
		super(context, c);
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.fragment = fragment;
	}

	@Override
	public void bindView(View view, Context context, Cursor c) {
		// TODO Auto-generated method stub
		TextView v1 = (TextView)view.findViewById(R.id.textView);
		v1.setText(c.getString(1));
		v1.setTag(c.getInt(0));
		v1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fragment.EditThisNote(v);
			}
		});
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_note, parent, false);
	}

}
