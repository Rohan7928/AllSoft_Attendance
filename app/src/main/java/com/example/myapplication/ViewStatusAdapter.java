package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class ViewStatusAdapter extends RecyclerView.Adapter<ViewStatusAdapter.Myholder>{
    ArrayList<Student> sub_list=new ArrayList<>();
    Context context;
    public ViewStatusAdapter(MainActivity mainActivity) {

        this.context=mainActivity;
    }

    @NonNull
    @Override
    public ViewStatusAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewstatuspage,parent,false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewStatusAdapter.Myholder myholder, final int position) {
        final Student teacherstatus=sub_list.get(position);
        myholder.txtloc.setText(teacherstatus.log);
        myholder.txttime.setText(String.valueOf(teacherstatus.num));
        myholder.txtadd.setText(teacherstatus.address);
        myholder.txtid.setText(teacherstatus.id);
        myholder.txttype.setText(teacherstatus.type);
        myholder.txtname.setText(teacherstatus.name);
        Picasso.get().load(teacherstatus.getProfileurl()).into(myholder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return sub_list.size();
    }

    public void addData(Student teacherstatus) {
        this.sub_list.add(teacherstatus);
        notifyDataSetChanged();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView txttime,txtloc,txtid,txtadd,txttype,txtname;
        ImageView circleImageView;
     CardView cardView,cardView1;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            txtadd=itemView.findViewById(R.id.txt_address);
            txttime=itemView.findViewById(R.id.txt_time);
            txtloc=itemView.findViewById(R.id.txt_loc);
            txtid=itemView.findViewById(R.id.txt_id);
            cardView=itemView.findViewById(R.id.card);
            txttype=itemView.findViewById(R.id.type);
            circleImageView=itemView.findViewById(R.id.show_photo);
            txtname=itemView.findViewById(R.id.name);
           }
    }
}
