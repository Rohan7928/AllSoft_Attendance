package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;

import static com.example.myapplication.Configuration.LIST_USER_URL;

public class datewise extends AppCompatActivity {
    RecyclerView listView;
    Calendar calendar;
    CalendarView calendarView;
    String msg;
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
        calendarView = findViewById(R.id.calenderView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
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
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                sendRequest();
            }
        });

    }
    private void sendRequest(){
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
    }
}
