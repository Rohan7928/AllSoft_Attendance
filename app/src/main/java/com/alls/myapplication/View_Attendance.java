package com.alls.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


public class View_Attendance extends AppCompatActivity implements View.OnClickListener {
   ListView listView;
        FirebaseFirestore fb;
        ArrayList<Student> attendencesList;
        ArrayList<Student> students;
        String mobile;
        LinearLayout linearLayout;
      @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__attend);
       linearLayout=findViewById(R.id.snack_layout);
        fb= FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        mobile=intent.getStringExtra("MobileNO");

        getData();
      }
    private void getData() {
        fb.collection("Employee").document(mobile).collection("ID")
                .orderBy("date", Query.Direction.DESCENDING)
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
                    }
                else {
                    Snackbar.make(linearLayout,"No Data Found",Snackbar.LENGTH_LONG).show();
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
        tr.addView(getTextView(0, "Photo", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        tl.addView(tr, getTblLayoutParams());
    }
    public void addData(final ArrayList<Student> students) {
        int attsize = students.size();
        TableLayout tl = findViewById(R.id.table);
        try {
            for (int i = 0; i < students.size(); i++) {
                final TableRow tr = new TableRow(this);
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
                final int finalI = i;
                final int finalI1 = i;
                tr.addView(getTextView(finalI, String.valueOf(students.get(finalI1).getProfileurl()), Color.BLACK, Typeface.NORMAL, Color.WHITE));

                tr.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Snackbar snackbar=Snackbar.make(linearLayout,String.valueOf(students.get(finalI).getProfileurl()),Snackbar.LENGTH_LONG);
                       View snackbarView = snackbar.getView();
                       snackbarView.setBackgroundColor(Color.parseColor("#ff3333"));
                       snackbar.setAction("Click", new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               String uri= String.valueOf(Uri.parse(String.valueOf(students.get(finalI).getProfileurl())));
                               Intent intent=new Intent(Intent.ACTION_VIEW);
                               intent.setData(Uri.parse(uri));
                               startActivity(intent);
                           }
                       });
                       snackbar.show();
                   }
               });
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
        final int id = v.getId();
        final TextView tv = findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row :: " + id);
        }
    }
}
