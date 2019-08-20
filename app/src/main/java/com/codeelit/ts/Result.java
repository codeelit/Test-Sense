package com.codeelit.ts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codeelit.ts.model.question;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Result extends AppCompatActivity {
    TextView question;
    TextView explain;
    TextView noofq;
    TextView right;
    PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ArrayList<com.codeelit.ts.model.question> myList = (ArrayList<question>) getIntent().getSerializableExtra("question");

        int score = (int) getIntent().getIntExtra("no_of_right_answer", 0);
        int noof = (int) getIntent().getIntExtra("no_of_question", 0);
        question.setText(myList.get(0).getQuestion());
        explain.setText(myList.get(0).getAnswer());
        right.setText("Correct " + score);
        noofq.setText("out of" + noof);


        /*List pieData = new ArrayList<>();

        pieData.add(new SliceValue(noof-score, Color.rgb(200,0,25)).setLabel("Wrong Answers"));
        pieData.add(new SliceValue(score, Color.rgb(25,0,200)).setLabel("Right Answers"));
        Log.e("H", "score is"+score );

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1(((score/noof)*100)+"%  "+"Well Done").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);*/

    }
}
