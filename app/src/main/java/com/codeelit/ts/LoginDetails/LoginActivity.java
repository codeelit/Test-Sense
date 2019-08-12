package com.codeelit.ts.LoginDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codeelit.ts.MainActivity;
import com.codeelit.ts.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ImageView loginPhoto;
    private EditText login_mail, login_password;
    private TextView forgotPass, txt_signup;
    private Button loginBtn, signUpbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private Intent MainActivity;
    Animation btnAnim;

    private static final int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton button;
    GoogleSignInAccount account;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        login_mail = findViewById(R.id.login_mail);
        login_password = findViewById(R.id.login_password);
        forgotPass = findViewById(R.id.tvForgotPass);
        txt_signup = findViewById(R.id.sign_up);
        loginBtn = findViewById(R.id.loginBtn);

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        if(user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, com.codeelit.ts.MainActivity.class));
        }

        this.forgotPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity logIn_Activity = LoginActivity.this;
                logIn_Activity.startActivity(new Intent(logIn_Activity, PWresetActivity.class));
                CustomIntent.customType(LoginActivity.this, "bottom-to-up");
            }
        });

        this.txt_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity logIn_Activity = LoginActivity.this;
                logIn_Activity.startActivity(new Intent(logIn_Activity, RegistrationActivity.class));
                CustomIntent.customType(LoginActivity.this, "left-to-right");
            }
        });
        initializeGUI();

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inEmail = login_mail.getText().toString().trim();
                String inPassword = login_password.getText().toString().trim();

                if (TextUtils.isEmpty(inEmail)){
                    login_mail.setError("Invalid Email");
                    login_mail.setFocusable(true);
                }

                if (validateInput(inEmail, inPassword)) {
                    signUser(inEmail, inPassword);
                }
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity logIn_Activity = LoginActivity.this;
                logIn_Activity.startActivity(new Intent(logIn_Activity, RegistrationActivity.class));
                CustomIntent.customType(LoginActivity.this, "left-to-right");
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity logIn_Activity = LoginActivity.this;
                logIn_Activity.startActivity(new Intent(logIn_Activity, PWresetActivity.class));
                CustomIntent.customType(LoginActivity.this, "bottom-to-up");
            }
        });
    }


    public void signUser(String email, String password) {
        progressDialog.setMessage("Verifying...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerification();
                    //TastyToast.makeText(getApplicationContext(), "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //goToMainActivity();
                } else {
                    progressDialog.dismiss();
                    TastyToast.makeText(getApplicationContext(), "Login Unsuccessful: Please check your email i'd and password", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }
            }
        });

    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void UpdateUI() {
        startActivity(MainActivity);
        finish();
    }

    private void initializeGUI() {

        login_mail = findViewById(R.id.login_mail);
        login_password = findViewById(R.id.login_password);
        forgotPass = findViewById(R.id.tvForgotPass);
        txt_signup = findViewById(R.id.sign_up);
        loginBtn = findViewById(R.id.loginBtn);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public boolean validateInput(String inemail, String inpassword) {

        if (inemail.isEmpty()) {
            login_mail.setError("Email field is empty.");
            return false;
        }
        if (inpassword.isEmpty()) {
            login_password.setError("Password is empty.");
            return false;
        }

        return true;
    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            LoginActivity logIn_Activity = LoginActivity.this;
            logIn_Activity.startActivity(new Intent(logIn_Activity, MainActivity.class));
            finish();
            CustomIntent.customType(LoginActivity.this, "fadein-to-fadeout");
        } else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
