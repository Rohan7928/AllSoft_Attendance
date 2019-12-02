package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class flash_activity extends AppCompatActivity {
    private  static  int Timeout=3000;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(flash_activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(flash_activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(flash_activity.this, "Location permission is already granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Request Location Permission
                    ActivityCompat.requestPermissions(flash_activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }

                Intent intent=new Intent(flash_activity.this,find.class);
                startActivity(intent);
              finish();
            }
        },Timeout);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            // Check Location permission is granted or not
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(flash_activity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(flash_activity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                    }
           }

}
