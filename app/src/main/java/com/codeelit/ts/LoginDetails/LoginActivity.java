package com.codeelit.ts.LoginDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.sdsmdg.tastytoast.TastyToast;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ImageView logo, ivSignIn, btnTwitter;
    private EditText login_mail, login_password;
    private TextView forgotPass, txt_signup;
    private Button loginBtn, signUpbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
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
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(mAuthListener);
    }

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
        firebaseAuth = firebaseAuth.getInstance();

        // Google Signin
        /*button = (SignInButton) findViewById(R.id.btnGoogle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    LoginActivity logIn_Activity = LoginActivity.this;
                    logIn_Activity.startActivity(new Intent(logIn_Activity, com.codeelit.ts.MainActivity.class));
                    CustomIntent.customType(LoginActivity.this, "fadein-to-fadeout");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


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


        if (user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inEmail = login_mail.getText().toString();
                String inPassword = login_password.getText().toString();

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

    ///GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    ///       .requestIdToken(getString(R.string.default_web_client_id))
    ///       .requestEmail()
    ///      .build();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());
        }else {
            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener !=null){
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithCredential:failure");
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
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

        logo = findViewById(R.id.ivLogLogo);
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
            CustomIntent.customType(LoginActivity.this, "fadein-to-fadeout");
        } else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
