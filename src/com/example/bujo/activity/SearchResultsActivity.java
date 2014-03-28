package com.example.bujo.activity;

import static java.lang.System.out;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.bujo.R;
import com.example.bujo.R.id;
import com.example.bujo.R.layout;
import com.example.bujo.R.menu;
import com.example.bujo.model.DataBaseTable;
import com.example.bujo.util.SearchResultsAdapter;


public class SearchResultsActivity extends Activity {

	public DataBaseTable virtualDb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		virtualDb = new DataBaseTable(this);
		handleIntent(getIntent());
		
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
		protected Cursor doInBackground(String... queries) {
			// TODO Auto-generated method stub
			Cursor c = virtualDb.getBulletMatches(queries[0], null);
			return c;
		}
		
		protected void onPostExecute(Cursor c){
			ListView list = (ListView) findViewById(R.id.listViewSearchResult);
			SearchResultsAdapter adapter = new SearchResultsAdapter(SearchResultsActivity.this, c);
			list.setAdapter(adapter);
//			c.moveToFirst();
//			if (c.getCount() > 0){
//				do{
//					out.println(c.getString(c.getColumnIndex(virtualDb.COL_NAME)) +"  " +  c.getString(c.getColumnIndex(virtualDb.COL_DESCRIPTION)));
//					
//				}while(c.moveToNext());
//			}
		}
	}

	
}
