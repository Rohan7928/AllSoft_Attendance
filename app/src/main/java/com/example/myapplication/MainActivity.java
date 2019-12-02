package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {

FirebaseFirestore fb;
    RecyclerView recyclerView;
    ViewStatusAdapter viewStatusAdapter;
    TextView id;
    ProgressDialog progressDialog;
    String iid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStatusAdapter=new   ViewStatusAdapter(this);
        recyclerView = findViewById(R.id.lit_view);
        id=findViewById(R.id.deviceid);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Uploading Attendance...");
        progressDialog.setCancelable(false);

        progressDialog.setCanceledOnTouchOutside(false);
        fb=FirebaseFirestore.getInstance();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(viewStatusAdapter);

                 iid= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                id.setText(iid);
                try {
                    getsavedata();
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

       /*
        fb.collection("Device").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot documentSnapshot=task.getResult();
                        List<Student> userDataR=documentSnapshot.toObjects(Student.class);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/
    }

    private void getsavedata() {

        if (iid.isEmpty()) {
            Toast.makeText(this, "Enter Device First", Toast.LENGTH_SHORT).show();

        }
        else
        {
            fb.collection("Device").document(iid).collection("Time")
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
                                Toast.makeText(MainActivity.this, "wait", Toast.LENGTH_SHORT).show();
                                //Log.e("subject ", subjects.sub_name);
                            }

                    }
                }
            });
        }
    }
}
