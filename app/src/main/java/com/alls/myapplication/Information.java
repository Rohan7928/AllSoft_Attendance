package com.alls.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Information extends AppCompatActivity {
    TextView locationText, currentimev, did, useraddress,username,usertype;
    String location,timev,idi,address,name,type,url;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        locationText=findViewById(R.id.location_Text);
        currentimev=findViewById(R.id.c_timeview);
        did=findViewById(R.id.id_id);
        useraddress=findViewById(R.id.u_address);
        username=findViewById(R.id.u_name);
        usertype=findViewById(R.id.ty_pe);
        imageView=findViewById(R.id.show_photo);
        Intent intent=getIntent();
        location=intent.getStringExtra("location");
        timev=intent.getStringExtra("time");
        idi=intent.getStringExtra("Id");
        address=intent.getStringExtra("Address");
        name=intent.getStringExtra("Type");
        type=intent.getStringExtra("Name");
        url=intent.getStringExtra("Photo");
        locationText.setText(location);
        currentimev.setText(timev);
        useraddress.setText(address);
        username.setText(name);
        usertype.setText(type);
        did.setText(idi);
        Picasso.get().load(url).into(imageView);


    }
}
