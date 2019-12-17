package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
