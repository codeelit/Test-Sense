package com.codeelit.ts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.codeelit.ts.ShowAnswer.ShowAnswers;
import com.codeelit.ts.model.ResultCategoryList;
import com.codeelit.ts.model.Test;
import com.codeelit.ts.model.question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;


public class Result extends AppCompatActivity {
    TextView no_of_questions, correct_answers, wrong_answers;
    Button mShowAnswer;
    private ArrayList<ResultCategoryList> m_correct_list = new ArrayList<>();

    RecyclerView recyclerView_correct;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView_correct = (RecyclerView) findViewById(R.id.recyclerView_right);
        mShowAnswer = findViewById(R.id.show_answers);
        no_of_questions = findViewById(R.id.no_of_questions);
        correct_answers = findViewById(R.id.correct_answers);
        wrong_answers = findViewById(R.id.wrong_answers);

        final ArrayList<com.codeelit.ts.model.question> myList = (ArrayList<question>) getIntent().getSerializableExtra("question");
        final ArrayList<ResultCategoryList> correct_category = (ArrayList<ResultCategoryList>) getIntent().getSerializableExtra("right_category");
        final ArrayList<ResultCategoryList> wrong_category = (ArrayList<ResultCategoryList>) getIntent().getSerializableExtra("wrong_category");

        int score = (int) getIntent().getIntExtra("no_of_right_answer", 0);
        int noof = (int) getIntent().getIntExtra("no_of_question", 0);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, ShowAnswers.class);
                intent.putExtra("questions", myList);
                startActivity(intent);

            }
        });

      /*  question.setText(myList.get(0).getQuestion());
        explain.setText(myList.get(0).getAnswer());
        right.setText("Correct " + score);
        noofq.setText("out of" + noof);*/

      for(int i =0;i<myList.size();i++){
          Log.e("Nodolo", "onEverting: " + myList.get(i).getQuestion());
          Log.e("Nodolo", "onEverting: " + myList.get(i).getOpt_A());
          Log.e("Nodolo", "onEverting: " + myList.get(i).getOpt_B());
          Log.e("Nodolo", "onEverting: " + myList.get(i).getOpt_C());
          Log.e("Nodolo", "onEverting: " + myList.get(i).getOpt_D());

      }

        for (int i = 0; i < correct_category.size(); i++) {
            /*Toast.makeText(this,"correct category"+correct_category.get(i),Toast.LENGTH_SHORT).show();*/
            Log.e("Nodo", "onCorrrect: " + correct_category.get(i).getCategory());
        }
        for (int i = 0; i < wrong_category.size(); i++) {
            /*Toast.makeText(this,"correct category"+correct_category.get(i),Toast.LENGTH_SHORT).show();*/
            Log.e("Nodo", "onWrong " + wrong_category.get(i).getCategory());
        }
        ResultCategoryAdapter adapter = new ResultCategoryAdapter(this, wrong_category);
        recyclerView_correct.setHasFixedSize(true);
        recyclerView_correct.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_correct.setAdapter(adapter);


       /* List pieData = new ArrayList<>();

        pieData.add(new SliceValue(noof-score, Color.rgb(200,0,25)).setLabel("Wrong Answers"));
        pieData.add(new SliceValue(score, Color.rgb(25,0,200)).setLabel("Right Answers"));
        Log.e("H", "score is"+score );

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1(((score/noof)*100)+"%  "+"Well Done").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
