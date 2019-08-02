package com.codeelit.ts.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeelit.ts.Adapter.MyAdapter;
import com.codeelit.ts.Dummy;
import com.codeelit.ts.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PracticeFragment extends Fragment {

    private CardView cd1, cd2, cd3, cd4, cd5;
    FragmentTransaction ft;

    public PracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_practice, container, false);

        cd1 = view.findViewById(R.id.basic_aptitude);
        cd2 = view.findViewById(R.id.advance_aptitude);
        cd3 = view.findViewById(R.id.technical_aptitude);
        cd4 = view.findViewById(R.id.beginners_prog);
        cd5 = view.findViewById(R.id.advance_prog);

        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dummy.class);
                startActivity(intent);
            }
        });

        return view;

    }


}
