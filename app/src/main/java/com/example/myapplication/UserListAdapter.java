package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_row, null, true);
        TextView urid = listViewItem.findViewById(R.id.ur_id);
        TextView urname = listViewItem.findViewById(R.id.ur_name);
        TextView urtype = listViewItem.findViewById(R.id.ur_type);
        TextView urtime = listViewItem.findViewById(R.id.ur_timeview);
        TextView urlocation = listViewItem.findViewById(R.id.ur_Text);
        TextView uraddress =  listViewItem.findViewById(R.id.ur_address);
        TextView urmobile = listViewItem.findViewById(R.id.ur_Mobile);
        ImageView iv = listViewItem.findViewById(R.id.sh_photo);
        CardView cardView=listViewItem.findViewById(R.id.card_card);
        urid.setText(uId[position]);
        urname.setText(uNames[position]);
        urlocation.setText(uLocation[position]);
        urmobile.setText(uMobile[position]);
        uraddress.setText(uAddress[position]);
        urtime.setText(uTime[position]);
        urtype.setText(uType[position]);
        Picasso.get().load(uImages[position]).into(iv);
         cardView.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View view) {
                 Intent intent=new Intent(context,Information.class);
                 intent.putExtra("location",uLocation[position]);
                 intent.putExtra("time",uTime[position]);
                 intent.putExtra("Address",uAddress[position]);
                 intent.putExtra("Id",uId[position]);
                 intent.putExtra("Type",uType[position]);
                 intent.putExtra("Name",uNames[position]);
                 intent.putExtra("Photo",uImages[position]);
                 context.startActivity(intent);
                 return false;
             }
         });

        return listViewItem;
    }
}