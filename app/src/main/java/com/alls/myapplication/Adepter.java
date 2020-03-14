package com.alls.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alls.myapplication.R;

import java.util.ArrayList;

class Adepter extends RecyclerView.Adapter<Adepter.Myholder> {
    ArrayList<User> sub_list = new ArrayList<>();
    Context context;

    public Adepter(Employee_list employee_list) {

        this.context = employee_list;
    }

    @NonNull
    @Override
    public Adepter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.get_num, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adepter.Myholder holder, int position) {
        final User user = sub_list.get(position);
        holder.txtemail.setText(user.email);
        holder.txtnum.setText(String.valueOf(user.num));
        holder.txtname.setText(user.name);
        holder.showattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = String.valueOf(user.num);
                Intent intent = new Intent(context, View_Attendance.class);
                intent.putExtra("MobileNO", number);
                context.startActivity(intent);
            }
        });
        holder.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = String.valueOf(user.num);
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 101);
                }
                Toast.makeText(context, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return sub_list.size();
    }

    public void add(User user) {
        this.sub_list.add(user);
        notifyDataSetChanged();
    }
    public class Myholder extends RecyclerView.ViewHolder {
        TextView txtemail, txtnum, txtname;
        ImageView imgcall,showattendance;


        public Myholder(@NonNull View itemView) {
            super(itemView);
            txtemail = itemView.findViewById(R.id.txt_email);
            txtname = itemView.findViewById(R.id.txt_name);
            txtnum = itemView.findViewById(R.id.txt_num);
            imgcall = itemView.findViewById(R.id.img_call);
            showattendance = itemView.findViewById(R.id.show_attendance_);
        }
    }
}
