package com.alls.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.alls.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class leave extends AppCompatActivity {
    private WebView webView = null;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        relativeLayout=findViewById(R.id.relative_web);
        this.webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebContentsDebuggingEnabled(true);
        webView.setScrollBarStyle(relativeLayout.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScbMBmqUtErfAdJx52KR4WfG000O7Z4ORePWc6wwGuMGN81vw/viewform");

    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar=Snackbar.make(relativeLayout,"Exit the app",Snackbar.LENGTH_LONG);
        snackbar.show();
        startActivity(new Intent(getApplicationContext(),Admin_Option.class));
      this.finishAffinity();

    }
}
