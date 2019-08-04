
package com.codeelit.ts;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codeelit.ts.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.sdsmdg.tastytoast.TastyToast;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView logo, joinus;
    private EditText username, email, password, college;
    private Button signup;
    private TextView signin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Intent MainActivity;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        initializeGUI();

        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        firebaseAuth = FirebaseAuth.getInstance();

        if(user != null) {
            finish();
            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String inputName = username.getText().toString().trim();
                final String inputPw = password.getText().toString().trim();
                final String inputEmail = email.getText().toString().trim();
                final String inputCollege = college.getText().toString().trim();

                if (validateInput(inputName, inputPw, inputEmail, inputCollege))
                    registerUser(inputName, inputPw, inputEmail, inputCollege);

            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }


    private void initializeGUI() {

        username = findViewById(R.id.atvUsernameReg);
        email = findViewById(R.id.atvEmailReg);
        password = findViewById(R.id.atvPasswordReg);
        college = findViewById(R.id.atvCollegeReg);
        signin = findViewById(R.id.tvSignIn);
        signup = findViewById(R.id.btnSignUp);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(final String inputName, final String inputPw, final String inputEmail, final String inputCollege) {

        progressDialog.setMessage("Verifying...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Student details = new Student(inputName,inputEmail,inputCollege);
                    FirebaseDatabase.getInstance().getReference("Student")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            sendEmailVerification();
                            //TastyToast.makeText(getApplicationContext(), "You've been registered successfully.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            //startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            //goToMainActivity();
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    TastyToast.makeText(getApplicationContext(), "Email already exists.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            }
        });


    }

    private void goToMainActivity() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    private void sendUserData(String username, String college) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference users = firebaseDatabase.getReference("users");
        UserProfile user = new UserProfile(username, college);
        users.push().setValue(user);

    }

    private boolean validateInput(String inName, String intPw, String inEmail, String inpCollege) {

        if (inName.isEmpty()) {
            username.setError("Username is empty.");
            return false;
        }
        if (intPw.isEmpty()) {
            password.setError("Password is empty.");
            return false;
        }
        if (inEmail.isEmpty()) {
            email.setError("Email is empty.");
            return false;
        }
        if (inpCollege.isEmpty()) {
            email.setError("College Name is empty.");
            return false;
        }

        return true;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "Registration Successful, Verification mail sent", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }else {
                        Toast.makeText(RegistrationActivity.this, "Verification mail sent is unsuccessful", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
