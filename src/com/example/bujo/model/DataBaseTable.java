package com.example.bujo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.bujo.util.BujoDbHandler;

import android.R;
import static java.lang.System.out;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;

public class DataBaseTable {

    private static final String TAG = "BuJo";

    //The columns we'll include in the dictionary table
    public static final String COL_ID = "BULLETID";
    public static final String COL_BULLET_TYPE = "TYPE";
    public static final String COL_NAME = "NAME";
    public static final String COL_DESCRIPTION = "DESCRIPTION";

    private static final String DATABASE_NAME = "BuJoIndex";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;

    public final DatabaseOpenHelper mDatabaseOpenHelper;

    private SQLiteDatabase mDb; 
    private final Context context;
    
    public DataBaseTable(Context context) {
    	this.context = context;
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
        mDb =mDatabaseOpenHelper.getWritableDatabase();
    }

    public static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private static final String FTS_TABLE_CREATE =
                    "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (_id, " +
                    COL_ID + ", " +
                    COL_BULLET_TYPE + ", " +
                    COL_NAME + ", " +
                    COL_DESCRIPTION + ")";

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	db.execSQL(FTS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }

    
    }

    public void loadBulletEntries() {
    	new Thread(new Runnable() {
    		public void run() {
                loadNotes();

    		}
    	}).start();
    }

    private void loadNotes(){
    	BujoDbHandler dbHandler = new BujoDbHandler(context);
    	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    	bullets = dbHandler.getAllNotes();
    	int i = 0;
    	if(bullets != null){
    		while(i < bullets.size()){
    			Bullet aBullet = bullets.get(i);
				addNote(aBullet.get_id(), aBullet.getClass().getName(), aBullet.getName(), aBullet.getDescription());
				i = i +1;
    		}}
    	dbHandler.close();
    }

    public long addNote(int id, String type, String name, String description) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(COL_ID, id);
    	initialValues.put(COL_BULLET_TYPE, type);
    	initialValues.put(COL_NAME, name);
    	initialValues.put(COL_DESCRIPTION, description);
    	return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
    }

    
    public Cursor getBulletMatches(String query, String[] columns) {
    	//mDatabaseOpenHelper.loadBulletEntries();
    	String selection = COL_NAME + " MATCH ?";
    	String[] selectionArgs = new String[] {query+"*"};
    	Cursor c = mDb.rawQuery("SELECT * FROM FTS WHERE NAME MATCH ?", selectionArgs);
    	return c;
    }

    public void DeleteBulletEntries(){
    	mDb.execSQL("DELETE from FTS");
    }
//    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
//    	SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//    	builder.setTables(FTS_VIRTUAL_TABLE);
//
//    	Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
//    			columns, null, null, null, null, null);
//
//    	if (cursor == null) {
//    		return null;
//    	} else if (!cursor.moveToFirst()) {
//    		cursor.close();
//    		return null;
//    	}
//    	return cursor;
//    }

}    
