package com.khuatlinh.notebookamaria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class NoteDetailActivity extends AppCompatActivity {

    EditText titleEdittext,contentEditText;
    ImageButton saveNotebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        titleEdittext = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_Content_text);
        saveNotebtn = findViewById(R.id.save_note_btn);
        saveNotebtn.setOnClickListener((v)-> saveNote());
    }

    void saveNote() {
        String noteTitle = titleEdittext.getText().toString();
        String noteContent = contentEditText.getText().toString();
        if(noteTitle == null || noteTitle.isEmpty()){
            titleEdittext.setError("Title is not null");
            return;
        }

    }
}