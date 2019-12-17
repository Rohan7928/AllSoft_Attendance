package com.example.myapplication;

/**
 * Created by ADJ on 8/8/2017.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class UserListAdapter extends ArrayAdapter<String> {
    private String[] uId;
    private String[] uNames;
    private String[] uLocation;
    private String[] uMobile;
    private String[] uTime;
    private String[] uType;
    private String[] uAddress;
    private String[] uImages;
    private Activity context;

    public UserListAdapter(Activity context, String[] uId, String[] uNames, String[] uLocation, String[] uMobile, String[] uTime, String[] uType, String[] uAddress, String[] uImages) {
        super(context, R.layout.list_row, uId);
        this.context = context;
        this.uId = uId;
        this.uNames = uNames;
        this.uLocation=uLocation;
        this.uMobile=uMobile;
        this.uTime=uTime;
        this.uType=uType;
        this.uAddress=uAddress;
        this.uImages = uImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_row, null, true);
        TextView urid = (TextView) listViewItem.findViewById(R.id.ur_id);
        TextView urname = (TextView) listViewItem.findViewById(R.id.ur_name);
        TextView urtype = (TextView) listViewItem.findViewById(R.id.ur_type);
        TextView urtime = (TextView) listViewItem.findViewById(R.id.ur_timeview);
        TextView urlocation = (TextView) listViewItem.findViewById(R.id.ur_Text);
        TextView uraddress = (TextView) listViewItem.findViewById(R.id.ur_address);
        TextView urmobile = (TextView) listViewItem.findViewById(R.id.ur_Mobile);
        ImageView iv = (ImageView)listViewItem.findViewById(R.id.sh_photo);


        urid.setText(uId[position]);
        urname.setText(uNames[position]);
        urlocation.setText(uLocation[position]);
        urmobile.setText(uMobile[position]);
        urtime.setText(uTime[position]);
        urtype.setText(uType[position]);
        uraddress.setText(uAddress[position]);
       // Uri uri = Uri.parse(uImages[position]);
        //Uri uri = Uri.parse("https://drive.google.com/uc?id=0B___GhMLUVtOY09SbDU5cDU2T1U");
       // draweeView.setImageURI(uri);

        Picasso.get().load(uImages[position]).into(iv);

        return listViewItem;
    }
}