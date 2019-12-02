package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ADMIN extends AppCompatActivity {
   TextView txtcode;
   EditText etnumber,etname,etemail,etdesignation,etcode;
   ImageView btnasave;
    FirebaseFirestore fb;
    ProgressDialog progressDialog;
    String num,user,time,cd,email,designation,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        fb = FirebaseFirestore.getInstance();
        txtcode=findViewById(R.id.code);
        etnumber=findViewById(R.id.et_num);
        etemail=findViewById(R.id.et_email);
        etcode=findViewById(R.id.et_code);
        etdesignation=findViewById(R.id.et_designation);
        btnasave=findViewById(R.id.btn_save);
        etname=findViewById(R.id.et_name);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
        time =mdformat.format(calendar.getTime());

        btnasave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            progressDialog.show();
             user=etname.getText().toString();
            num=etnumber.getText().toString();
            cd=txtcode.getText().toString();
            String number=cd+num;
            email=etemail.getText().toString();
            designation=etdesignation.getText().toString();
            code=etcode.getText().toString();

            User admin=new User(number,user,time,email,code,designation);
             fb.collection("Admin").document(number).collection("Time").document(time).
                     set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     Toast.makeText(ADMIN.this, "Added", Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();

                    Intent intent=new Intent(getApplicationContext(),Login.class);
                     startActivity(intent);
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     progressDialog.dismiss();
                     Toast.makeText(ADMIN.this, "Sorry", Toast.LENGTH_SHORT).show();
                 }
             });
         }
     });
    }

}
