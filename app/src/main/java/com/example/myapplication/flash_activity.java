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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class flash_activity extends AppCompatActivity {
    private  static  int Timeout=2000;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    ViewDialog viewDialog;
   FirebaseAuth auth;
    FirebaseUser user;
    FirebaseUser currentUser;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_activity);
        viewDialog=new ViewDialog(this);
        auth=FirebaseAuth.getInstance();
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        currentUser=auth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(flash_activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(flash_activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(flash_activity.this, "Location permission is already granted", Toast.LENGTH_SHORT).show();
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
                        //viewDialog.hideDialog();
                        finish();
                    }
                } else {
                    // Request Location Permission
                    ActivityCompat.requestPermissions(flash_activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }


            }
        },Timeout);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            // Check Location permission is granted or not
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
                            //viewDialog.hideDialog();
                            finish();
                        }
                    } else {
                        Toast.makeText(flash_activity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
           }

}
