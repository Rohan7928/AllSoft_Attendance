package com.alls.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.StatementEvent;

import static com.alls.myapplication.Configuration.LIST_USER_URL;

public class datewise extends AppCompatActivity {
    RecyclerView listView;
    Calendar calendar;
    CalendarView calendarView;
    String msg;
    FirebaseFirestore fb;
    Button showhide;
    UsersAdepter userListAdapter;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datewise);
        listView=findViewById(R.id._listview);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        fb=FirebaseFirestore.getInstance();
        showhide=findViewById(R.id.show_hide);
        calendarView = findViewById(R.id.calenderView);
        linearLayout=findViewById(R.id.cal);
        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        userListAdapter = new UsersAdepter(datewise.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        listView.setLayoutManager(gridLayoutManager);
        listView.setAdapter(userListAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2)
            {
                i1=i1+1;
                String month="0";
                if(i1<10){
                    month=month.concat(String.valueOf(i1));
                }else{
                    month=String.valueOf(i1);
                }

                String day="0";
                if(i2<10){
                    day=day.concat(String.valueOf(i2));
                }else{
                    day=String.valueOf(i2);
                }

                msg=day+"."+month+"."+i;
               // msg =i2+"."+(i1 + 1)+"."+i;
                showhide.setText(msg);
                linearLayout.setVisibility(View.GONE);
                userListAdapter.sub_list.clear();
              sendRequest();
            }
        });
    }
    private void sendRequest() {
        fb.collection("Employee").orderBy("name", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User teacherstatus = document.toObject(User.class);
                                    String numbers=teacherstatus.num;
                                    fb.collection("Employee").document(numbers)
                                            .collection("ID").orderBy("num",Query.Direction.ASCENDING)
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.getResult().size() != 0) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Student student = document.toObject(Student.class);
                                                    String date=String.valueOf(student.date);
                                                    if(msg.equals(date))
                                                    {
                                                        userListAdapter.add(student);
                                                        userListAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                            else{

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(datewise.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }else {
                                Toast.makeText(datewise.this, "wait", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
   /* private void sendRequest(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest= new StringRequest(LIST_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(datewise.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String json){
        JsonParser pj = new JsonParser(json);
        pj.parseJSON();
        UsersAdepter userListAdapter = new UsersAdepter(this, JsonParser.uIds,JsonParser.uNames,JsonParser.uLocation,JsonParser.uMobile,JsonParser.uTime,JsonParser.uType,JsonParser.uAddress,JsonParser.uImages,msg);
        listView.setAdapter(userListAdapter);
    }*/

}
