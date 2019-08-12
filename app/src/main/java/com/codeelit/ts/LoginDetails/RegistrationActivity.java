
package com.codeelit.ts.LoginDetails;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.codeelit.ts.MainActivity;
import com.codeelit.ts.R;
import com.codeelit.ts.UserProfile;
import com.codeelit.ts.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;

import maes.tech.intentanim.CustomIntent;

public class RegistrationActivity extends AppCompatActivity {

    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    ImageView ImgUserPhoto;
    ImageView image;
    private EditText name, email, password, college;
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
            startActivity(new Intent(RegistrationActivity.this, com.codeelit.ts.MainActivity.class));
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {

                final String inputName = name.getText().toString().trim();
                final String inputPw = password.getText().toString().trim();
                final String inputEmail = email.getText().toString().trim();
                final String inputCollege = college.getText().toString().trim();


                if (TextUtils.isEmpty(inputName)) {
                    Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Enter username !", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(inputPw)) {
                    Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Enter password!",  Toast.LENGTH_SHORT).show();
                } else if (inputPw.length() < 6) {
                    Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Password too short, enter minimum 6 characters!",  Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Enter email address!",  Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(inputCollege)){
                    Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Enter college address!",  Toast.LENGTH_SHORT).show();
                }

                if (validateInput(inputName, inputPw, inputEmail, inputCollege))
                    registerUser(inputName, inputPw, inputEmail, inputCollege);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationActivity logIn_Activity = RegistrationActivity.this;
                logIn_Activity.startActivity(new Intent(logIn_Activity, LoginActivity.class));
                CustomIntent.customType(RegistrationActivity.this, "right-to-left");
            }
        });

    }


    private void initializeGUI() {

        name = findViewById(R.id.atvUsernameReg);
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

    /*private void updateUserInfo(final String inputName, Uri pickedImgUri, final FirebaseUser currentUser) {

        // first we need to upload user photo to firebase and get url
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //url contain user image url
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(inputName)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            //sendEmailVerification();
                                            // url info updated successfully
                                            showMessage(" ");
                                            CustomIntent.customType(RegistrationActivity.this, "up-to-bottom");
                                            //updateUI();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }*/

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegistrationActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
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
            name.setError("Username is empty.");
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
                        RegistrationActivity logIn_Activity = RegistrationActivity.this;
                        logIn_Activity.startActivity(new Intent(logIn_Activity, LoginActivity.class));
                        finish();
                        CustomIntent.customType(RegistrationActivity.this, "up-to-bottom");
                    }else {
                        Toast.makeText(RegistrationActivity.this, "Verification mail sent is unsuccessful", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
