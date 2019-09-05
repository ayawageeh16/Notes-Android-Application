package com.example.architecturecomponentsmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.architecturecomponentsmvvm.Entity.Note;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "architecturecomponentsmvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "architecturecomponentsmvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "architecturecomponentsmvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "architecturecomponentsmvvm.EXTRA_PRIORITY";

    private EditText noteTitle;
    private EditText noteDescription;
    private NumberPicker priorityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitle = findViewById(R.id.add_note_title);
        noteDescription = findViewById(R.id.add_note_description);
        priorityPicker = findViewById(R.id.priority_number_picker);

        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(10);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            noteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            noteDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        } else {
            setTitle("Add Note");
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveNote() {
        String title = noteTitle.getText().toString();
        String description = noteDescription.getText().toString();
        int priority = priorityPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "please insert missing data", Toast.LENGTH_LONG).show();
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        // Send data back to MainActivity
        setResult(RESULT_OK, data);
        finish();
    }
}
