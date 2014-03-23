package com.example.bujo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bujo.model.Bullet;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;
import com.example.bujo.util.ApplicationConstants;

public class Today extends Activity implements View.OnTouchListener{

	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 100;
	private GestureDetector gestureDetetctor;
	View.OnTouchListener gestureListener;
	
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

		DisplayTasks();
	}

	public void DisplayTasks(){
		new PopulateJournalEntries().execute(this);
		}
	

	public Task getTaskObjectFor(int taskId, Context context){
		BujoDbHandler taskHandler = new BujoDbHandler(context);
		Task taskObject = taskHandler.getTask(taskId);
		return taskObject;		
	}

	public SubTask getSubTaskObjectFor(int subTaskId, Context context){
		BujoDbHandler taskHandler = new BujoDbHandler(context);
		SubTask subTaskObject = taskHandler.getSubTask(subTaskId);
		return subTaskObject;		
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
			Toast.makeText(getApplicationContext(), "adding note", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_add_event:
			Toast.makeText(getApplicationContext(), "adding event", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//	                                ContextMenuInfo menuInfo) {
//	    super.onCreateContextMenu(menu, v, menuInfo);
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.task_long_click_menu, menu);
//	}
//	
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//	    switch (item.getItemId()) {
//	        case R.id.action_delete_task:
//	        	View targetView = info.targetView;
//	        	TextView textView = (TextView)targetView.findViewById(R.id.textView);
//	        	new TaskDeleter().execute(Integer.valueOf(textView.getTag().toString()));
//	        	DisplayTasks();
//	            return true;
//	        case R.id.action_add_sub_task:
//	        	View targetViewForSubTask = info.targetView;
//	        	TextView textViewForSubTask = (TextView)targetViewForSubTask.findViewById(R.id.textView);
//	        	int taskId =  Integer.valueOf(textViewForSubTask.getTag().toString());
//				Task taskObject = getTaskObjectFor(taskId, getApplicationContext());
//	        	new SubTaskCreator().execute(taskObject);
//	        default:
//	            return super.onContextItemSelected(item);
//	    }
//	}
	
	private class PopulateJournalEntries extends AsyncTask<Context, Void, ArrayList<Bullet>>{

		@Override
		protected ArrayList<Bullet> doInBackground(Context... contexts) {
			// TODO Auto-generated method stub
			BujoDbHandler dbHandler = new BujoDbHandler(contexts[0]);
			ArrayList<Bullet> bullets = new ArrayList<Bullet>();
			bullets = dbHandler.getTodayTasks();
			bullets.addAll(dbHandler.getTodayNotes());
			return bullets;
		}
		
		protected void onPostExecute(ArrayList<Bullet> bullets){
			ListView list = (ListView) findViewById(R.id.listView);
			//list.setOnTouchListener(gestureListener);
			ListViewAdapter adapter = new ListViewAdapter(Today.this, R.layout.layout_task, bullets);
			list.setAdapter(adapter);
		}
	}
	
	public void EditThisTask(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int taskId =  Integer.valueOf(textView.getTag().toString());
		Task taskObject = getTaskObjectFor(taskId, getApplicationContext());
		new TaskEditor().execute(taskObject);
	}
	
	public void editSubTask(View v){
		TextView textViewSubTask = (TextView) v.findViewById(R.id.textViewForSubTask);
		int subTaskId =  Integer.valueOf(textViewSubTask.getTag().toString());
		SubTask subTaskObject = getSubTaskObjectFor(subTaskId, getApplicationContext());
		new SubTaskEditor().execute(subTaskObject);
    }
	
	private class TaskDeleter extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer...taskIds) {
			BujoDbHandler taskHandler = new BujoDbHandler(getApplicationContext());
			taskHandler.deleteTask(taskIds[0]);
			return null;
		}
		
		protected void onPostExecute(){
		}
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
	
	private class SubTaskCreator extends AsyncTask<Task, Void, Void>{
		@Override
		protected Void doInBackground(Task...tasks) {
			Intent intent = new Intent(Today.this, AddSubTask.class);
			intent.putExtra(ApplicationConstants.TASK_OBJECT, tasks[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	private class SubTaskDeleter extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer...subtaskids) {
			BujoDbHandler taskHandler = new BujoDbHandler(getApplicationContext());
			taskHandler.deleteSubTask(subtaskids[0]);			
			return null;
		}
		
		protected void onPostExecute(){
		}
	}

	static class ViewHolder{
		View rowView, subLine;
		TextView textForTask, textForSubTask;
		CheckBox checkBoxForTask, checkBoxForSubTask;
		LinearLayout layoutForSubTask;
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
	
	
	public class ListViewAdapter extends ArrayAdapter<Bullet>{
		private final Context context;
		private final int layoutId ;
		private final ArrayList<Bullet> values;
		
		public ListViewAdapter(Context context, int layoutId, ArrayList<Bullet> values){
			super(context, layoutId, values);
			this.context = context;
			this.layoutId = layoutId;
			this.values = values;
		}
		
		 @Override
	     public int getCount(){
	           return values!=null ? values.size() : 0;
	     }
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			final ViewHolder holder;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null){
				final View rowView = inflater.inflate(this.layoutId, parent, false);
				rowView.setClickable(true);
				convertView = rowView;
				holder = new ViewHolder();
				holder.rowView = rowView;
				//holder.rowView.setOnTouchListener(gestureListener);
				holder.textForTask = (TextView) rowView.findViewById(R.id.textView);
				holder.checkBoxForTask = (CheckBox) rowView.findViewById(R.id.checkBox);
				holder.layoutForSubTask = (LinearLayout) rowView.findViewById(R.id.linearLayoutForSubTask);
				holder.layoutForSubTask.removeAllViews();
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			if (values != null){
				holder.checkBoxForTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton button, boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked == true){
							holder.textForTask.setPaintFlags(holder.textForTask.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
							CompleteAllSubTasksForTask(holder.rowView);
						}
						else{
							holder.textForTask.setPaintFlags(holder.textForTask.getPaintFlags()& ~ Paint.STRIKE_THRU_TEXT_FLAG);
						}
					}
				} );
				if (values.get(position).getClass().getName().equals("com.example.bujo.model.Task")){
					Task tempTask = (Task)(values.get(position));
					holder.textForTask.setText(tempTask.getName());
					holder.textForTask.setTag(tempTask.get_id());
					if (tempTask.getIsDone() == "Yes"){
						holder.checkBoxForTask.setChecked(true);
					}
				}
				
				holder.textForTask.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						showOptionsMenuForTask((TextView)v);
						return true;
					}
				});
				
				ArrayList<SubTask> subtasks = new ArrayList<SubTask>();
				BujoDbHandler taskHandler = new BujoDbHandler(getContext());
				subtasks = taskHandler.getSubTasksForTask(values.get(position).get_id());
				taskHandler.close();
				for(int i =0; i < subtasks.size(); i = i +1){
					View subLine = inflater.inflate(R.layout.layout_sub_task, null);
					final TextView textViewForSubTask = (TextView) subLine.findViewById(R.id.textViewForSubTask);
					final CheckBox checkBoxForSubTask = (CheckBox) subLine.findViewById(R.id.checkBoxForSubTask);
					textViewForSubTask.setText(subtasks.get(i).getSubTaskName());
					textViewForSubTask.setTag(subtasks.get(i).get_id());
					if (subtasks.get(i).getIsDone() == "Yes"){
						checkBoxForSubTask.setChecked(true);
					}
					
					checkBoxForSubTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton button, boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked == true){
								textViewForSubTask.setPaintFlags(textViewForSubTask.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
							}
							else{
								textViewForSubTask.setPaintFlags(textViewForSubTask.getPaintFlags()& ~ Paint.STRIKE_THRU_TEXT_FLAG);
								ChangeStatusOfParentTaskIfApplicable(holder.rowView);
							}
						}
					} );

					textViewForSubTask.setOnLongClickListener(new OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							// TODO Auto-generated method stub
							showOptionsMenuForSubTask((TextView)v);
							return true;
						}
					});
					holder.layoutForSubTask.addView(subLine);
				}
			}
			return holder.rowView;
		}


		public void CompleteAllSubTasksForTask(View rowView){
			LinearLayout layoutForSubTask = (LinearLayout) rowView.findViewById(R.id.linearLayoutForSubTask);
			int rowCount = layoutForSubTask.getChildCount();
			for(int i=0; i<rowCount; i = i+1){
				View subLine = layoutForSubTask.getChildAt(i);
				CheckBox checkBoxForSubTask = (CheckBox) subLine.findViewById(R.id.checkBoxForSubTask);
				checkBoxForSubTask.setChecked(true);
			}
		}
		
		public void ChangeStatusOfParentTaskIfApplicable(View rowView){
			CheckBox checkBoxForTask = (CheckBox) rowView.findViewById(R.id.checkBox);
			checkBoxForTask.setChecked(false);
		}
		
		@SuppressWarnings("deprecation")
		public void showOptionsMenuForTask(final TextView v){
			AlertDialog alert = new AlertDialog.Builder(getContext()).create();
			alert.setTitle("Task Actions");
			alert.setButton("Delete Task", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					new TaskDeleter().execute(Integer.valueOf(v.getTag().toString()));
					recreate();
				}
			});
			alert.setButton2("Add Sub Task", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
		        	int taskId =  Integer.valueOf(v.getTag().toString());
					Task taskObject = getTaskObjectFor(taskId, getApplicationContext());
		        	new SubTaskCreator().execute(taskObject);
				}
			});

			alert.show();
		}
		
		@SuppressWarnings("deprecation")
		public void showOptionsMenuForSubTask(final TextView v){
			AlertDialog alert = new AlertDialog.Builder(getContext()).create();
			alert.setTitle("Sub-Task Actions");
			alert.setButton("Delete Sub-Task", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					new SubTaskDeleter().execute(Integer.valueOf(v.getTag().toString()));
					recreate();
				}
			});
			alert.show();
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}



