package com.codeelit.ts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codeelit.ts.Fragments.CompaniesFragment;
import com.codeelit.ts.Fragments.DiscussionFragment;
import com.codeelit.ts.Fragments.SettingsFragment;
import com.codeelit.ts.Learn.LearnFragment;
import com.codeelit.ts.LoginDetails.LoginActivity;
import com.codeelit.ts.Practice.PracticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button btnLogout;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    BottomNavigationView bottomNavigationView;
    FrameLayout mainFrame;
    private int position;

    LearnFragment learnFragment;
    PracticeFragment practiceFragment;
    CompaniesFragment companiesFragment;
    DiscussionFragment discussionFragment;
    SettingsFragment settingsFragment;
    TextView privacy;
    TextView terms;
    DrawerLayout drawer;

    TextView nav_username;
    TextView userEmailHeader;
    String getUserHeaderEmail;

    Toolbar toolbarMain;

    private static final String TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.toolbarMain = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(this.toolbarMain);
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_nav);
        this.mainFrame = (FrameLayout) findViewById(R.id.changeFrame);
        this.learnFragment = new LearnFragment();
        this.practiceFragment = new PracticeFragment();
        this.companiesFragment = new CompaniesFragment();
        this.discussionFragment = new DiscussionFragment();
        setFragment(this.learnFragment);
        this.position = 0;

        this.bottomNavigationView.getMenu().getItem(0).setChecked(true);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_post_home /*2131361888*/:
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.setFragment(mainActivity.learnFragment);
                        MainActivity.this.bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.bottom_video /*2131361890*/:
                        MainActivity mainActivity2 = MainActivity.this;
                        mainActivity2.setFragment(mainActivity2.practiceFragment);
                        MainActivity.this.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case R.id.bottom_companies /*2131361891*/:
                        MainActivity mainActivity3 = MainActivity.this;
                        mainActivity3.setFragment(mainActivity3.companiesFragment);
                        MainActivity.this.bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        break;
                    case R.id.bottom_feed /*2131361892*/:
                        MainActivity mainActivity4 = MainActivity.this;
                        mainActivity4.setFragment(mainActivity4.discussionFragment);
                        MainActivity.this.bottomNavigationView.getMenu().getItem(3).setChecked(true);
                        break;
                }
                return false;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        this.privacy = (TextView) findViewById(R.id.privacypolicy_menu);
        this.terms = (TextView) findViewById(R.id.termsandconditionmenu);

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, this.toolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        firebaseAuth = FirebaseAuth.getInstance();
        this.userEmailHeader = (TextView) headerView.findViewById(R.id.userEmailHeader);
        this.user = firebaseAuth.getCurrentUser();
        this.getUserHeaderEmail = this.user.getEmail();
        this.userEmailHeader.setText(this.getUserHeaderEmail);

        /*PracticeFragment fragment1 = new PracticeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container, fragment1, "");
        ft1.commit();
        */

        //BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        /*bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.learn:
                                PracticeFragment fragment1 = new PracticeFragment();
                                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                                ft1.replace(R.id.fragment_container, fragment1, "");
                                ft1.commit();
                                return true;
                            case R.id.practice:
                                PracticeFragment fragment2= new PracticeFragment();
                                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                                ft2.replace(R.id.fragment_container, fragment2, "");
                                ft2.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                                ft2.commit();
                                return true;
                            case R.id.companies:
                                CompaniesFragment fragment3 = new CompaniesFragment();
                                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                                ft3.replace(R.id.fragment_container, fragment3, "");
                                ft3.commit();
                                return true;
                            case R.id.discussion:
                                DiscussionFragment fragment4 = new DiscussionFragment();
                                FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                                ft4.replace(R.id.fragment_container, fragment4, "");
                                ft4.commit();
                                return true;
                        }
                        return true;
                    }
                });*/
    }


    public void setFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.changeFrame, fragment);
        beginTransaction.commit();
    }

    private void checkCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){

        }else {

        }
    }

    public void getUserProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();

            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*@SuppressLint("WrongConstant")
    public void onBackPressed() {
        new BottomDialog.Builder(this)
                .setTitle("Exit Dialog !")
                .setContent("Are you sure do want to Exit?")
                .setPositiveText("OK")
                .setNegativeText("CANCEL")
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .setNegativeTextColor(R.color.red)
                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        finish();
                    }
                })
                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                    }
                })

                .show();
        return;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String str = "android.intent.action.SEND";
        String str2 = "left-to-right";
        switch (menuItem.getItemId()) {
            case R.id.drwar_about /*2131361995*/:
                break;
            case R.id.drwar_feedback /*2131361999*/:

                break;
            case R.id.drwar_logout /*2131362000*/:
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
                break;
            case R.id.drwar_share /*2131362002*/:
                break;
        }
        this.drawer.closeDrawer((int) GravityCompat.START);
        return true;
    }


}
