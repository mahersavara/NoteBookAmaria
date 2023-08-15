package com.khuatlinh.notebookamaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailActivity extends AppCompatActivity {

    EditText titleEdittext,contentEditText;
    ImageButton saveNotebtn;
    TextView pageTitleTextview;
    String title,content,docId;

    //if receive docId -> true
    boolean isEditMode= false;
    TextView deleteTextviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        titleEdittext = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_Content_text);
        saveNotebtn = findViewById(R.id.save_note_btn);
        pageTitleTextview = findViewById(R.id.page_title);
        deleteTextviewBtn =findViewById(R.id.delete_note_textView_btn);

        //! receive data
        title= getIntent().getStringExtra("title");
        content= getIntent().getStringExtra("content");
        docId= getIntent().getStringExtra("docId");

        //delete


        if(docId!=null && !docId.isEmpty()){
            isEditMode = true;
        }

        titleEdittext.setText(title);
        contentEditText.setText(content);
       if(isEditMode){
           pageTitleTextview.setText("Edit your note");
           deleteTextviewBtn.setVisibility(View.VISIBLE);
       }

        saveNotebtn.setOnClickListener((v)-> saveNote());

        deleteTextviewBtn.setOnClickListener((v)-> deleteNoteFromFirebase());
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
        if(isEditMode){
            // update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        } else{
            //create new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }
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

    void deleteNoteFromFirebase(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        documentReference.delete().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //note is deleted
                            Utility.showToast(NoteDetailActivity.this,"Note deleted successfully");
                            finish();
                        } else  {
                            Utility.showToast(NoteDetailActivity.this, "Failed while deleting note");
                        }
                    }
                }
        );
    }
}