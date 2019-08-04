package com.codeelit.ts.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.codeelit.ts.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnFragment extends Fragment {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayoutAndroid;
    CoordinatorLayout rootLayoutAndroid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);
        final Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);



        final CollapsingToolbarLayout c1 = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingtb_explore_detail);

        AppBarLayout appBarLayout = (AppBarLayout)view.findViewById(R.id.appbarlayout_explore_detail) ;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0){
                    collapsingToolbarLayoutAndroid.setTitle(" ");
                    isShow = true;
                }else if (isShow){
                    collapsingToolbarLayoutAndroid.setTitle(" ");
                    isShow = false;
                }
            }
        });
        c1.setTitle("Learn");
        return view;
    }

}
