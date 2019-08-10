package com.codeelit.ts.Learn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codeelit.ts.R;


public abstract class MasterFragmentCreator extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public abstract Fragment createFragment();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_android_beginners_list);
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.fragment_container) == null) {
            manager.beginTransaction().add((int) R.id.fragment_container, createFragment()).commit();
        }
    }
}
