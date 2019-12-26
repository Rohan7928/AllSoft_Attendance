package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class UsersAdepter extends RecyclerView.Adapter<UsersAdepter.Myholder> {
    ArrayList<Json> arrayList=new ArrayList<>();

    private Activity context;
    private ArrayList<String> uId;
    private ArrayList<String> uNames;
    private ArrayList<String> uLocation;
    private ArrayList<String> uMobile;
    private ArrayList<String> uTime;
    private ArrayList<String> uType;
    private ArrayList<String> uAddress;
    private ArrayList<String> uImages;
   String date;
   String a;
 int count=0;
    public UsersAdepter(datewise datewise, ArrayList<String> uIds, ArrayList<String> uNames, ArrayList<String> uLocation, ArrayList<String> uMobile, ArrayList<String> uTime, ArrayList<String> uType, ArrayList<String> uAddress,ArrayList<String> uImages, String msg) {
        this.context=datewise;
        this.uId = uIds;
        this.uNames = uNames;
        this.uLocation=uLocation;
        this.uMobile=uMobile;
        this.uTime=uTime;
        this.uType=uType;
        this.uAddress=uAddress;
        this.uImages = uImages;
        this.date=msg;
    }

    /*public UsersAdepter(datewise datewise, String msg) {
        this.context=datewise;
        this.date=msg;
    }*/

    @NonNull
    @Override
    public UsersAdepter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.user_row, parent, false);
        return new Myholder(listViewItem);
    }
    @Override
    public void onBindViewHolder(@NonNull UsersAdepter.Myholder holder, int position) {
        //Json json=arrayList.get(position);
        int siz=uId.size();

        for(int i=0;i<siz;i++)
        {
            Toast.makeText(context, "Size"+siz, Toast.LENGTH_SHORT).show();
            if(uId.contains(date)) {
              /*  holder.urid.setText(String.valueOf(uId[position]));
                holder.urname.setText(String.valueOf(uNames[position]));
                holder.urtype.setText(String.valueOf(uType[position]));
                holder.urtime.setText(String.valueOf(uTime[position]));Toast.makeText(context, "count"+count, Toast.LENGTH_SHORT).show();
*/             count++;
                Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
            }        else {

                Toast.makeText(context, "No Date Found", Toast.LENGTH_SHORT).show();
            }

        }

        }

    @Override
    public int getItemCount() {
        Toast.makeText(context, "count"+a, Toast.LENGTH_SHORT).show();
        return uId.size();
    }
   /*public void addData(Json json1) {
       this.arrayList.add(json1);
       notifyDataSetChanged();
    }*/
    public class Myholder extends RecyclerView.ViewHolder {
        TextView urid,urname, urtype,urtime;
        public Myholder(View listViewItem) {
            super(listViewItem);
              urid = (TextView) listViewItem.findViewById(R.id.Date);
             urname = (TextView) listViewItem.findViewById(R.id._Name);
            urtype = (TextView) listViewItem.findViewById(R.id._Type);
             urtime = (TextView) listViewItem.findViewById(R.id._Time);
         }
    }
}
