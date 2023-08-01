package com.khuatlinh.notebookamaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

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
        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());
        saveNotetoFirebase(note);

    }
    void saveNotetoFirebase(Note note){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();
        documentReference.set(note).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //note is added
                            Utility.showToast(NoteDetailActivity.this,"Note added successfully");
                            finish();
                        } else  {
                            Utility.showToast(NoteDetailActivity.this, "Failed while adding note");
                        }
                    }
                }
        );

    }
}