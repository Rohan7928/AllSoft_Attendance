package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


class UserAdapter extends ArrayAdapter<String> {
    private String[] uId;
    private String[] uNames;
    private String[] uLocation;
    private String[] uMobile;
    private String[] uTime;
    private String[] uType;
    private String[] uAddress;
    private String[] uImages;
    private Activity context;
    public UserAdapter(Activity context, String[] uId, String[] uNames, String[] uLocation, String[] uMobile, String[] uTime, String[] uType, String[] uAddress, String[] uImages) {
        super(context, R.layout.user_row, uId);
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
        View listViewItem = inflater.inflate(R.layout.user_row, parent, false);
        TextView urid = (TextView) listViewItem.findViewById(R.id.Date);
       TextView urname = (TextView) listViewItem.findViewById(R.id._Name);
        TextView urtype = (TextView) listViewItem.findViewById(R.id._Type);
        TextView urtime = (TextView) listViewItem.findViewById(R.id._Time);
        /* TextView urlocation = (TextView) listViewItem.findViewById(R.id.ur_Text);
        TextView uraddress = (TextView) listViewItem.findViewById(R.id.ur_address);
        TextView urmobile = (TextView) listViewItem.findViewById(R.id.ur_Mobile);
        ImageView iv = (ImageView)listViewItem.findViewById(R.id.sh_photo);
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
*/
       String date="24.12.2019";
       String crdate=uId[position];
       if(crdate.equals(date))
       {
           urid.setText(uId[position]);
           urname.setText(uNames[position]);
           urtype.setText(uType[position]);
           urtime.setText(uTime[position]);
           Toast.makeText(context, uId[position], Toast.LENGTH_SHORT).show();

       }
       else
       {
           Toast.makeText(context, "No Date Found", Toast.LENGTH_SHORT).show();
       }
        return listViewItem;
    }
}