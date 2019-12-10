package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.myapplication.R;
import com.example.myapplication.model.MyDataModel;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<MyDataModel> {

    List<MyDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapter(Context context, List<MyDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.textViewName.setText(item.getName());
        vh.textViewLocation.setText(item.getLocation());
        vh.textViewtime.setText(item.getTime());

        return vh.rootView;
    }



    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView textViewName;
        public final TextView textViewLocation;
        public final TextView textViewtime;

        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewCountry, TextView textViewTime) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewLocation = textViewCountry;
            this.textViewtime = textViewTime;

        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewLocation = (TextView) rootView.findViewById(R.id.textViewlocation);
            TextView textViewTime = (TextView) rootView.findViewById(R.id.textViewtime);
            return new ViewHolder(rootView, textViewName, textViewLocation,textViewTime);
        }
    }
}