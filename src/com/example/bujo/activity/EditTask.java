package com.example.bujo.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static java.lang.System.out;
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

import com.example.bujo.R;
import com.example.bujo.model.Task;
import com.example.bujo.util.ApplicationConstants;
import com.example.bujo.util.BujoDbHandler;

public class EditTask extends Activity{

	public Task currentTask;
	public EditText taskName;
	public EditText taskDescription;
//	public DatePicker taskDatePicker;
//	public TimePicker taskTimePicker;
//	public int year, month, day, hour, minute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		// Show the Up button in the action bar.
		taskName = (EditText) findViewById(R.id.edit_task_name);
    	taskDescription = (EditText) findViewById(R.id.edit_task_description);
//    	taskDatePicker = (DatePicker) findViewById(R.id.edit_task_datePicker);
//    	taskTimePicker = (TimePicker) findViewById(R.id.edit_task_timePicker);
    	
    	ConfigureTextChangedListener(taskName);
    	ConfigureTextChangedListener(taskDescription);
    	currentTask = (Task) this.getIntent().getParcelableExtra(ApplicationConstants.TASK_OBJECT);
    	
    	if (currentTask != null){
    		taskName.setText(currentTask.getName());
    		taskDescription.setText(currentTask.getDescription());
    		Calendar cal = Calendar.getInstance();
    		cal.setTimeInMillis(currentTask.getCreateDate());
//    		taskDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(cal.DATE));
//    		taskTimePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
//    		taskTimePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
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
		getMenuInflater().inflate(R.menu.add_task, menu);
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
			SaveTask(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void SaveTask(View view){
		//Intent intent = new Intent(this, Today.class);
		String name = taskName.getText().toString();
		String desc = taskDescription.getText().toString();
//		long milliseconds = 0;
//		year = taskDatePicker.getYear();
//		month = taskDatePicker.getMonth() + 1;
//		day = taskDatePicker.getDayOfMonth();
//		hour = taskTimePicker.getCurrentHour();
//		minute = taskTimePicker.getCurrentMinute();
//		try {
//			String toParse = day + "-" + month + "-" + year + " " + hour + ":" + minute; // Results in "2-5-2012 20:43"
//			SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm"); // I assume d-M, you may refer to M-d for month-day instead.
//			Date date = formatter.parse(toParse);
//			milliseconds = date.getTime();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // You will need try/catch around this

		BujoDbHandler taskHandler = new BujoDbHandler(view.getContext());
		currentTask.setName(name);
		currentTask.setDescription(desc);
		taskHandler.saveTask(currentTask);
	}
}
