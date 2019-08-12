package com.codeelit.ts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.codeelit.ts.model.question;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    TextView question;
    TextView explain;
    TextView noofq;
    TextView right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        question = (TextView) findViewById(R.id.questionres);
        explain = (TextView) findViewById(R.id.expalin);
        noofq = (TextView) findViewById(R.id.noofq);
        right = (TextView) findViewById(R.id.right);
        ArrayList<com.codeelit.ts.model.question> myList = (ArrayList<question>) getIntent().getSerializableExtra("question");

        int score = (int) getIntent().getIntExtra("no_of_right_answer", 0);
        int noof = (int) getIntent().getIntExtra("no_of_question", 0);
        question.setText(myList.get(0).getQuestion());
        explain.setText(myList.get(0).getAnswer());
        right.setText("Correct " + score);
        noofq.setText("out of" + noof);

    }
}
