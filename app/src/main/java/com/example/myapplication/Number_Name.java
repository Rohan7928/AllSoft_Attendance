package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Number_Name extends BaseAdapter {
ArrayList<User> users;
Context context;

    public Number_Name(Verify_NO verify_no) {
        this.context=verify_no;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user1=users.get(position);

        return null;
    }

    public void add(User user) {
        this.users=users;

    }
}
