package com.example.myapplication;

/**
 * Created by ADJ on 8/9/2017.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.myapplication.Configuration.KEY_ADDRESS;
import static com.example.myapplication.Configuration.KEY_COUNTRY;
import static com.example.myapplication.Configuration.KEY_ID;
import static com.example.myapplication.Configuration.KEY_IMAGE;
import static com.example.myapplication.Configuration.KEY_MOBILE;
import static com.example.myapplication.Configuration.KEY_NAME;
import static com.example.myapplication.Configuration.KEY_TIME;
import static com.example.myapplication.Configuration.KEY_TYPE;
import static com.example.myapplication.Configuration.KEY_USERS;


public class JsonParser {
    public static String[] uIds;
    public static String[] uNames;
    public static String[] uLocation;
    public static String[] uMobile;
    public static String[] uTime;
    public static String[] uType;
    public static String[] uAddress;
    public static String[] uImages;


    private JSONArray users = null;

    private String json;

    public JsonParser(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(KEY_USERS);

            uIds = new String[users.length()];
            uNames = new String[users.length()];
            uLocation = new String[users.length()];
            uMobile = new String[users.length()];
            uTime = new String[users.length()];
            uType = new String[users.length()];
            uAddress = new String[users.length()];
            uImages = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                uIds[i] = jo.getString(KEY_ID);
                uNames[i] = jo.getString(KEY_NAME);
                uLocation[i] = jo.getString(KEY_COUNTRY);
                uMobile[i] = jo.getString(KEY_MOBILE);
                uTime[i] = jo.getString(KEY_TIME);
                uType[i] = jo.getString(KEY_TYPE);
                uAddress[i] = jo.getString(KEY_ADDRESS);
                uImages[i] = jo.getString(KEY_IMAGE);
            }

           // Log.e("uImage","ser image"+uImages[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
