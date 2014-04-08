package com.example.bujo.util;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bujo.R;
import com.example.bujo.activity.AddSubTask;
import com.example.bujo.activity.SearchResultsActivity;
import com.example.bujo.fragment.BulletFragment;
import com.example.bujo.model.Bullet;
import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;

public class ListViewAdapter extends BaseAdapter{
	private final Context context;
	private final ArrayList<Bullet> values;
	private final LayoutInflater inflater;
	private final BulletFragment fragment;
	private static final int TASKROW = 0;
	private static final int NOTEROW = 1;
	private static final int EVENTROW = 2;
	public ArrayList<Integer> selectedItems;
	public BuJoDbHelper dbHelper;
//	private ActionMode.Callback mActionModeCallback;
//	private ActionMode mActionMode;
	
	static class ViewHolder{
		View rowView, subLine;
		TextView textForTask, textForSubTask;
		CheckBox checkBoxForTask, checkBoxForSubTask;
		LinearLayout layoutForSubTask;
	}
	
	public ListViewAdapter(ArrayList<Bullet> values, Context context, BulletFragment fragment){
		super();
		this.values = values;
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.inflater = inflater;
		this.fragment = fragment;
		dbHelper = new BuJoDbHelper(context);
//		selectedItems = new ArrayList<Integer>();
//		mActionModeCallback = new ActionMode.Callback() {
//
//		    // Called when the action mode is created; startActionMode() was called
//		    @Override
//		    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//		        // Inflate a menu resource providing context menu items
//		        MenuInflater inflater = mode.getMenuInflater();
//		        inflater.inflate(R.menu.task_long_click_menu, menu);
//		        return true;
//		    }
//
//		    // Called each time the action mode is shown. Always called after onCreateActionMode, but
//		    // may be called multiple times if the mode is invalidated.
//		    @Override
//		    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//		        return false; // Return false if nothing is done
//		    }
//
//		    // Called when the user selects a contextual menu item
//		    @Override
//		    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//		        switch (item.getItemId()) {
//		            case R.id.action_add_sub_task:
//		            	
//		            	mode.finish(); // Action picked, so close the CAB
//		                return true;
//		            case R.id.action_delete_task:
//		            	
//		            	mode.finish();
//		            	return true;
//		            default:
//		                return false;
//		        }
//		    }
//
//		    // Called when the user exits the action mode
//		    @Override
//		    public void onDestroyActionMode(ActionMode mode) {
//		        mActionMode = null;
//		    }
//		};
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (values.get(position).getClass().getName().equals("com.example.bujo.model.Task")){
			return this.TASKROW;
		}
		if (values.get(position).getClass().getName().equals("com.example.bujo.model.Note")){
			return this.NOTEROW;
		}
		if (values.get(position).getClass().getName().equals("com.example.bujo.model.Event")){
			return this.EVENTROW;
		}
		return -1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
    public int getCount(){
       return this.values!=null ? this.values.size() : 0;
    }
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		final ViewHolder holder;
		final int positionOfRow = position;
		final int rowType = getItemViewType(position);
		if(rowType == TASKROW){
			
			if (convertView == null){
				final View rowView = this.inflater.inflate(R.layout.layout_task, parent, false);
				rowView.setClickable(true);
				convertView = rowView;
				holder = new ViewHolder();
				holder.rowView = rowView;
				//holder.rowView.setOnTouchListener(gestureListener);
				holder.textForTask = (TextView) rowView.findViewById(R.id.textView);
				holder.checkBoxForTask = (CheckBox) rowView.findViewById(R.id.checkBox);
				holder.layoutForSubTask = (LinearLayout) rowView.findViewById(R.id.linearLayoutForSubTask);
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
				Task tempTask = (Task)(values.get(position));
				holder.textForTask.setText(tempTask.getName());
				holder.textForTask.setTag(tempTask.get_id());
				if (tempTask.getIsDone() == "Yes"){
					holder.checkBoxForTask.setChecked(true);
				}

//				holder.textForTask.setOnLongClickListener(new View.OnLongClickListener() {
//				    // Called when the user long-clicks on someView
//				    public boolean onLongClick(View view) {
//				    	selectedItems.add(Integer.valueOf(positionOfRow));
//				    	view.setBackgroundColor(Color.YELLOW);
//				    	return TaskLongClick(view);
//				    }
//				});
				
				holder.textForTask.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						fragment.EditThisTask(v);
					}
				});
			
