package com.example.bujo.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import static java.lang.System.out;
import com.example.bujo.R;
import com.example.bujo.fragment.BulletFragment;
import com.example.bujo.fragment.BulletFragment.OnFragmentInteractionListener;
import com.example.bujo.model.Bullet;
import com.example.bujo.model.DataBaseTable;
import com.example.bujo.util.BujoDbHandler;
import com.example.bujo.util.SearchResultsAdapter;


public class SearchResultsActivity extends FragmentActivity implements View.OnTouchListener, OnFragmentInteractionListener {

	public DataBaseTable virtualDb;
	public BulletFragment firstFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
//		virtualDb = new DataBaseTable(this);
		if(findViewById(R.id.fragment_container) != null){
			if(savedInstanceState != null){
				return;
			}
			firstFragment = new BulletFragment();
			firstFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment, "firstFragment").commit();
		}
//		virtualDb.DeleteBulletEntries();
//		virtualDb.loadBulletEntries();

		handleIntent(getIntent());
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent){
		setIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent){
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			String query = intent.getStringExtra(SearchManager.QUERY);
			new PopulateSearchEntries().execute(query);
		}
	}
	
	private class PopulateSearchEntries extends AsyncTask<String, Void, Cursor>{

		@Override
		protected ArrayList <Bullet> doInBackground(String... queries) {
			// TODO Auto-generated method stub
			BujoDbHandler dbHandler = new BujoDbHandler(getApplicationContext());
			ArrayList<Bullet> bullets = new ArrayList<Bullet>();
//			Cursor tasks = dbHandler.getTasksWithName(queries[0]);
//			Cursor events = dbHandler.getEventsWithName(queries[0]);
//			Cursor notes = dbHandler.getNotesWithName(queries[0]);
//			Cursor []cursors = {tasks, events, notes};
//			MergeCursor mergeCursor = new MergeCursor(cursors);
//			out.println("No of rows");
//			out.println(Integer.toString(mergeCursor.getCount()));
			return bullets;
		}
		
		protected void onPostExecute(Cursor c){
			BulletFragment fragment = (BulletFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
			SearchResultsAdapter adapter = new SearchResultsAdapter(SearchResultsActivity.this, c, fragment);
			fragment.setListAdapter(adapter);
		}
	}
	
	


	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
