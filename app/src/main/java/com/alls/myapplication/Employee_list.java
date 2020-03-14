package com.alls.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alls.myapplication.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Employee_list extends AppCompatActivity {
RecyclerView recyclerView;
FirebaseFirestore fb;

Adepter adepter;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),Admin_Option.class);
        startActivity(intent);
        Animatoo.animateFade(Employee_list.this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        recyclerView=findViewById(R.id.recycle_view);

        fb=FirebaseFirestore.getInstance();
        adepter=new Adepter(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adepter);




       fb.collection("Employee").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() != 0) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User teacherstatus = document.toObject(User.class);
                            String number=teacherstatus.num;
                            Log.e("subject ", teacherstatus.num);

                            adepter.add(teacherstatus);
                            adepter.notifyDataSetChanged();
                        }
                    }else {
                        Toast.makeText(Employee_list.this, "wait", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Employee_list.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
