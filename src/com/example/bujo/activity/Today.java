package com.example.bujo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bujo.R;
import com.example.bujo.model.Bullet;
import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;
import com.example.bujo.util.ApplicationConstants;
import com.example.bujo.util.BuJoDbHelper;
import com.example.bujo.util.BujoDbHandler;
import com.example.bujo.util.ListViewAdapter;

public class Today extends Activity implements View.OnTouchListener{

	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 100;
	private GestureDetector gestureDetetctor;
	View.OnTouchListener gestureListener;
	public static final BuJoDbHelper dbHelper = new BuJoDbHelper();
	
	
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

		DisplayBullets();
	}

	public void DisplayBullets(){
		new PopulateJournalEntries().execute(this);
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today, menu);
		return true;
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public ArrayList<Bullet> GetSorted(ArrayList<Bullet> bullets){
		Collections.sort(bullets, new Comparator<Bullet>() {
			@Override
			public int compare(Bullet bullet1, Bullet bullet2){
				return (((Long)bullet1.getDate()).compareTo((Long)bullet2.getDate()));
			}
		});
		return bullets;
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
			ListView list = (ListView) findViewById(R.id.listView);
			ListViewAdapter adapter = new ListViewAdapter(bullets, Today.this);
			list.setAdapter(adapter);
		}
	}
	
	public void EditThisTask(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int taskId =  Integer.valueOf(textView.getTag().toString());
		Task taskObject = dbHelper.getTaskObjectFor(taskId, getApplicationContext());
		new TaskEditor().execute(taskObject);
	}

	public void EditSubTask(View v){
		TextView textViewSubTask = (TextView) v.findViewById(R.id.textViewForSubTask);
		int subTaskId =  Integer.valueOf(textViewSubTask.getTag().toString());
		SubTask subTaskObject = dbHelper.getSubTaskObjectFor(subTaskId, getApplicationContext());
		new SubTaskEditor().execute(subTaskObject);
    }
	
	public void EditThisNote(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int noteId =  Integer.valueOf(textView.getTag().toString());
		Note noteObject = dbHelper.getNoteObjectFor(noteId, getApplicationContext());
		new NoteEditor().execute(noteObject);
	}

	public void EditThisEvent(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int eventId =  Integer.valueOf(textView.getTag().toString());
		Event eventObject = dbHelper.getEventObjectFor(eventId, getApplicationContext());
		new EventEditor().execute(eventObject);
	}
	
	private class TaskEditor extends AsyncTask<Task, Void, Void>{
		@Override
		protected Void doInBackground(Task...tasks) {
			Intent intent = new Intent(Today.this, EditTask.class);
			intent.putExtra(ApplicationConstants.TASK_OBJECT, tasks[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	private class SubTaskEditor extends AsyncTask<SubTask, Void, Void>{
		@Override
		protected Void doInBackground(SubTask...tasks) {
			Intent intent = new Intent(Today.this, EditSubTask.class);
			intent.putExtra(ApplicationConstants.SUB_TASK_OBJECT, tasks[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	private class NoteEditor extends AsyncTask<Note, Void, Void>{
		@Override
		protected Void doInBackground(Note...notes) {
			Intent intent = new Intent(Today.this, EditNote.class);
			intent.putExtra(ApplicationConstants.NOTE_OBJECT, notes[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	private class EventEditor extends AsyncTask<Event, Void, Void>{
		@Override
		protected Void doInBackground(Event...events) {
			Intent intent = new Intent(Today.this, EditEvent.class);
			intent.putExtra(ApplicationConstants.EVENT_OBJECT, events[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
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
}



