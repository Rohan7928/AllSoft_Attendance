package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainPAge extends AppCompatActivity {
  Button ashr;
    TextView etnumber;
    Button btnasave;
    FirebaseFirestore fb;
    ProgressDialog progressDialog;
    String num;
    TextView txtcode;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ashr=findViewById(R.id.h_r);
        etnumber = findViewById(R.id.check_num);
        btnasave = findViewById(R.id.check_save);
        txtcode=findViewById(R.id.txt_code);
        fb=FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ashr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ADMIN.class);
                startActivity(intent);
            }
        });
        btnasave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num=etnumber.getText().toString();
                code=txtcode.getText().toString();
                String number=code+num;
                fb.collection("Admin").document(number).collection("Time")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Intent intent= new Intent(getApplicationContext(),Detail.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "NOt Found", Toast.LENGTH_SHORT).show();
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
        });
    }
}
