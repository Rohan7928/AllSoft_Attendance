package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;

public class Gps extends AppCompatActivity {
TextView textView;
ImageView gps_on;
SwipeRefreshLayout linearLayout;
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        textView=findViewById(R.id.text_view);
        gps_on=findViewById(R.id.gpss);
        linearLayout=findViewById(R.id.refresh_out);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        linearLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               boolean connection=isNetworkAvailable();
               boolean gps=isGpsAvailable();
               if(connection && gps==true){
                   textView.setText("Connected");
                   textView.setTextColor(Color.WHITE);
                   textView.setBackgroundColor(Color.GREEN);
                   linearLayout.setRefreshing(false);
                   linearLayout.setRefreshing(false);
                   gps_on.setVisibility(View.VISIBLE);
                   startActivity(new Intent(getApplicationContext(),flash_activity.class));
               }
               else{
                   gps_on.setVisibility(View.VISIBLE);
                   linearLayout.setRefreshing(false);
                   textView.setText("Not connected");
                   textView.setTextColor(Color.WHITE);
                   textView.setBackgroundColor(Integer.parseInt("#ff3333"));
                   Snackbar snackbar=Snackbar.make(linearLayout,"Check Your GPS or Internet Connetcion",Snackbar.LENGTH_LONG);
                   View snackbarView = snackbar.getView();
                   snackbarView.setBackgroundColor(Color.parseColor("#ff3333"));
                   snackbar.show();

               }

           }
       });
        linearLayout.setColorSchemeColors(Color.RED);
    }
    private boolean isGpsAvailable() {
         if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
               {
                  return true;
               }
               else {
                   return false;
               }
    }
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }
}
