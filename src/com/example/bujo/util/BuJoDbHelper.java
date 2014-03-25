package com.example.bujo.util;

import android.content.Context;

import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;

public class BuJoDbHelper {

	public BuJoDbHelper() {
		// TODO Auto-generated constructor stub
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

	public Note getNoteObjectFor(int noteId, Context context){
		BujoDbHandler dbHandler = new BujoDbHandler(context);
		Note noteObject = dbHandler.getNote(noteId);
		return noteObject;		
	}

	public Event getEventObjectFor(int eventId, Context context){
		BujoDbHandler dbHandler = new BujoDbHandler(context);
		Event eventObject = dbHandler.getEvent(eventId);
		return eventObject;		
	}
	
}
