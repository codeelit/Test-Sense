package com.codeelit.ts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {
    TextView right_answers;


    public MyHolder(@NonNull View itemView) {
        super(itemView);

        right_answers = (TextView)itemView.findViewById(R.id.recyclerView_right);
    }
}
