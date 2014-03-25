package com.example.bujo.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bujo.model.Bullet;
import com.example.bujo.model.Event;
import com.example.bujo.model.Note;
import com.example.bujo.model.SubTask;
import com.example.bujo.model.Task;

public class BujoDbHandler extends SQLiteOpenHelper{
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BuJo.db";

    public static final String TABLE_TASK = "tasks";
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_ISDONE = "isDone";
    
    public static final String TABLE_SUBTASK = "subtasks";
    public static final String KEY_SUBTASK = "subtask";
    public static final String KEY_SUBTASKDESCRIPTION = "subtaskdescription";
    public static final String KEY_PARENTTASK = "parenttask";

    public static final String TABLE_NOTE = "notes";
    public static final String KEY_NOTE = "note";
    public static final String KEY_NOTEDESCRIPTION = "description";

    public static final String TABLE_EVENT = "events";
    public static final String KEY_EVENT = "event";
    public static final String KEY_EVENTDESCRIPTION = "description";

    private static final String SQL_CREATE_TASK_ENTRIES =
            "CREATE TABLE " + TABLE_TASK + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, description TEXT, date LONG, isDone TEXT)";

    private static final String SQL_CREATE_SUBTASK_ENTRIES =
            "CREATE TABLE " + TABLE_SUBTASK + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, subtask TEXT, subtaskdescription TEXT, parenttask INTEGER, date LONG, isDone TEXT)";

    private static final String SQL_CREATE_NOTE_ENTRIES =
            "CREATE TABLE " + TABLE_NOTE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT, description TEXT, date LONG)";

    private static final String SQL_CREATE_EVENT_ENTRIES =
            "CREATE TABLE " + TABLE_EVENT + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, event TEXT, description TEXT, date LONG)";

    
    private static final String SQL_DELETE_TASKENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_TASK;

    private static final String SQL_DELETE_SUBTASKENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_SUBTASK;

    private static final String SQL_DELETE_NOTEENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NOTE;

    private static final String SQL_DELETE_EVENTENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_EVENT;

    
    public BujoDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASK_ENTRIES);
        db.execSQL(SQL_CREATE_SUBTASK_ENTRIES);
        db.execSQL(SQL_CREATE_NOTE_ENTRIES);
        db.execSQL(SQL_CREATE_EVENT_ENTRIES);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
    	db.execSQL(SQL_DELETE_SUBTASKENTRIES);
        db.execSQL(SQL_DELETE_TASKENTRIES);
        db.execSQL(SQL_DELETE_NOTEENTRIES);
        db.execSQL(SQL_DELETE_EVENTENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void addTask(Task task){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	//values.put(KEY_ID, task.get_id());
    	values.put(KEY_TASK, task.getName());
    	values.put(KEY_DESCRIPTION, task.getDescription());
    	values.put(KEY_DATE, task.getDate());
    	values.put(KEY_ISDONE, task.getIsDone());
    	db.insert(TABLE_TASK, null, values);
    	db.close();
    }

    public void addSubTask(SubTask subTask){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	//values.put(KEY_ID, task.get_id());
    	values.put(KEY_SUBTASK, subTask.getSubTaskName());
    	values.put(KEY_SUBTASKDESCRIPTION, subTask.getSubTaskDescription());
    	values.put(KEY_PARENTTASK, subTask.getParentTask());
    	values.put(KEY_DATE, subTask.getDate());
    	values.put(KEY_ISDONE, subTask.getIsDone());
    	db.insert(TABLE_SUBTASK, null, values);
    	db.close();
    }

    public void addNote(Note note){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_NOTE, note.getName());
    	values.put(KEY_NOTEDESCRIPTION, note.getDescription());
    	values.put(KEY_DATE, note.getDate());
    	db.insert(TABLE_NOTE, null, values);
    	db.close();
    }

    public void addEvent(Event event){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_EVENT, event.getName());
    	values.put(KEY_EVENTDESCRIPTION, event.getDescription());
    	values.put(KEY_DATE, event.getDate());
    	db.insert(TABLE_EVENT, null, values);
    	db.close();
    }

    public Task getTask(int id){
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from tasks where _id = ?", new String[] {Integer.toString(id)});
		String taskDescription, taskName, isDone;
		long taskDate;		
		c.moveToFirst();
		taskName = c.getString(c.getColumnIndex(BujoDbHandler.KEY_TASK));
		taskDescription = c.getString(c.getColumnIndex(BujoDbHandler.KEY_DESCRIPTION));
		taskDate = c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE));
		isDone = c.getString(c.getColumnIndex(BujoDbHandler.KEY_ISDONE));
		Task task = new Task();
		task.set_id(id);
		task.setName(taskName);
		task.setDescription(taskDescription);
		task.setDate(taskDate);
		task.setIsDone(isDone);
		db.close();
		return task;
    }

    public SubTask getSubTask(int id){
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from subtasks where _id = ?", new String[] {Integer.toString(id)});
		String subTaskDescription, subTaskName, isDone;
		int parentTask;
		long taskDate;		
		c.moveToFirst();
		subTaskName = c.getString(c.getColumnIndex(BujoDbHandler.KEY_SUBTASK));
		subTaskDescription = c.getString(c.getColumnIndex(BujoDbHandler.KEY_SUBTASKDESCRIPTION));
		parentTask = c.getInt(c.getColumnIndex(BujoDbHandler.KEY_PARENTTASK));
		taskDate = c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE));
		isDone = c.getString(c.getColumnIndex(BujoDbHandler.KEY_ISDONE));
		SubTask subTask = new SubTask();
		subTask.set_id(id);
		subTask.setSubTaskName(subTaskName);
		subTask.setSubTaskDescription(subTaskDescription);
		subTask.setParentTask(parentTask);
		subTask.setDate(taskDate);
		subTask.setIsDone(isDone);
		db.close();
		return subTask;
    }

    public Note getNote(int id){
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from notes where _id = ?", new String[] {Integer.toString(id)});
		String noteDescription, noteName;
		long noteDate;		
		c.moveToFirst();
		noteName = c.getString(c.getColumnIndex(BujoDbHandler.KEY_NOTE));
		noteDescription = c.getString(c.getColumnIndex(BujoDbHandler.KEY_DESCRIPTION));
		noteDate = c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE));
		Note note = new Note();
		note.set_id(id);
		note.setName(noteName);
		note.setDescription(noteDescription);
		note.setDate(noteDate);
		db.close();
		return note;
    }

    public Event getEvent(int id){
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from events where _id = ?", new String[] {Integer.toString(id)});
		String eventDescription, eventName;
		long eventDate;		
		c.moveToFirst();
		eventName = c.getString(c.getColumnIndex(BujoDbHandler.KEY_EVENT));
		eventDescription = c.getString(c.getColumnIndex(BujoDbHandler.KEY_DESCRIPTION));
		eventDate = c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE));
		Event event = new Event();
		event.set_id(id);
		event.setName(eventName);
		event.setDescription(eventDescription);
		event.setDate(eventDate);
		db.close();
		return event;
    }

    public ArrayList <Bullet> getTodayTasks(){
    	SQLiteDatabase db = this.getReadableDatabase();
    	try{
    		Cursor c1 = db.rawQuery("select date('now')", new String[0]);
    		c1.moveToFirst();
    		Cursor c = db.rawQuery("select * from tasks where date(datetime(date/1000, 'unixepoch')) = date('now')", new String[0]);
    		c.moveToFirst();
    		ArrayList<Bullet> tasks = new ArrayList<Bullet>();
    		if (c.getCount() > 0){
    			do{
    			//out.println(Integer.toString(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_ID))) + " " + c.getString(c.getColumnIndex(BujoDbHandler.KEY_TASK)) + "  " + c.getString(c.getColumnIndex(BujoDbHandler.KEY_DESCRIPTION)) + "  " +  Long.toString(c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE))) );
    				Task newTask = new Task();
    				newTask.set_id(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_ID)));
    				newTask.setName(c.getString(c.getColumnIndex(BujoDbHandler.KEY_TASK)));
    				newTask.setDescription(c.getString(c.getColumnIndex(BujoDbHandler.KEY_DESCRIPTION)));
    				newTask.setDate(c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE)));
    				newTask.setIsDone(c.getString(c.getColumnIndex(BujoDbHandler.KEY_ISDONE)));
    				tasks.add(newTask);
    			}while(c.moveToNext());
    		}
    		return tasks;
    	}catch (Exception e){
    	     return null;
    	 }
    }
    
    public ArrayList <SubTask> getSubTasksForTask(int taskid){
    	SQLiteDatabase db = this.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("select * from subtasks where parenttask = ?", new String[]{Integer.toString(taskid)});
    		c.moveToFirst();
    		ArrayList<SubTask> subTasks = new ArrayList<SubTask>();
    		if (c.getCount() > 0){
    			do{
    				SubTask subTask = new SubTask();
    				subTask.set_id(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_ID)));
    				subTask.setSubTaskName(c.getString(c.getColumnIndex(BujoDbHandler.KEY_SUBTASK)));
    				subTask.setSubTaskDescription(c.getString(c.getColumnIndex(BujoDbHandler.KEY_SUBTASKDESCRIPTION)));
    				subTask.setParentTask(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_PARENTTASK)));
    				subTask.setDate(c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE)));
    				subTask.setIsDone(c.getString(c.getColumnIndex(BujoDbHandler.KEY_ISDONE)));
    				subTasks.add(subTask);
    			}while(c.moveToNext());
    		}
    		return subTasks;
    	}catch (Exception e){
    	     return null;
    	 }
    }

    public ArrayList <Bullet> getTodayNotes(){
    	SQLiteDatabase db = this.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("select * from notes where date(datetime(date/1000, 'unixepoch')) = date('now')", new String[0]);
    		c.moveToFirst();
    		ArrayList<Bullet> notes = new ArrayList<Bullet>();
    		if (c.getCount() > 0){
    			do{
    				Note newNote = new Note();
    				newNote.set_id(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_ID)));
    				newNote.setName(c.getString(c.getColumnIndex(BujoDbHandler.KEY_NOTE)));
    				newNote.setDescription(c.getString(c.getColumnIndex(BujoDbHandler.KEY_NOTEDESCRIPTION)));
    				newNote.setDate(c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE)));
    				notes.add(newNote);
    			}while(c.moveToNext());
    		}
    		return notes;
           }catch (Exception e){
        	   return null;
   	       }
    }

    public ArrayList <Bullet> getTodayEvents(){
    	SQLiteDatabase db = this.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("select * from events where date(datetime(date/1000, 'unixepoch')) = date('now')", new String[0]);
    		c.moveToFirst();
    		ArrayList<Bullet> events = new ArrayList<Bullet>();
    		if (c.getCount() > 0){
    			do{
    				Event newEvent = new Event();
    				newEvent.set_id(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_ID)));
    				newEvent.setName(c.getString(c.getColumnIndex(BujoDbHandler.KEY_EVENT)));
    				newEvent.setDescription(c.getString(c.getColumnIndex(BujoDbHandler.KEY_EVENTDESCRIPTION)));
    				newEvent.setDate(c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE)));
    				events.add(newEvent);
    			}while(c.moveToNext());
    		}
    		return events;
           }catch (Exception e){
        	   return null;
   	       }
    }

    public ArrayList <Bullet> getAllTasks(){
    	SQLiteDatabase db = this.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("select * from tasks", new String [0]);
    		c.moveToFirst();
    		ArrayList<Bullet> tasks = new ArrayList<Bullet>();
    		do{
    			Task newTask = new Task();
    			newTask.set_id(c.getInt(c.getColumnIndex(BujoDbHandler.KEY_ID)));
    			newTask.setName(c.getString(c.getColumnIndex(BujoDbHandler.KEY_TASK)));
    			newTask.setDescription(c.getString(c.getColumnIndex(BujoDbHandler.KEY_DESCRIPTION)));
    			newTask.setDate(c.getLong(c.getColumnIndex(BujoDbHandler.KEY_DATE)));
    			tasks.add(newTask);
    		}while(c.moveToNext());
    		return tasks;
    	}catch (Exception e){
    	     return null;
    	 }
	}
    
    public void deleteTask(int taskId){
    	SQLiteDatabase db = this.getWritableDatabase();
		//db.rawQuery("delete from tasks where _id = ?", new String[] {Integer.toString(taskId)});
    	db.delete(TABLE_SUBTASK, "parenttask = ?", new String[] {Integer.toString(taskId)});
		db.delete(TABLE_TASK, "_id = ?", new String[] {Integer.toString(taskId)});
		db.close();   	
    }

    public void deleteSubTask(int taskId){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_SUBTASK, "_id = ?", new String[] {Integer.toString(taskId)});
    	db.close();
    }

    public void deleteNote(int noteId){
    	SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTE, "_id = ?", new String[] {Integer.toString(noteId)});
		db.close();   	
    }

    public void deleteEvent(int eventId){
    	SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVENT, "_id = ?", new String[] {Integer.toString(eventId)});
		db.close();   	
    }
   
    public void saveTask(Task task){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_TASK, task.getName());
    	values.put(KEY_DESCRIPTION, task.getDescription());
    	values.put(KEY_DATE, task.getDate());
    	values.put(KEY_ISDONE, task.getIsDone());
    	db.update(TABLE_TASK, values, KEY_ID+" = ?", new String[] { String.valueOf(task.get_id())});
    	db.close();
   }
    
   public void saveSubTask(SubTask subtask){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_SUBTASK, subtask.getSubTaskName());
    	values.put(KEY_SUBTASKDESCRIPTION, subtask.getSubTaskDescription());
    	values.put(KEY_PARENTTASK, subtask.getParentTask());
    	values.put(KEY_DATE, subtask.getDate());
    	values.put(KEY_ISDONE, subtask.getIsDone());
    	db.update(TABLE_SUBTASK, values, KEY_ID+" = ?", new String[] { String.valueOf(subtask.get_id())});
    	db.close();
   }
    
   public void saveNote(Note note){
   	SQLiteDatabase db = this.getWritableDatabase();
   	ContentValues values = new ContentValues();
   	values.put(KEY_NOTE, note.getName());
   	values.put(KEY_NOTEDESCRIPTION, note.getDescription());
   	values.put(KEY_DATE, note.getDate());
   	db.update(TABLE_NOTE, values, KEY_ID+" = ?", new String[] { String.valueOf(note.get_id())});
   	db.close();
  }

   public void saveEvent(Event event){
   	SQLiteDatabase db = this.getWritableDatabase();
   	ContentValues values = new ContentValues();
   	values.put(KEY_EVENT, event.getName());
   	values.put(KEY_EVENTDESCRIPTION, event.getDescription());
   	values.put(KEY_DATE, event.getDate());
   	db.update(TABLE_EVENT, values, KEY_ID+" = ?", new String[] { String.valueOf(event.get_id())});
   	db.close();
  }

    
    
  }
