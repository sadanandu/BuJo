package com.example.bujo.activity;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.bujo.R;
import com.example.bujo.model.Event;
import com.example.bujo.util.BujoDbHandler;

public class AddEvent extends Activity {

	public static Boolean EVENT_CREATED = false;
	public Event currentEvent;
	public EditText eventName;
	public EditText eventDescription;
//	public DatePicker eventDatePicker;
//	public TimePicker eventTimePicker;
//	public int year, month, day, hour, minute;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		// Show the Up button in the action bar.
		EVENT_CREATED = false;
		eventName = (EditText) findViewById(R.id.add_event_name);
		eventDescription = (EditText) findViewById(R.id.add_event_description);
//		eventDatePicker = (DatePicker) findViewById(R.id.add_event_datePicker);
//		eventTimePicker = (TimePicker) findViewById(R.id.add_event_timePicker);
		eventName.requestFocus();
		ConfigureTextChangedListener(eventName);
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void ConfigureTextChangedListener(EditText editText){
		editText.addTextChangedListener(new TextWatcher(){
    		@Override
    		public void afterTextChanged(Editable s){
    			if (s.length() > 0){
    				EVENT_CREATED = true;    				
    			} 
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		return true;
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
			AddEventToDB(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void AddEventToDB(View view){
		if (EVENT_CREATED == true){
			String name = eventName.getText().toString();
			String desc = eventDescription.getText().toString();
//			long millisecondsToReminder = 0;
			long millisecondsToCreated = 0;
//			year = eventDatePicker.getYear();
//			month = eventDatePicker.getMonth() + 1;
//			day = eventDatePicker.getDayOfMonth();
//			hour = eventTimePicker.getCurrentHour();
//			minute = eventTimePicker.getCurrentMinute();			
			try {
				Calendar cal = Calendar.getInstance();
				Date dateCreated = cal.getTime();
				millisecondsToCreated = dateCreated.getTime();
//				String dateToParse = day + "-" + month + "-" + year + " " + hour + ":" + minute;
//				SimpleDateFormat dateFormatter = new SimpleDateFormat("d-M-yyyy hh:mm");
//				Date date = dateFormatter.parse(dateToParse);
//				millisecondsToReminder = date.getTime();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // You will need try/catch around this

			BujoDbHandler dbHandler = new BujoDbHandler(view.getContext());
			currentEvent = new Event(name, desc, millisecondsToCreated);
			dbHandler.addEvent(currentEvent);
		}
	}

	
}
