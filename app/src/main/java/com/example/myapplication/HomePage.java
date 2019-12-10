package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class HomePage extends AppCompatActivity  implements View.OnClickListener {
    Button markattendance,checkattendace,logout;
    FirebaseAuth auth;
    String user,mobilno,generatedPassword;
    FirebaseFirestore fb;
    String user_name, user_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        logout=findViewById(R.id.bt_logout);
        auth=FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();
        markattendance=findViewById(R.id.mark_attendance);
        checkattendace=findViewById(R.id.check_attendance);
        markattendance.setOnClickListener(this);
        checkattendace.setOnClickListener(this);
        logout.setOnClickListener(this);
        Intent intent=getIntent();
        user= intent.getStringExtra("UserName");
        mobilno=intent.getStringExtra("Authorized");
        Random random = new Random();
        generatedPassword = String.format("%04d", random.nextInt(10000));
        fb.collection("Employee")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() != 0) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            User user = documentSnapshot.toObject(User.class);

                                user_number = user.getNum();
                                user_name = user.getName();

                            }

                    } else {
                        Toast.makeText(HomePage.this, "Data Not Found", Toast.LENGTH_SHORT).show();
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
            case R.id.mark_attendance:
            {

                Toast.makeText(this, user_number, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Detail.class);
               /* intent.putExtra("User_Name",user_name);
                intent.putExtra("Autho_rized",user_number);
                intent.putExtra("Random",generatedPassword);*/
                startActivity(intent);
                break;
            }
            case R.id.check_attendance:
            {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
               /* intent.putExtra("UserNam_e",user_name);
                intent.putExtra("Authorize_d",user_number);
                intent.putExtra("Rando_m",generatedPassword);*/
                startActivity(intent);
                break;
            }
            case R.id.bt_logout:
            {
                auth.signOut();
                Intent intent=new Intent(getApplicationContext(),Verify_NO.class);
                startActivity(intent);
                break;
            }
        }
    }
}
