package com.example.bujo.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bujo.R;
import com.example.bujo.activity.AddEvent;
import com.example.bujo.activity.AddNote;
import com.example.bujo.activity.AddTask;
import com.example.bujo.activity.EditEvent;
import com.example.bujo.activity.EditNote;
import com.example.bujo.activity.EditSubTask;
import com.example.bujo.activity.EditTask;
import com.example.bujo.model.Bullet;
import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;
import com.example.bujo.util.ApplicationConstants;
import com.example.bujo.util.BuJoDbHelper;
import com.example.bujo.util.BujoDbHandler;
import com.example.bujo.util.ListViewAdapter;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the
 * ListView with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
//public class BulletFragment extends Fragment implements AbsListView.OnItemClickListener {
public class BulletFragment extends ListFragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	public static BuJoDbHelper dbHelper;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	// TODO: Rename and change types of parameters
	public static BulletFragment newInstance(String param1, String param2) {
		BulletFragment fragment = new BulletFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BulletFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		dbHelper  = new BuJoDbHelper(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_bullet, container, false);
//		DisplayBullets();
	
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * The default content for this Fragment has a TextView that is shown when
	 * the list is empty. If you would like to change the text, call this method
	 * to supply the text it should use.
	 */
	public void setEmptyText(CharSequence emptyText) {
		View emptyView = getListView().getEmptyView();

		if (emptyText instanceof TextView) {
			((TextView) emptyView).setText(emptyText);
		}
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(String id);
	}

	
//	
//	public void DisplayBullets(){
//		new PopulateJournalEntries().execute(getActivity());
//		}
//
//
//	private class PopulateJournalEntries extends AsyncTask<Context, Void, ArrayList<Bullet>>{
//
//		@Override
//		protected ArrayList<Bullet> doInBackground(Context... contexts) {
//			// TODO Auto-generated method stub
//			BujoDbHandler dbHandler = new BujoDbHandler(contexts[0]);
//			ArrayList<Bullet> bullets = new ArrayList<Bullet>();
//			bullets = dbHandler.getTodayTasks();
//			bullets.addAll(dbHandler.getTodayNotes());
//			bullets.addAll(dbHandler.getTodayEvents());
//			return GetSorted(bullets);
//		}
//		
//		protected void onPostExecute(ArrayList<Bullet> bullets){
//			setListAdapter(new ListViewAdapter(bullets, getActivity(), BulletFragment.this));
//		}
//	}
//
//	public ArrayList<Bullet> GetSorted(ArrayList<Bullet> bullets){
//		Collections.sort(bullets, new Comparator<Bullet>() {
//			@Override
//			public int compare(Bullet bullet1, Bullet bullet2){
//				return (((Long)bullet1.getDate()).compareTo((Long)bullet2.getDate()));
//			}
//		});
//		return bullets;
//	}

	public void EditThisNote(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int noteId =  Integer.valueOf(textView.getTag().toString());
		Note noteObject = dbHelper.getNoteObjectFor(noteId);
		new NoteEditor().execute(noteObject);
	}

	public void EditThisTask(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int taskId =  Integer.valueOf(textView.getTag().toString());
		Task taskObject = dbHelper.getTaskObjectFor(taskId);
		new TaskEditor().execute(taskObject);
	}

	public void EditSubTask(View v){
		TextView textViewSubTask = (TextView) v.findViewById(R.id.textViewForSubTask);
		int subTaskId =  Integer.valueOf(textViewSubTask.getTag().toString());
		SubTask subTaskObject = dbHelper.getSubTaskObjectFor(subTaskId);
		new SubTaskEditor().execute(subTaskObject);
    }
	
	public void EditThisEvent(View v){
		TextView textView = (TextView) v.findViewById(R.id.textView);
		int eventId =  Integer.valueOf(textView.getTag().toString());
		Event eventObject = dbHelper.getEventObjectFor(eventId);
		new EventEditor().execute(eventObject);
	}
	
	private class TaskEditor extends AsyncTask<Task, Void, Void>{
		@Override
		protected Void doInBackground(Task...tasks) {
			Intent intent = new Intent(getActivity(), EditTask.class);
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
			Intent intent = new Intent(getActivity(), EditSubTask.class);
			intent.putExtra(ApplicationConstants.SUB_TASK_OBJECT, tasks[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	private class EventEditor extends AsyncTask<Event, Void, Void>{
		@Override
		protected Void doInBackground(Event...events) {
			Intent intent = new Intent(getActivity(), EditEvent.class);
			intent.putExtra(ApplicationConstants.EVENT_OBJECT, events[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	
	private class NoteEditor extends AsyncTask<Note, Void, Void>{
		@Override
		protected Void doInBackground(Note...notes) {
			Intent intent = new Intent(getActivity(), EditNote.class);
			intent.putExtra(ApplicationConstants.NOTE_OBJECT, notes[0]);
	    	startActivity(intent);
	    	return null;
		}
		
		protected void onPostExecute(){
		}
	}

	
	
	
}
