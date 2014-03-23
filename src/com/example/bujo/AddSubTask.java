package com.example.bujo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;
import com.example.bujo.util.ApplicationConstants;

public class AddSubTask extends Activity {

	public AddSubTask() {
		// TODO Auto-generated constructor stub
	}
	public static Boolean SUB_TASK_CREATED = false;
	public SubTask currentSubTask;
	public Task currentTask;
	public EditText taskName;
	public EditText taskDescription;
	public DatePicker taskDatePicker;
	public TimePicker taskTimePicker;
	public int year, month, day, hour, minute;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_sub_task);
		SUB_TASK_CREATED = false;
		// Show the Up button in the action bar.
		taskName = (EditText) findViewById(R.id.add_sub_task_name);
    	taskDescription = (EditText) findViewById(R.id.add_sub_task_description);
    	taskDatePicker = (DatePicker) findViewById(R.id.add_sub_task_datePicker);
    	taskTimePicker = (TimePicker) findViewById(R.id.add_sub_task_timePicker);
    	taskName.requestFocus();
    	currentTask = (Task) this.getIntent().getParcelableExtra(ApplicationConstants.TASK_OBJECT);
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
		getMenuInflater().inflate(R.menu.add_sub_task, menu);
		return true;
	}

	public void ConfigureTextChangedListener(EditText editText){
		editText.addTextChangedListener(new TextWatcher(){
    		@Override
    		public void afterTextChanged(Editable s){
    			if (s.length() > 0){
    				SUB_TASK_CREATED = true;    				
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
			AddSubTaskToDB(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void AddSubTaskToDB(View view){
		//Intent intent = new Intent(this, Today.class);
		if (SUB_TASK_CREATED == true){
			String name = taskName.getText().toString();
			String desc = taskDescription.getText().toString();
			long milliseconds = 0;
			year = taskDatePicker.getYear();
			month = taskDatePicker.getMonth() + 1;
			day = taskDatePicker.getDayOfMonth();
			hour = taskTimePicker.getCurrentHour();
			minute = taskTimePicker.getCurrentMinute();
			try {
				String dateToParse = day + "-" + month + "-" + year + " " + hour + ":" + minute;
				SimpleDateFormat dateFormatter = new SimpleDateFormat("d-M-yyyy hh:mm");
				Date date = dateFormatter.parse(dateToParse);
				milliseconds = date.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // You will need try/catch around this

			BujoDbHandler taskHandler = new BujoDbHandler(view.getContext());
			currentSubTask = new SubTask(name, desc, currentTask.get_id(), milliseconds);
			taskHandler.addSubTask(currentSubTask);
		}
	}
	
}
