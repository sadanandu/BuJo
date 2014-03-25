package com.example.bujo.activity;

import com.example.bujo.R;
import com.example.bujo.R.layout;
import com.example.bujo.R.menu;
import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.util.ApplicationConstants;
import com.example.bujo.util.BujoDbHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class EditEvent extends Activity {

	public Event currentEvent;
	public EditText eventName;
	public EditText eventDescription;
	public long date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		// Show the Up button in the action bar.
		eventName = (EditText) findViewById(R.id.add_event_name);
		eventDescription = (EditText) findViewById(R.id.add_event_description);
		eventName.requestFocus();
    	ConfigureTextChangedListener(eventName);
    	ConfigureTextChangedListener(eventDescription);
    	currentEvent = (Event) this.getIntent().getParcelableExtra(ApplicationConstants.EVENT_OBJECT);
    	
    	if (currentEvent != null){
    		eventName.setText(currentEvent.getName());
    		eventDescription.setText(currentEvent.getDescription());
    	}

		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_event, menu);
		return true;
	}

	public void ConfigureTextChangedListener(EditText editText){
		editText.addTextChangedListener(new TextWatcher(){
    		@Override
    		public void afterTextChanged(Editable s){
    		}

    		@Override
    		public void onTextChanged(CharSequence s, int start, int before, int count){
    		}
    		
    		@Override
    		public void beforeTextChanged(CharSequence s, int start, int count, int after){
    		}
    		
    	});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			SaveEvent(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void SaveEvent(View view){
		String name = eventName.getText().toString();
		String desc = eventDescription.getText().toString();
		BujoDbHandler dbHandler = new BujoDbHandler(view.getContext());
		currentEvent.setName(name);
		currentEvent.setDescription(desc);
		dbHandler.saveEvent(currentEvent);
	}

	
}
