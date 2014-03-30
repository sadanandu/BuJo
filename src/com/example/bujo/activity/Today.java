package com.example.bujo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bujo.R;
import com.example.bujo.fragment.BulletFragment;
import com.example.bujo.fragment.BulletFragment.OnFragmentInteractionListener;
import com.example.bujo.model.Bullet;
import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;
import com.example.bujo.util.ApplicationConstants;
import com.example.bujo.util.BuJoDbHelper;
import com.example.bujo.util.BujoDbHandler;
import com.example.bujo.util.ListViewAdapter;

public class Today extends FragmentActivity  implements View.OnTouchListener, OnFragmentInteractionListener{
	

	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 100;
	private GestureDetector gestureDetetctor;
	View.OnTouchListener gestureListener;
	public static BuJoDbHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		gestureDetetctor = new GestureDetector(getApplicationContext(), new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return gestureDetetctor.onTouchEvent(arg1);
			}
		};
		dbHelper  = new BuJoDbHelper(getApplicationContext());
		//DisplayBullets();
		if(findViewById(R.id.fragment_container) != null){
			if(savedInstanceState != null){
				return;
			}
			BulletFragment firstFragment = new BulletFragment();
			firstFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_add_task:
			new TaskCreator().execute();
			return true;
		case R.id.action_add_note:
			new NoteCreator().execute();
			//Toast.makeText(getApplicationContext(), "adding note", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_add_event:
			new EventCreator().execute();
			//Toast.makeText(getApplicationContext(), "adding event", Toast.LENGTH_SHORT).show();
			return true;
//		case R.id.action_search:
//			Toast.makeText(getApplicationContext(), "searching", Toast.LENGTH_SHORT).show();
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class TaskCreator extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void...voids) {
			Intent intent = new Intent(Today.this, AddTask.class);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
		
	}

	private class NoteCreator extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void...voids) {
			Intent intent = new Intent(Today.this, AddNote.class);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
		
	}

	private class EventCreator extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void...voids) {
			Intent intent = new Intent(Today.this, AddEvent.class);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
		
	}
	
	public class MyGestureDetector extends SimpleOnGestureListener{

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			try{
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE ){  //&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
					Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE ){ //&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY){
					Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
				}
				
			}catch(Exception e){
				
			}
			return false;
		}
		
		@Override
		public boolean onDown(MotionEvent e){
			return true;
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub
		
	}

}



