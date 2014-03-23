package com.example.bujo;

import java.text.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.bujo.model.Note;
import com.example.bujo.util.ApplicationConstants;

public class EditNote extends Activity {

	public Note currentNote;
	public EditText noteName;
	public EditText noteDescription;
	public long date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);
		// Show the Up button in the action bar.
		noteName = (EditText) findViewById(R.id.add_note_name);
		noteDescription = (EditText) findViewById(R.id.add_note_description);
		noteName.requestFocus();
    	ConfigureTextChangedListener(noteName);
    	ConfigureTextChangedListener(noteDescription);
    	currentNote = (Note) this.getIntent().getParcelableExtra(ApplicationConstants.NOTE_OBJECT);
    	
    	if (currentNote != null){
    		noteName.setText(currentNote.getName());
    		noteDescription.setText(currentNote.getDescription());
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
		getMenuInflater().inflate(R.menu.edit_note, menu);
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
			SaveNote(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void SaveNote(View view){
		//Intent intent = new Intent(this, Today.class);
		String name = noteName.getText().toString();
		String desc = noteDescription.getText().toString();
		BujoDbHandler dbHandler = new BujoDbHandler(view.getContext());
		currentNote.setName(name);
		currentNote.setDescription(desc);
		dbHandler.saveNote(currentNote);
	}

	
}
