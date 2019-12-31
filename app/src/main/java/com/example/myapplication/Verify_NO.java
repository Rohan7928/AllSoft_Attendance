package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Verify_NO extends AppCompatActivity {
    private EditText editTextMobile;
    String countrycode, mobile, usermobile;
    FirebaseFirestore fb;
    ProgressDialog progressDialog;
    String user_name, user_number;
    FirebaseAuth auth;
    String time,number,type;
    Number_Name number_name;

    protected LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__no);
        editTextMobile = findViewById(R.id.editTextMobile);
        fb = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this, R.style.CustomDialogTheme);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        number_name = new Number_Name(this);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
        time =mdformat.format(calendar.getTime());
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("7018793629");
        arrayList.add("7837709702");
        arrayList.add("7814170405");

    findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile=editTextMobile.getText().toString();
                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                else if(arrayList.contains(mobile)) {
                    progressDialog.show();
                    mobile=editTextMobile.getText().toString();
                    countrycode = "+91";
                    usermobile = countrycode + mobile;
                    for (int i = 0; i <= arrayList.size(); i++) {
                       fb.collection("Employee")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() != 0) {
                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                            User user = documentSnapshot.toObject(User.class);
                                            if (usermobile.equals(user.getNum())) {
                                                user_number = user.getNum();
                                                user_name = user.getName();
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(getApplicationContext(), OTP_Verify.class);
                                                intent.putExtra("UserMobile", mobile);
                                                intent.putExtra("UserName", user_name);
                                                startActivity(intent);
                                                Animatoo.animateSlideUp(Verify_NO.this);
                                                finish();
                                            }
                                            else {
                                            }
                                        }

                                    }
                                }
                                            else {

                                            }
                                        }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    progressDialog.show();
                    countrycode = "+91";
                    usermobile = countrycode + mobile;
                    fb.collection("Employee")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() != 0) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        User user = documentSnapshot.toObject(User.class);
                                        if (usermobile.equals(user.getNum())) {
                                            user_number = user.getNum();
                                            user_name = user.getName();
                                             progressDialog.dismiss();
                                            Intent intent = new Intent(getApplicationContext(), OTP_Verify.class);
                                            intent.putExtra("UserMobile", mobile);
                                            startActivity(intent);
                                            Animatoo.animateSlideLeft(Verify_NO.this);
                                            finish();
                                        } else {
                                            progressDialog.dismiss();
                                        }
                                    }
                                } else {
                                    editTextMobile.setError("Not Valid");
                                    progressDialog.dismiss();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}