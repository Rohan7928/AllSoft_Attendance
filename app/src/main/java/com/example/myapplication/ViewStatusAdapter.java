package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        myholder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(context,Detail.class);
                intent.putExtra("location",teacherstatus.log);
                intent.putExtra("time",teacherstatus.num);
                context.startActivity(intent);
                                return false;
            }
        });
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
        TextView txttime,txtloc,txttime1,txtloc1;
     CardView cardView,cardView1;
        public Myholder(@NonNull View itemView) {
            super(itemView);

            txttime=itemView.findViewById(R.id.txt_time);
            txtloc=itemView.findViewById(R.id.txt_loc);

            cardView=itemView.findViewById(R.id.card);
           }
    }
}
