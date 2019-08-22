package com.codeelit.ts.Learn.LearnCategory;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeelit.ts.NoInternetActivity;
import com.codeelit.ts.R;

import java.util.ArrayList;

public class BasicAptitude extends AppCompatActivity {

    private static final String TAG ="BasicAptitude";
    private Context mContext;
    ArrayList<String> titleArrayList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_basic_aptitude);

        mContext = BasicAptitude.this;

        titleArrayList = new ArrayList<String>();
        titleArrayList.add(Constant.SEVENTH_SENSE);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Adapter adapter = new Adapter(mContext, titleArrayList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent begdesIntent = new Intent(mContext, DescriptionActivity.class);
                begdesIntent.putExtra("titles", titleArrayList.get(position));
                startActivity(begdesIntent);
            }
        });

        mRecyclerView.setAdapter(adapter);

    }
}
