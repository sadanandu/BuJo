package com.example.bujo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import static java.lang.System.out;

public class SearchResultsActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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
			
			out.println("************************Query is****************************");
			out.println(query);
			//txtQuery.setText("Search Query: " + query);
			//Cursor c = db.getWordMatches(query, null);
		}
	}
	
	
}
