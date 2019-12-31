package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {

FirebaseFirestore fb;
FirebaseAuth auth;
    RecyclerView recyclerView;
    ViewStatusAdapter viewStatusAdapter;
    TextView id;
    ProgressDialog progressDialog;
    String mobileno,iid,randomid;
  LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStatusAdapter=new   ViewStatusAdapter(this);
        recyclerView = findViewById(R.id.lit_view);
        id=findViewById(R.id.deviceid);
        linearLayout=findViewById(R.id.linear);
        progressDialog = new ProgressDialog(this,R.style.CustomDialogTheme);
        progressDialog.setTitle("Uploading Attendance...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        fb=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(viewStatusAdapter);

       Intent intent=getIntent();
         mobileno=intent.getStringExtra("Authorize_d");
         randomid=intent.getStringExtra("Rando_m");
                iid= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                id.setText(iid);
                try {
                    getsavedata();
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),Admin_Option.class);
        startActivity(intent);
        Animatoo.animateFade(MainActivity.this);
        finish();
    }

    private void getsavedata() {
        if (iid.isEmpty()) {
            Toast.makeText(this, "Enter Device First", Toast.LENGTH_SHORT).show();

        }
        else
        {
            fb.collection("Employee").document(mobileno).collection("ID")
                    .orderBy("date", Query.Direction.DESCENDING).orderBy("num", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().size() != 0) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Student teacherstatus = document.toObject(Student.class);
                                viewStatusAdapter.addData(teacherstatus);
                                viewStatusAdapter.notifyDataSetChanged();
                            }
                        }else {
                           Snackbar snackbar=Snackbar.make(linearLayout,"No Attendance Marked Yet",Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(Color.parseColor("#00B9F5"));
                           snackbar.show();
                            }
                    }
                }
            });
        }

    }
}
