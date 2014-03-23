package com.example.bujo.util;
//import java.util.ArrayList;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Paint;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnLongClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.bujo.BujoDbHandler;
//import com.example.bujo.R;
//import com.example.bujo.model.SubTask;
//import com.example.bujo.model.Task;
//
////package com.example.bujo.util;
////
//	public class ListViewAdapter extends ArrayAdapter<Task>{
//		private final Context context;
//		private final int layoutId ;
//		private final ArrayList<Task> values;
//		
//		public ListViewAdapter(Context context, int layoutId, ArrayList<Task> values){
//			super(context, layoutId, values);
//			this.context = context;
//			this.layoutId = layoutId;
//			this.values = values;
//		}
//		
//		 @Override
//	     public int getCount(){
//	           return values!=null ? values.size() : 0;
//	     }
//		
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent){
//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			final View rowView = inflater.inflate(this.layoutId, parent, false);
//			rowView.setClickable(true);
//			//rowView.setLongClickable(true);
//			if (values != null){
//				final TextView textView = (TextView) rowView.findViewById(R.id.textView);
//				final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
//				checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(CompoundButton button, boolean isChecked) {
//						// TODO Auto-generated method stub
//						if (isChecked == true){
//							textView.setPaintFlags(textView.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
//							CompleteAllSubTasksForTask(rowView);
//						}
//						else{
//							textView.setPaintFlags(textView.getPaintFlags()& ~ Paint.STRIKE_THRU_TEXT_FLAG);
//						}
//					}
//				} );
//				textView.setText(values.get(position).getTaskName());
//				textView.setTag(values.get(position).get_id());
//				if (values.get(position).getIsDone() == "Yes"){
//					checkBox.setChecked(true);
//				}
//				
//				textView.setOnLongClickListener(new OnLongClickListener() {
//					@Override
//					public boolean onLongClick(View v) {
//						// TODO Auto-generated method stub
//						showOptionsMenuForTask((TextView)v);
//						return true;
//					}
//				});
//				
//				LinearLayout layoutForSubTask = (LinearLayout) rowView.findViewById(R.id.linearLayoutForSubTask);
//				layoutForSubTask.removeAllViews();
//				ArrayList<SubTask> subtasks = new ArrayList<SubTask>();
//				BujoDbHandler taskHandler = new BujoDbHandler(getContext());
//				subtasks = taskHandler.getSubTasksForTask(values.get(position).get_id());
//				taskHandler.close();
//				for(int i =0; i < subtasks.size(); i = i +1){
//					View subLine = inflater.inflate(R.layout.layout_sub_task, null);
//					final TextView textViewForSubTask = (TextView) subLine.findViewById(R.id.textViewForSubTask);
//					final CheckBox checkBoxForSubTask = (CheckBox) subLine.findViewById(R.id.checkBoxForSubTask);
//					textViewForSubTask.setText(subtasks.get(i).getSubTaskName());
//					textViewForSubTask.setTag(subtasks.get(i).get_id());
//					if (subtasks.get(i).getIsDone() == "Yes"){
//						checkBoxForSubTask.setChecked(true);
//					}
//					
//					checkBoxForSubTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//						@Override
//						public void onCheckedChanged(CompoundButton button, boolean isChecked) {
//							// TODO Auto-generated method stub
//							if (isChecked == true){
//								textViewForSubTask.setPaintFlags(textView.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
//							}
//							else{
//								textViewForSubTask.setPaintFlags(textView.getPaintFlags()& ~ Paint.STRIKE_THRU_TEXT_FLAG);
//							}
//						}
//					} );
//
//					textViewForSubTask.setOnLongClickListener(new OnLongClickListener() {
//						@Override
//						public boolean onLongClick(View v) {
//							// TODO Auto-generated method stub
//							showOptionsMenuForSubTask((TextView)v);
//							return true;
//						}
//					});
//					layoutForSubTask.addView(subLine);
//				}
//			}
//			return rowView;
//		}
//
//
//		public void CompleteAllSubTasksForTask(View rowView){
//			LinearLayout layoutForSubTask = (LinearLayout) rowView.findViewById(R.id.linearLayoutForSubTask);
//			int rowCount = layoutForSubTask.getChildCount();
//			for(int i=0; i<rowCount; i = i+1){
//				View subLine = layoutForSubTask.getChildAt(i);
//				CheckBox checkBoxForSubTask = (CheckBox) subLine.findViewById(R.id.checkBoxForSubTask);
//				checkBoxForSubTask.setChecked(true);
//			}
//		}
//		
//		@SuppressWarnings("deprecation")
//		public void showOptionsMenuForTask(final TextView v){
//			AlertDialog alert = new AlertDialog.Builder(getContext()).create();
//			alert.setTitle("Task Actions");
//			alert.setButton("Delete Task", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					// TODO Auto-generated method stub
//					new TaskDeleter().execute(Integer.valueOf(v.getTag().toString()));
//					recreate();
//				}
//			});
////			alert.setButton2("Add Sub Task", new DialogInterface.OnClickListener() {
////				
////				@Override
////				public void onClick(DialogInterface arg0, int arg1) {
////					// TODO Auto-generated method stub
////		        	int taskId =  Integer.valueOf(v.getTag().toString());
////					Task taskObject = getTaskObjectFor(taskId, getApplicationContext());
////		        	new SubTaskCreator().execute(taskObject);
////				}
////			});
////
//			alert.show();
//		}
//		
////		@SuppressWarnings("deprecation")
////		public void showOptionsMenuForSubTask(final TextView v){
////			AlertDialog alert = new AlertDialog.Builder(getContext()).create();
////			alert.setTitle("Sub-Task Actions");
////			alert.setButton("Delete Sub-Task", new DialogInterface.OnClickListener() {
////				@Override
////				public void onClick(DialogInterface arg0, int arg1) {
////					// TODO Auto-generated method stub
////					new SubTaskDeleter().execute(Integer.valueOf(v.getTag().toString()));
////					recreate();
////				}
////			});
//			alert.show();
//		}
//	}
