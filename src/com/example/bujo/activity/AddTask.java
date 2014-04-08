package com.example.bujo.activity;


import static java.lang.System.out;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.bujo.R;
import com.example.bujo.model.Task;
import com.example.bujo.util.BujoDbHandler;


public class AddTask extends Activity {

	public static Boolean TASK_CREATED = false;
	public Task currentTask;
	public EditText taskName;
	public EditText taskDescription;
//	public DatePicker taskDatePicker;
//	public TimePicker taskTimePicker;
//	public int year, month, day, hour, minute;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		TASK_CREATED = false;
		// Show the Up button in the action bar.
		taskName = (EditText) findViewById(R.id.add_task_name);
    	taskDescription = (EditText) findViewById(R.id.add_task_description);
//    	taskDatePicker = (DatePicker) findViewById(R.id.add_task_datePicker);
//    	taskTimePicker = (TimePicker) findViewById(R.id.add_task_timePicker);
    	taskName.requestFocus();
    	ConfigureTextChangedListener(taskName);
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
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}

	public void ConfigureTextChangedListener(EditText editText){
		editText.addTextChangedListener(new TextWatcher(){
    		@Override
    		public void afterTextChanged(Editable s){
    			if (s.length() > 0){
    				TASK_CREATED = true;    				
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
	
//	   private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
//		   
//	        // when dialog box is closed, below method will be called.
//	        @Override
//	        public void onDateSet(DatePicker view, int selectedYear,
//	                int selectedMonth, int selectedDay) {
//	             
//	            year  = selectedYear;
//	            month = selectedMonth;
//	            day   = selectedDay;
//	 
//	            // Show selected date
//	            
//	            Output.setText(new StringBuilder().append(month + 1)
//	                    .append("-").append(day).append("-").append(year)
//	                    .append(" "));
//	     
//	           }
//	        };
	
	
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
			AddTaskToDB(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void AddTaskToDB(View view){
		//Intent intent = new Intent(this, Today.class);
		if (TASK_CREATED == true){
			String name = taskName.getText().toString();
			String desc = taskDescription.getText().toString();
//			long millisecondsToReminder = 0;
			long millisecondsToCreated = 0;
//			year = taskDatePicker.getYear();
//			month = taskDatePicker.getMonth() + 1;
//			day = taskDatePicker.getDayOfMonth();
//			hour = taskTimePicker.getCurrentHour();
//			minute = taskTimePicker.getCurrentMinute();
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

			BujoDbHandler taskHandler = new BujoDbHandler(view.getContext());
			currentTask = new Task(name, desc, millisecondsToCreated);
			taskHandler.addTask(currentTask);
		}
	}
	
}
