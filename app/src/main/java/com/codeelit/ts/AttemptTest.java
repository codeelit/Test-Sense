package com.codeelit.ts;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codeelit.ts.model.ResultCategoryList;
import com.codeelit.ts.model.Test;
import com.codeelit.ts.model.question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AttemptTest extends AppCompatActivity {

    ArrayList<question> questions;
    String[] answers;
    String name;
    Toolbar toolbar;
    DiscreteScrollView scrollView;
    LinearLayout indexLayout;
    GridView quesGrid;
    long timer;// =((Test) getIntent().getExtras().get("Questions")).getTime()*60*1000;
    popGridAdapter popGrid;
    Button next, prev;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    TextView qstnNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_attempt_test);
        questions = ((Test) getIntent().getExtras().get("Questions")).getQuestions();
        toolbar = findViewById(R.id.toolbar);
        answers = new String[questions.size()];
        setSupportActionBar(toolbar);
        scrollView = findViewById(R.id.discrete);
        final QuestionAdapter questionAdapter = new QuestionAdapter(questions);
        scrollView.setAdapter(questionAdapter);
        for (int i=0;i<questions.size();i++){
            Log.e("Nodama", "fine "+questions.get(i).getAnswer() );
        }
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scrollView.getCurrentItem() == questions.size() - 1) {
                    showPopUp();
                } else {
                    //setNextPrevButton(scrollView.getCurrentItem() + 1);
                    scrollView.smoothScrollToPosition(scrollView.getCurrentItem() + 1);
                }
            }
        });
        prev = findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scrollView.getCurrentItem() != 0) {
                    //setNextPrevButton(scrollView.getCurrentItem()-1);
                    scrollView.smoothScrollToPosition(scrollView.getCurrentItem() - 1);
                }
            }
        });
        setNextPrevButton(scrollView.getCurrentItem());
        indexLayout = findViewById(R.id.index_layout);
        indexLayout.setAlpha(.5f);
        quesGrid = findViewById(R.id.pop_grid);
        popGrid = new popGridAdapter(AttemptTest.this);
        quesGrid.setAdapter(popGrid);
        quesGrid.setNumColumns(popGrid.getCount());
        quesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                scrollView.smoothScrollToPosition(i + 1);
                slideUp(indexLayout);
            }
        });
        scrollView.addScrollListener(new DiscreteScrollView.ScrollListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
                setNextPrevButton(newPosition);
            }
        });
        timer = ((Test) getIntent().getExtras().get("Questions")).getTime() * 60 * 1000;


    }

    void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AttemptTest.this);
        builder.setMessage("Do you want to submit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                submit();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

    void submit() {
        ArrayList<ResultCategoryList> wrong_Answerd_Category=new ArrayList<>();
        ArrayList<ResultCategoryList> right_Answerd_Category=new ArrayList<>();
        ArrayList<String> question=new ArrayList<>();
        ArrayList<String> explain=new ArrayList<>();

        int score = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] != null && answers[i].equals(questions.get(i).getAnswer())) {
                ResultCategoryList resultCategoryList=new ResultCategoryList();
                resultCategoryList.setCategory(questions.get(i).getCategory());
                right_Answerd_Category.add(resultCategoryList);

                score++;
            }
            else {
                ResultCategoryList resultCategoryList=new ResultCategoryList();
                resultCategoryList.setCategory(questions.get(i).getCategory());
                wrong_Answerd_Category.add(resultCategoryList);
            }
            Log.e("List", "right"+right_Answerd_Category.size());
            Log.e("List", "wrong"+wrong_Answerd_Category.size());

        }
        try {
            mDatabase.child("Results").child(((Test) getIntent().getExtras().get("Questions")).getName()).child(auth.getUid()).setValue(score);
        } catch (Exception e) {
            Log.e("Result Update Failed ", e.getMessage());
        }
        for (int i=0;i<question.size();i++){
            Log.e("question",i+question.get(i));
        }
        for (int i=0;i<explain.size();i++){
            Log.e("explain",i+explain.get(i));
        }

        Intent intent=new Intent(AttemptTest.this,Result.class);
        intent.putExtra("wrong_category",wrong_Answerd_Category);
        intent.putExtra("right_category",right_Answerd_Category);
        intent.putExtra("question",questions);
        intent.putExtra("user_answer",answers);
        intent.putExtra("no_of_question",questions.size());
        intent.putExtra("no_of_right_answer",score);
        Log.e("No of questions", "submit: "+ questions.size());
        this.finish();
        overridePendingTransition(0,0);

        startActivity(intent);




    }


    void setNextPrevButton(int pos) {
        if (pos == 0) {
            prev.setBackground(getResources().getDrawable(R.drawable.prev_gray));
            prev.setVisibility(View.GONE);
            prev.setText("");
        } else {
            prev.setBackground(getResources().getDrawable(R.drawable.prev_blue));
            prev.setVisibility(View.VISIBLE);
            prev.setText("Previous");

        }
        if (pos == questions.size() - 1) {
            next.setText("Submit");
            next.setVisibility(View.VISIBLE);
            next.setBackground(getResources().getDrawable(R.drawable.next_red));
        } else {
            next.setText("Next");
            next.setVisibility(View.VISIBLE);
            next.setBackground(getResources().getDrawable(R.drawable.next_blue));
        }
    }

    @Override
    public void onBackPressed() {
        showPopUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.attempt_menu, menu);
        final MenuItem counter = menu.findItem(R.id.counter);

        new CountDownTimer(timer, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                long hr = TimeUnit.MILLISECONDS.toHours(millis), mn = (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))), sc = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
                String hms = format(hr) + ":" + format(mn) + ":" + format(sc);
                counter.setTitle(hms);
                timer = millis;
            }

            public String format(long n) {
                if (n < 10)
                    return "0" + n;
                else return "" + n;
            }

            public void onFinish() {
                submit();
            }
        }.start();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.submit) {
            showPopUp();

            return true;
        } else if (id == R.id.info) {
            togglePopUp();
        }
        return super.onOptionsItemSelected(item);
    }

    void togglePopUp() {
        if (indexLayout.getVisibility() == View.GONE) {
            slideDown(indexLayout);
        } else slideUp(indexLayout);
    }

    class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

        private int itemHeight;
        public ArrayList<question> data;

        public QuestionAdapter(ArrayList<question> data) {
            this.data = data;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            Activity context = (Activity) recyclerView.getContext();
            Point windowDimensions = new Point();
            context.getWindowManager().getDefaultDisplay().getSize(windowDimensions);
            itemHeight = Math.round(windowDimensions.y * 0.6f);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.frag_test, parent, false);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    itemHeight);
            v.setLayoutParams(params);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.questionText.setText(data.get(position).getQuestion());
            holder.r1.setText(data.get(position).getOpt_A());
            holder.r2.setText(data.get(position).getOpt_B());
            holder.r3.setText(data.get(position).getOpt_C());
            holder.r4.setText(data.get(position).getOpt_D());
            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.radioButton) {
                        answers[position] = holder.r1.getText().toString();
                    } else if (i == R.id.radioButton2) {
                        answers[position] = holder.r2.getText().toString();
                    } else if (i == R.id.radioButton3) {
                        answers[position] = holder.r3.getText().toString();
                    } else if (i == R.id.radioButton4) {
                        answers[position] = holder.r4.getText().toString();
                    } else {
                        answers[position] = null;
                    }
                    popGrid.notifyDataSetChanged();
                }
            });
            if (answers[position] == null) {
                holder.radioGroup.clearCheck();
            } else if (answers[position].equals(data.get(position).getAnswer())) {
                holder.radioGroup.check(R.id.radioButton);
            } else if (answers[position].equals(data.get(position).getAnswer())) {
                holder.radioGroup.check(R.id.radioButton2);
            } else if (answers[position].equals(data.get(position).getAnswer())) {
                holder.radioGroup.check(R.id.radioButton3);
            } else if (answers[position].equals(data.get(position).getAnswer())) {
                holder.radioGroup.check(R.id.radioButton4);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private View overlay;
            private TextView questionText, qstNo;
            private RadioGroup radioGroup;
            private RadioButton r1, r2, r3, r4;

            public ViewHolder(View itemView) {
                super(itemView);
                questionText = (TextView) itemView.findViewById(R.id.questionTextView);
                radioGroup = itemView.findViewById(R.id.radioGroup);
                r1 = itemView.findViewById(R.id.radioButton);
                r2 = itemView.findViewById(R.id.radioButton2);
                r3 = itemView.findViewById(R.id.radioButton3);
                r4 = itemView.findViewById(R.id.radioButton4);

            }

            public void setOverlayColor(@ColorInt int color) {
                overlay.setBackgroundColor(color);
            }
        }
    }

    class popGridAdapter extends BaseAdapter {
        Context mContext;

        public popGridAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View convertView;
            if (view == null) {
                convertView = new Button(mContext);
            } else convertView = view;
            if (answers[i] != null)
                ((Button) convertView).setBackgroundColor(getResources().getColor(R.color.green));

            else {
                ((Button) convertView).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                Log.e("The read success: ", "Worst code");
            }
            ((Button) convertView).setText("" + (i + 1));
            ((Button) convertView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setNextPrevButton(i);
                    scrollView.smoothScrollToPosition(i);
                }
            });
            return convertView;
        }
    }

    public void slideUp(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(500);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        view.startAnimation(animate);

    }

    @Override
    protected void onDestroy() {
        submit();
        super.onDestroy();
    }
}
