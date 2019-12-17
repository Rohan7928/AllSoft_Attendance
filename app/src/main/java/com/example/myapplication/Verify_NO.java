package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    String time,number;
    Number_Name number_name;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__no);
        editTextMobile = findViewById(R.id.editTextMobile);
        fb = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        number_name = new Number_Name(this);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
        time =mdformat.format(calendar.getTime());
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("8629870458");
        arrayList.add("7018793629");
        arrayList.add("7837709702");
        arrayList.add("9851700100");
        arrayList.add("9682588655");
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
                                            } else {
                                                progressDialog.dismiss();
                                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Verify_NO.this);
                                                View mView = getLayoutInflater().inflate(R.layout.dailog_detail, null);
                                                final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
                                                final EditText mName = (EditText) mView.findViewById(R.id.etName);
                                                final TextView mPhone = (TextView) mView.findViewById(R.id.etmobile);
                                                final TextView mCode = (TextView) mView.findViewById(R.id.t_code);
                                                final EditText mDesignation = (EditText) mView.findViewById(R.id.etdesignation);
                                                mPhone.setText(mobile);
                                                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);
                                                mBuilder.setView(mView);
                                                dialog = mBuilder.create();
                                                dialog.show();
                                                mLogin.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if(!mEmail.getText().toString().isEmpty() && !mName.getText().toString().isEmpty() && !mDesignation.getText().toString().isEmpty()){
                                                            progressDialog.show();
                                                            String email=mEmail.getText().toString();
                                                            String name=mName.getText().toString();
                                                            String code=mCode.getText().toString();
                                                            String designation=mDesignation.getText().toString();
                                                            number=code+mobile;
                                                            User admin=new User(number,name,time,email,designation);
                                                            fb.collection("Employee").document(number).set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    dialog.dismiss();
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(Verify_NO.this, "Registered", Toast.LENGTH_SHORT).show();
                                                                    Intent intent=new Intent(getApplicationContext(),OTP_Verify.class);
                                                                    intent.putExtra("UserMobile",mobile);
                                                                    startActivity(intent);
                                                                    Animatoo.animateSlideUp(Verify_NO.this);
                                                                    finish();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(Verify_NO.this, "Sorry", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }else{
                                                            progressDialog.dismiss();
                                                            Toast.makeText(Verify_NO.this,
                                                                    "Fill your Details",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

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