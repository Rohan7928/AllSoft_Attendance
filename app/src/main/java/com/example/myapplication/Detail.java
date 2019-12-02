package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class Detail extends AppCompatActivity {
    CircularImageView profilepic;
    TextView username,userid,userdate,userlatlong,usertype,useraddress;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        username=findViewById(R.id.user_name);
        userid=findViewById(R.id.user_date);
        userdate=findViewById(R.id.user_address);
        userlatlong=findViewById(R.id.user_latlong);
        usertype=findViewById(R.id.user_type);
        profilepic=findViewById(R.id.profile_image);
        edit=findViewById(R.id.profile_edit);
        useraddress=findViewById(R.id.user_address);


    }
}
