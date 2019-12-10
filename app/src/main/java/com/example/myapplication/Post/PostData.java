package com.example.myapplication.Post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class PostData extends AppCompatActivity {


    private ProgressDialog progress;


    EditText tvName;
    EditText tvCountry, tvtime;
    Button button;
    String name, name1;
    String location, location1;
    String time, time1, type, mobileno, address, url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        button = (Button) findViewById(R.id.btn_signup);
        tvName = (EditText) findViewById(R.id.input_name);
        tvCountry = (EditText) findViewById(R.id.input_location);
        tvtime = (EditText) findViewById(R.id.input_time);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        location = intent.getStringExtra("Location");
        time = intent.getStringExtra("Time");
        type = intent.getStringExtra("Type");
        mobileno = intent.getStringExtra("Mobile");
        address = intent.getStringExtra("Address");
        url = intent.getStringExtra("Url");

        tvName.setText(name);
        tvCountry.setText(location);
        tvtime.setText(time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name1 = tvName.getText().toString();
                location1 = tvCountry.getText().toString();
                time1 = tvtime.getText().toString();
                //   new PostData.SendRequest().execute();
            }

        });

    }








  /*  public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //Change your web app deployed URL or u can use this for attributes (name, country)
                URL url = new URL("https://script.google.com/macros/s/AKfycbw0V5VPjI8cBtlZDAYFjJX2xgbpSkBy-7iVd639CIaoPI7irUVB/exec");

                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                String id= "1rjk9fkzleTjWEMryfAkT5TzNY40zzU_I-Hm5jdZQcsk";

                postDataParams.put("Name",name1);
                postDataParams.put("Country",location1);
                postDataParams.put("Mobile",mobileno);
                postDataParams.put("Time",time);
                postDataParams.put("Type",type);
                postDataParams.put("Address",address);

                postDataParams.put("Photo",url);
                postDataParams.put("id",id);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 *//* milliseconds *//*);
                conn.setConnectTimeout(15000 *//* milliseconds *//*);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
*/
}