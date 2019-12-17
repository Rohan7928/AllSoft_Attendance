package com.example.myapplication;

/**
 * Created by ADJ on 8/8/2017.
 */


import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import static com.example.myapplication.Configuration.LIST_USER_URL;
public class UserList extends AppCompatActivity {





    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);




        listView = (ListView) findViewById(R.id.list_view);
        sendRequest();
    }

    private void sendRequest(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(LIST_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Log.e("null","ser image"+response);
                        showJSON(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserList.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
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
        //Log.e("uImage","ser image"+JsonParser.uImages[1]);
        UserListAdapter userListAdapter = new UserListAdapter(this, JsonParser.uIds,JsonParser.uNames,JsonParser.uLocation,JsonParser.uMobile,JsonParser.uTime,JsonParser.uType,JsonParser.uAddress,JsonParser.uImages);
        listView.setAdapter(userListAdapter);
    }



    }
