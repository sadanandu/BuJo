package com.example.bujo.util;

import android.content.Context;

import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;

public class BuJoDbHelper {
	public BujoDbHandler dbHandler;

	public BuJoDbHelper(Context context) {
		// TODO Auto-generated constructor stub
		dbHandler = new BujoDbHandler(context);
	}
	
	public Task getTaskObjectFor(int taskId){
		Task taskObject = dbHandler.getTask(taskId);
		return taskObject;		
	}

	public SubTask getSubTaskObjectFor(int subTaskId){
		SubTask subTaskObject = dbHandler.getSubTask(subTaskId);
		return subTaskObject;		
	}

	public Note getNoteObjectFor(int noteId){
		Note noteObject = dbHandler.getNote(noteId);
		return noteObject;		
	}

	public Event getEventObjectFor(int eventId){
		Event eventObject = dbHandler.getEvent(eventId);
		return eventObject;		
	}
	
}
