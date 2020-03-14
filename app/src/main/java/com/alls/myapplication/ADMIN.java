package com.alls.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alls.myapplication.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ADMIN extends AppCompatActivity {
   TextView txtcode;
   EditText etnumber,etname,etemail,etdesignation;
   Button btnasave;
    FirebaseFirestore fb;
    ProgressDialog progressDialog;
    String num,user,time,cd,email,designation,type;
    CheckBox isHR;
    LinearLayout relativeLayout;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), Admin_Option.class);
        startActivity(intent);
        Animatoo.animateFade(ADMIN.this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        fb = FirebaseFirestore.getInstance();
        txtcode=findViewById(R.id.code);
        etnumber=findViewById(R.id.et_num);
        etemail=findViewById(R.id.et_email);
        etdesignation=findViewById(R.id.et_designation);
        btnasave=findViewById(R.id.btn_save);
        etname=findViewById(R.id.et_name);
        isHR=findViewById(R.id.is_hr);
        relativeLayout=findViewById(R.id.Relate);
        progressDialog = new ProgressDialog(this,R.style.CustomDialogTheme);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
        time =mdformat.format(calendar.getTime());
        isHR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    type="isHR";
                }
            }
        });
        btnasave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             progressDialog.show();
             user = etname.getText().toString();
             num = etnumber.getText().toString();
             cd = txtcode.getText().toString();
             String number = cd + num;
             email = etemail.getText().toString();
             designation = etdesignation.getText().toString();

             if (user.isEmpty() || num.isEmpty() || cd.isEmpty() || email.isEmpty() || designation.isEmpty()) {
                 progressDialog.dismiss();
                 Snackbar.make(relativeLayout,"Enter Employee's Detail",Snackbar.LENGTH_LONG).show();
             } else {


                 User admin = new User(number, user, time, email, designation,type);
                 fb.collection("Employee").document(number).set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(ADMIN.this, "Added", Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();

                         Intent intent = new Intent(getApplicationContext(), Admin_Option.class);
                         startActivity(intent);
                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         progressDialog.dismiss();
                         Toast.makeText(ADMIN.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         }
         });
    }
}
