package com.codeelit.ts.Learn.LearnCategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.codeelit.ts.NoInternetActivity;
import com.codeelit.ts.R;

public class DescriptionActivity extends AppCompatActivity {

    private static final String TAG ="DescriptionActivity";
    private Bundle extras;
    private Context mBegContext;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_aptitude_description);

        mBegContext = DescriptionActivity.this;
        webView = findViewById(R.id.WebView);
        extras = getIntent().getExtras();
        if (!extras.equals(null)){
            String key;
            String data = extras.getString("titles");
            Log.d(TAG, "onCreate: the coming data is" + data);
            String url = "file:///android_asset/" +data+ ".html";
            webView.loadUrl(url);

            WebSettings web = webView.getSettings();
            web.setBuiltInZoomControls(true);
            web.setDisplayZoomControls(false);
            web.setJavaScriptEnabled(true);
            

            @SuppressLint("WrongConstant")
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
            if (connectivityManager.getNetworkInfo(0).getState() != NetworkInfo.State.CONNECTED) {
                if (connectivityManager.getNetworkInfo(1).getState() != NetworkInfo.State.CONNECTED) {
                    startActivity(new Intent(this, NoInternetActivity.class));
                    finish();
                    return;
                }
            }
        }
    }
}
