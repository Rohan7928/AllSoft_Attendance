package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class find extends AppCompatActivity implements LocationListener {
    FloatingActionButton getLoc;
    TextView locationText,currentimev,did;
    FirebaseAuth auth;
    FirebaseFirestore fb;
    FloatingActionButton getLocationBtn;
    private String log;
    private String id;
    private String num;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.find);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
       did=findViewById(R.id.did);
       currentimev=findViewById(R.id.current_time_view);
       getLocationBtn=findViewById(R.id.getLocationBtn);
        getLoc =  findViewById(R.id.loc);
        locationText = (TextView) findViewById(R.id.locationText);
        auth = FirebaseAuth.getInstance();

        fb = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();



        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getLocation(log);
                progressDialog.show();

                     }
                });
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getid();
            }
        });
            }


    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
            log="Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2);
            getLocation(log);

            /*Intent intent = new Intent(getApplicationContext(), timeid.class);
            intent.putExtra("La", String.valueOf(location.getLatitude()));
            intent.putExtra("Lo", String.valueOf(location.getLongitude()));
            startActivity(intent);*/

        } catch (Exception e) {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {

        if ( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        Toast.makeText(find.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
    @SuppressLint("RestrictedApi")
    void getLocation(String log) {


        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        did.setText(id);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
        num =mdformat.format(calendar.getTime());

        TextView textView = (TextView) findViewById(R.id.current_time_view);
        textView.setText(num);
        progressDialog.dismiss();
        getLocationBtn.setVisibility(View.VISIBLE);

       }

    private void getid() {
        progressDialog.show();

        Student stu = new Student(id,num,log);

        fb.collection("Device").document(id).collection("Time")
                .document(num).set(stu).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(find.this, "Added", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(find.this, "Sorry", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(find.this, "Location  permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(find.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}