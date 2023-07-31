package com.khuatlinh.notebookamaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginBtnTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
        confirmPasswordEditText = findViewById(R.id.confirm_password_editText);
        createAccountBtn = findViewById(R.id.create_Account_button);
        progressBar = findViewById(R.id.progress_circular);
        loginBtnTextview = findViewById(R.id.login_textview_btn);

        createAccountBtn.setOnClickListener(v -> createAccount());
        loginBtnTextview.setOnClickListener(v -> finish());
    }


    void createAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        Log.i("email",email + " " +password + " " + confirmPassword);
        boolean isValidated = validateData(email, password, confirmPassword);

        if (!isValidated) {
            return;
        }
        createAccountInFirebase(email, password);
    }

    void createAccountInFirebase(String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    //set task is done.
                    Toast.makeText(CreateAccountActivity.this, "Welcome adventure, Please sign your email note", Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    // login
                    firebaseAuth.signOut();
                    finish();
                } else {
                    //failure
                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password, String confirmPassword) {
        // validate the data input by user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password length is invalid");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("2 Passwords are not equals");
            return false;
        }
        return true;
    }
}