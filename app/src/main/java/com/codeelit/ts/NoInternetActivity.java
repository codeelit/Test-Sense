package com.codeelit.ts;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.codeelit.ts.Learn.LearnCategory.BasicAptitude;

public class NoInternetActivity extends AppCompatActivity {

    class C15691 implements OnClickListener {
        C15691() {
        }

        public void onClick(View view) {
            NoInternetActivity.this.startActivity(new Intent(NoInternetActivity.this, BasicAptitude.class));
            NoInternetActivity.this.finish();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(512, 512);
            getWindow().setStatusBarColor(0);
        }
        setContentView(R.layout.activity_no_internet);
        ((Button) findViewById(R.id.startagain)).setOnClickListener(new C15691());
    }
}
