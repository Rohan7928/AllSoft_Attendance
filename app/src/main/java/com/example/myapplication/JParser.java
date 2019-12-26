package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.myapplication.Configuration.KEY_ADDRESS;
import static com.example.myapplication.Configuration.KEY_COUNTRY;
import static com.example.myapplication.Configuration.KEY_ID;
import static com.example.myapplication.Configuration.KEY_IMAGE;
import static com.example.myapplication.Configuration.KEY_MOBILE;
import static com.example.myapplication.Configuration.KEY_NAME;
import static com.example.myapplication.Configuration.KEY_TIME;
import static com.example.myapplication.Configuration.KEY_TYPE;
import static com.example.myapplication.Configuration.KEY_USERS;

public class JParser {
    public static ArrayList<String> uIds;
    public static ArrayList<String> uNames;
    public static ArrayList<String> uLocation;
    public static ArrayList<String> uMobile;
    public static ArrayList<String> uTime;
    public static ArrayList<String> uType;
    public static ArrayList<String> uAddress;
    public static ArrayList<String> uImages;

    private JSONArray users = null;
    private String json;

    public JParser(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(KEY_USERS);
            uIds=new ArrayList<>();
            uIds.add(String.valueOf(users.length()));
            uNames=new ArrayList<>();
            uNames.add(String.valueOf(users.length()));
            uLocation=new ArrayList<>();
            uLocation.add(String.valueOf(users.length()));
            uMobile=new ArrayList<>();
            uMobile.add(String.valueOf(users.length()));
            uTime=new ArrayList<>();
            uTime.add(String.valueOf(users.length()));
            uType=new ArrayList<>();
            uType.add(String.valueOf(users.length()));
            uAddress=new ArrayList<>();
            uAddress.add(String.valueOf(users.length()));
            uImages=new ArrayList<>();
            uImages.add(String.valueOf(users.length()));

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                uIds.set(i, jo.getString(KEY_ID));
                uNames.set(i, jo.getString(KEY_NAME));
                uLocation.set(i, jo.getString(KEY_COUNTRY));
                uMobile.set(i, jo.getString(KEY_MOBILE));
                uTime.set(i, jo.getString(KEY_TIME));
                uType.set(i, jo.getString(KEY_TYPE));
                uAddress.set(i, jo.getString(KEY_ADDRESS));
                uImages.set(i, jo.getString(KEY_IMAGE));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
