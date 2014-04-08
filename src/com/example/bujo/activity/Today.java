package com.example.bujo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bujo.R;
import com.example.bujo.fragment.BulletFragment;
import com.example.bujo.fragment.BulletFragment.OnFragmentInteractionListener;
import com.example.bujo.model.Bullet;
import com.example.bujo.util.BuJoDbHelper;
import com.example.bujo.util.BujoDbHandler;
import com.example.bujo.util.ListViewAdapter;

public class Today extends FragmentActivity  implements View.OnTouchListener, OnFragmentInteractionListener, ActionBar.OnNavigationListener {

	public static BuJoDbHelper dbHelper;
	public BulletFragment firstFragment;
	public Bundle InstanceState;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		InstanceState = savedInstanceState;
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// Specify a SpinnerAdapter to populate the dropdown list.
		String[] navigationOptions = new String[] {"Today", "Yesterday", "Tomorrow", "Custom Date"};
		ArrayAdapter <String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.layout_spinner, navigationOptions);
		actionBar.setListNavigationCallbacks(spinnerAdapter, this);

//		dbHelper  = new BuJoDbHelper(getApplicationContext());
//		if(findViewById(R.id.fragment_container) != null){
//			if(savedInstanceState != null){
//				return;
//			}
//			firstFragment = new BulletFragment();
//			firstFragment.setArguments(getIntent().getExtras());
//			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
//		}
//		
//		GetTodaysBullets();
	}

	public void GetTodaysBullets(){
		new PopulateJournalEntries().execute(this);
	}

	private class PopulateJournalEntries extends AsyncTask<Context, Void, ArrayList<Bullet>>{

		@Override
		protected ArrayList<Bullet> doInBackground(Context... contexts) {
			// TODO Auto-generated method stub
			BujoDbHandler dbHandler = new BujoDbHandler(contexts[0]);
			ArrayList<Bullet> bullets = new ArrayList<Bullet>();
			bullets = dbHandler.getTodayTasks();
			bullets.addAll(dbHandler.getTodayNotes());
			bullets.addAll(dbHandler.getTodayEvents());
			return GetSorted(bullets);
		}
		
		protected void onPostExecute(ArrayList<Bullet> bullets){
			BulletFragment fragment = (BulletFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
			fragment.setListAdapter(new ListViewAdapter(bullets, Today.this, fragment));
			//setListAdapter(new ListViewAdapter(bullets, getActivity(), BulletFragment.this));
		}
	}

	public ArrayList<Bullet> GetSorted(ArrayList<Bullet> bullets){
		Collections.sort(bullets, new Comparator<Bullet>() {
			@Override
			public int compare(Bullet bullet1, Bullet bullet2){
				return (((Long)bullet1.getCreateDate()).compareTo((Long)bullet2.getCreateDate()));
			}
		});
		return bullets;
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

//	  @Override
//	    public void onConfigurationChanged(Configuration newConfig) {
//	      super.onConfigurationChanged(newConfig);
//	      setContentView(R.layout.activity_today);
//	    }
	
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

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
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
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		dbHelper  = new BuJoDbHelper(getApplicationContext());
		if(findViewById(R.id.fragment_container) != null){
			if(InstanceState != null){
				return false;
			}
			firstFragment = new BulletFragment();
			firstFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
		}
		
		GetTodaysBullets();
		Toast.makeText(getApplicationContext(), "Showing today", Toast.LENGTH_SHORT).show();
		return false;
	}

}



