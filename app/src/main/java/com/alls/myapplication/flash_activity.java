package com.alls.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class flash_activity extends AppCompatActivity {
    private static int Timeout = 3000;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseUser currentUser;
    String phone;
    LinearLayout linearLayout;
    ImageView imageView;
    protected LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_activity);
        linearLayout=findViewById(R.id.main);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = auth.getCurrentUser();
        boolean connection=isNetworkAvailable();
        imageView=findViewById(R.id.logo_);
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        imageView.startAnimation(animation);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && connection)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ContextCompat.checkSelfPermission(flash_activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(flash_activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (user==null)
                        {
                            Intent intent=new Intent(flash_activity.this,Verify_NO.class);
                            startActivity(intent);
                            Animatoo.animateInAndOut(flash_activity.this);
                        }else {
                            phone=currentUser.getPhoneNumber();
                            Intent intent = new Intent(flash_activity.this, Admin_Option.class);
                            intent.putExtra("Values",phone);
                            startActivity(intent);
                            Animatoo.animateSplit(flash_activity.this);
                            finish();
                        }
                    } else {
                        ActivityCompat.requestPermissions(flash_activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                    }

                }
            },Timeout);
        }
        else {
             startActivity(new Intent(getApplicationContext(),Gps.class));
            Toast.makeText(flash_activity.this, "GPS is Disabled", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(flash_activity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();
                        if (user==null)
                        { Intent intent=new Intent(flash_activity.this,Verify_NO.class);
                            startActivity(intent);
                            Animatoo.animateInAndOut(flash_activity.this);
                            finish();
                        }else {
                            currentUser=auth.getCurrentUser();
                            phone=currentUser.getPhoneNumber();
                            Intent intent = new Intent(flash_activity.this, Admin_Option.class);
                            intent.putExtra("Values",phone);
                            startActivity(intent);
                            Animatoo.animateSplit(flash_activity.this);
                            finish();
                        }
                    } else {
                        this.finish();
                    }
           }

}
