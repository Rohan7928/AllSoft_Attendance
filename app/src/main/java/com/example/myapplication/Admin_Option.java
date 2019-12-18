package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class Admin_Option extends AppCompatActivity implements View.OnClickListener {

   TextView checkattendnace,addtrainer,exit,emplist,markattendance,leave,shsheet;
   FirebaseAuth auth;
   String number,name;
    String user,mobilno,generatedPassword;
    FirebaseFirestore fb;
   FirebaseUser firebaseUser;
    String user_name, user_number,current;
   TextView admin;
   CardView card_1,card_2,card_3,card_4,card_5,card_6,card_7;
   ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__option);
        progressDialog = new ProgressDialog(this, R.style.CustomDialogTheme);
        progressDialog.setTitle("Please wait for a sec...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        current=firebaseUser.getPhoneNumber();
        exit=findViewById(R.id.bt_log);
        card_1=findViewById(R.id.card1);
        card_2=findViewById(R.id.card2);
        card_3=findViewById(R.id.card3);
        card_4=findViewById(R.id.card4);
        card_5=findViewById(R.id.card5);
        card_6=findViewById(R.id.card6);
        card_7=findViewById(R.id.card7);
        checkattendnace=findViewById(R.id.show_attendance);
        markattendance=findViewById(R.id.hr_attendance);
        addtrainer=findViewById(R.id.addEmployee);
        emplist=findViewById(R.id.employeeList);
        admin=findViewById(R.id.Admin);
        leave=findViewById(R.id.bt_leave);
        shsheet=findViewById(R.id.showsheet);
        fb=FirebaseFirestore.getInstance();
        card_1.setOnClickListener(this);
        card_2.setOnClickListener(this);
        card_3.setOnClickListener(this);
        card_4.setOnClickListener(this);
        card_5.setOnClickListener(this);
        card_6.setOnClickListener(this);
        card_7.setOnClickListener(this);

        progressDialog.show();
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("+918629870458");
        arrayList.add("+917018793629");
        arrayList.add("+917837709702");
        arrayList.add("+919851700100");
        arrayList.add("+919682588655");
       Random random = new Random();
       generatedPassword = String.format("%04d", random.nextInt(10000));
       if(arrayList.contains(current))
       {
           for (int i = 0; i <= arrayList.size(); i++) {
               card_1.setVisibility(View.VISIBLE);
               card_2.setVisibility(View.VISIBLE);
               card_3.setVisibility(View.VISIBLE);
               card_4.setVisibility(View.VISIBLE);
               card_5.setVisibility(View.VISIBLE);
           }
       }
       else
       {
           card_1.setVisibility(View.GONE);
           card_2.setVisibility(View.GONE);
           card_5.setVisibility(View.GONE);
           card_3.setVisibility(View.VISIBLE);
           card_4.setVisibility(View.VISIBLE);
       }
      fb.collection("Employee")
               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   if (task.getResult().size() != 0) {
                       for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                           User user = documentSnapshot.toObject(User.class);
                          if(current.equals(user.getNum())) {
                              user_number = user.getNum();
                              user_name = user.getName();
                              admin.setText("Welcome"+"\n"+user_name);
                              progressDialog.dismiss();
                          }
                       }

                   } else {
                       Toast.makeText(Admin_Option.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });


   }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.card1: {
                startActivity(new Intent(getApplicationContext(), ADMIN.class));
                Animatoo.animateSplit(Admin_Option.this);
                break;
            }

            case R.id.card2: {
                startActivity(new Intent(getApplicationContext(), Employee_list.class));
                Animatoo.animateSplit(Admin_Option.this);
                break;
            }
            case R.id.card3: {

                Intent intent=new Intent(getApplicationContext(),Detail.class);
                intent.putExtra("User_Name",user_name);
                intent.putExtra("Autho_rized",current);
                intent.putExtra("Random",generatedPassword);
                startActivity(intent);
                Animatoo.animateSplit(Admin_Option.this);
                break;
            }
            case R.id.card4: {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("UserNam_e",user_name);
                intent.putExtra("Authorize_d",current);
                intent.putExtra("Rando_m",generatedPassword);
                startActivity(intent);
                Animatoo.animateSplit(Admin_Option.this);
                break;
            }
            case R.id.card6:
            {

                Intent intent=new Intent(getApplicationContext(),leave.class);
                startActivity(intent);
                Animatoo.animateSlideRight(Admin_Option.this);
                break;
            }
            case R.id.card7: {
                auth.signOut();
                Animatoo.animateSlideRight(Admin_Option.this);
                this.finish();
                break;
            }
            case R.id.card5:
            {
                startActivity(new Intent(getApplicationContext(),UserList.class));
                break;
            }
            }
   }
}
