package com.alls.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersAdepter extends RecyclerView.Adapter<UsersAdepter.Myholder> {
    ArrayList<Json> arrayList=new ArrayList<>();
    ArrayList<Student> sub_list = new ArrayList<>();
    Context context;
    String _date;

    public UsersAdepter(datewise datewise) {
        this.context=datewise;

            }




    @NonNull
    @Override
    public UsersAdepter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_row, parent, false);
        return new Myholder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull UsersAdepter.Myholder holder, int position) {
       final Student user = sub_list.get(position);
        holder.urtype.setText(user.num);
        holder.urname.setText(user.name);
        holder.urtime.setText(user.type);
        if(user.type.equals("IN"))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#00B9F5"));
        }
        else
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#012B72"));
        }
        }

    @Override
    public int getItemCount() {
        return sub_list.size();
    }

   public void add(Student student) {

        this.sub_list.add(student);

        notifyDataSetChanged();

    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView urname, urtype,urtime;
        CardView cardView;
        public Myholder(View listViewItem) {
            super(listViewItem);
             urname = (TextView) listViewItem.findViewById(R.id._Name);
            urtype = (TextView) listViewItem.findViewById(R.id._Type);
             urtime = (TextView) listViewItem.findViewById(R.id._Time);
             cardView = (CardView) listViewItem.findViewById(R.id.card_value);

         }
    }
}
