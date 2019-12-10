package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class View_Attendance extends AppCompatActivity implements View.OnClickListener {
ListView listView;
    FirebaseFirestore fb;
    ArrayList<Student> attendencesList;
    ArrayList<Student> students;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__attend);
       // student = new Gson().fromJson(getIntent().getStringExtra("list"), User.class);
        //tuid = getIntent().getStringExtra("tuid");
        //subNodeId = getIntent().getStringExtra("id");
        fb= FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        mobile=intent.getStringExtra("Mobile");
        getData();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    }
    private void getData() {
        fb.collection("Employee").document(mobile).collection("ID")
                .orderBy("num", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    attendencesList = new ArrayList<>();
                   students=new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        Student student = snapshot.toObject(Student.class);
                        attendencesList.add(student);
                                           }


                   addHeaders(attendencesList);
                    addData(attendencesList);
                    }else {
                        Toast.makeText(View_Attendance.this, "wait", Toast.LENGTH_SHORT).show();
                        //Log.e("subject ", subjects.sub_name);
                    }

                }

        });





    }
    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 0, 0, 1);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     *
     //@param attendencesList
     * @param attendencesList
     */
    public void addHeaders(ArrayList<Student> attendencesList) {
        TableLayout tl = findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "Name", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        tr.addView(getTextView(0, "Date", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        tr.addView(getTextView(0, "Location", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        tr.addView(getTextView(0, "IN & OUT", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimaryDark)));


        tl.addView(tr, getTblLayoutParams());
    }

    public void addData(ArrayList<Student> students) {
        int attsize = students.size();
        TableLayout tl = findViewById(R.id.table);
        try {
            for (int i = 0; i < students.size(); i++) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());
                tr.addView(getTextView(i, students.get(i).name, Color.BLACK, Typeface.NORMAL, Color.WHITE));
                tr.addView(getTextView(i, String.valueOf(students.get(i).date), Color.BLACK, Typeface.NORMAL, Color.WHITE));
                tr.addView(getTextView(i, String.valueOf(students.get(i).address), Color.BLACK, Typeface.NORMAL, Color.WHITE));
                String Type = String.valueOf(students.get(i).type);
                String time = String.valueOf(students.get(i).num);
                String INOUT = Type + "\n" + time;
                if (Type.equals("IN")) {

                    tr.addView(getTextView(i, INOUT, Color.BLACK, Typeface.NORMAL, Color.WHITE));
                } else if(Type.equals("OUT")) {

                    tr.addView(getTextView(i, INOUT, Color.BLACK, Typeface.NORMAL, Color.WHITE));

                }
               //tr.addView(getTextView(i, String.valueOf(students.get(i).type), Color.BLACK, Typeface.NORMAL, Color.WHITE));
                tl.addView(tr, getTblLayoutParams());

            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "No Attendance found ", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row :: " + id);
            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }

}