				holder.textForTask.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						showOptionsMenuForTask((TextView)v, positionOfRow);
						return true;
					}
				});
				
				ArrayList<SubTask> subtasks = new ArrayList<SubTask>();
				BujoDbHandler taskHandler = new BujoDbHandler(this.context);
				subtasks = taskHandler.getSubTasksForTask(values.get(position).get_id());
				holder.layoutForSubTask.removeAllViews();
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
					textViewForSubTask.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							fragment.EditSubTask(v);
						}
					});
					
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

		if(rowType == NOTEROW){
			if (convertView == null){
				final View rowView = this.inflater.inflate(R.layout.layout_note, parent, false);
				rowView.setClickable(true);
				convertView = rowView;
				holder = new ViewHolder();
				holder.rowView = rowView;
				holder.textForTask = (TextView) rowView.findViewById(R.id.textView);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			if (values != null){
				Note tempNote = (Note)(values.get(position));
				holder.textForTask.setText(tempNote.getName());
				holder.textForTask.setTag(tempNote.get_id());
				holder.textForTask.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						fragment.EditThisNote(v);
					}
				});
				
				holder.textForTask.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						showOptionsMenuForNote((TextView)v, positionOfRow);
						return true;
					}
				});
		
			}
			return holder.rowView;
		}
		
		if(rowType == EVENTROW){
			if (convertView == null){
				final View rowView = this.inflater.inflate(R.layout.layout_event, parent, false);
				rowView.setClickable(true);
				convertView = rowView;
				holder = new ViewHolder();
				holder.rowView = rowView;
				holder.textForTask = (TextView) rowView.findViewById(R.id.textView);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			if (values != null){
				Event tempEvent = (Event)(values.get(position));
				holder.textForTask.setText(tempEvent.getName());
				holder.textForTask.setTag(tempEvent.get_id());
				holder.textForTask.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						fragment.EditThisEvent(v);
					}
				});

				holder.textForTask.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						showOptionsMenuForEvent((TextView)v, positionOfRow);
						return true;
					}
				});
		
			}
			return holder.rowView;
		}

		return convertView;
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
	public void showOptionsMenuForTask(final TextView v, final int position){
		AlertDialog alert = new AlertDialog.Builder(this.context).create();
		alert.setTitle("Task Actions");
		alert.setButton("Delete Task", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				values.remove(position);
				new TaskDeleter().execute(Integer.valueOf(v.getTag().toString()));
				notifyDataSetChanged();
			}
		});
		alert.setButton2("Add Sub Task", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
	        	int taskId =  Integer.valueOf(v.getTag().toString());
				Task taskObject = dbHelper.getTaskObjectFor(taskId);
	        	new SubTaskCreator().execute(taskObject);
			}
		});

		alert.show();
	}
	
	@SuppressWarnings("deprecation")
	public void showOptionsMenuForSubTask(final TextView v){
		AlertDialog alert = new AlertDialog.Builder(this.context).create();
		alert.setTitle("Sub-Task Actions");
		alert.setButton("Delete Sub-Task", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				new SubTaskDeleter().execute(Integer.valueOf(v.getTag().toString()));
				notifyDataSetChanged();
			}
		});
		alert.show();
	}
	
	@SuppressWarnings("deprecation")
	public void showOptionsMenuForNote(final TextView v, final int position){
		AlertDialog alert = new AlertDialog.Builder(this.context).create();
		alert.setTitle("Note Actions");
		alert.setButton("Delete Note", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				values.remove(position);
				new NoteDeleter().execute(Integer.valueOf(v.getTag().toString()));
				notifyDataSetChanged();
			}
		});

		alert.show();
	}

	@SuppressWarnings("deprecation")
	public void showOptionsMenuForEvent(final TextView v, final int position){
		AlertDialog alert = new AlertDialog.Builder(this.context).create();
		alert.setTitle("Event Actions");
		alert.setButton("Delete Event", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				values.remove(position);
				new EventDeleter().execute(Integer.valueOf(v.getTag().toString()));
				notifyDataSetChanged();
			}
		});

		alert.show();
	}

	public class TaskDeleter extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer...taskIds) {
			BujoDbHandler taskHandler = new BujoDbHandler(context);
			taskHandler.deleteTask(taskIds[0]);
			return null;
		}
		
		protected void onPostExecute(){
		}
	}
	
	public class NoteDeleter extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer...noteIds) {
			BujoDbHandler dbHandler = new BujoDbHandler(context);
			dbHandler.deleteNote(noteIds[0]);
			return null;
		}
		
		protected void onPostExecute(){
		}
	}

	public class EventDeleter extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer...noteIds) {
			BujoDbHandler dbHandler = new BujoDbHandler(context);
			dbHandler.deleteEvent(noteIds[0]);
			return null;
		}
		
		protected void onPostExecute(){
		}
	}

	
	public class SubTaskCreator extends AsyncTask<Task, Void, Void>{
		@Override
		protected Void doInBackground(Task...tasks) {
			Intent intent = new Intent(context, AddSubTask.class);
			intent.putExtra(ApplicationConstants.TASK_OBJECT, tasks[0]);
	    	context.startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	public class SubTaskDeleter extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer...subtaskids) {
			BujoDbHandler taskHandler = new BujoDbHandler(context);
			taskHandler.deleteSubTask(subtaskids[0]);			
			return null;
		}
		
		protected void onPostExecute(){
		}
	}
	
}
