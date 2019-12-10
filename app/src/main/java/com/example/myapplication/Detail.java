package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.myapplication.Post.PostData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class Detail extends AppCompatActivity implements LocationListener {
    CircularImageView addphoto;
    ScrollView scrollView;
    RadioGroup radioGroup;
    TextView locationText, currentimev, did, useraddress,username;
    FirebaseAuth auth;
    FirebaseFirestore fb;
    FloatingActionButton getLocationBtn;
    private String log, add ;
    private String id, type,url;
    private String currentDateandTime, currentDate ;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    private static final int GALLERY_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int location_REQUEST = 3;
    private StorageReference storageReference;
    private Uri capImageURI;
    String user,mobilno, generatedPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        radioGroup = findViewById(R.id.current);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        did = findViewById(R.id.d_id);
        username=findViewById(R.id.user_name);
        currentimev = findViewById(R.id.current_time_view);
        getLocationBtn = findViewById(R.id.getLocationBtn);
        addphoto = findViewById(R.id.add_photo);
        locationText = (TextView) findViewById(R.id.locationText);
        useraddress = findViewById(R.id.user_address);
        auth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        scrollView=findViewById(R.id.scroll_view);
        storageReference = FirebaseStorage.getInstance().getReference().child("Database").child("Users");
        Intent intent=getIntent();
       user= intent.getStringExtra("User_Name");
        mobilno=intent.getStringExtra("Autho_rized");
        generatedPassword=intent.getStringExtra("Random");
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        username.setText(user);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                getLocation(log, add);
                progressDialog.show();
            }
        });
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getid();
                //savedata();
            }
        });
       addphoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               iphoto();
           }
       });
    }
    private void iphoto() {
        final CharSequence[] items = {"Take a new photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Take a new photo")) {
                    //request permission start camera intent
                    requestCameraPermission();
                } else if (items[i].equals("Choose from gallery")) {
                    //request gallery permission and start gallery intent
                    requestGalleryPermission();
                } else if (items[i].equals("Cancel")) {
                    //dismiss the alert dialog
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void requestGalleryPermission() {
        int result = ContextCompat
                .checkSelfPermission(Detail.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat
                    .requestPermissions(Detail.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            GALLERY_REQUEST);
        }
    }
    //Camera
    private void requestCameraPermission() {
        int result = ContextCompat.checkSelfPermission(Detail.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(Detail.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST);
        }
    }
    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, GALLERY_REQUEST);
    }
    private void startCamera() {
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent takepicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takepicture.resolveActivity(getPackageManager()) != null)
        {
            String filename="temp.jpg";
            ContentValues values=new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,filename);
            capImageURI=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            takepicture.putExtra(MediaStore.EXTRA_OUTPUT,capImageURI);
            startActivityForResult(takepicture,CAMERA_REQUEST);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),Admin_Option.class);
        startActivity(intent);
        Animatoo.animateFade(Detail.this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_REQUEST: {
                if (resultCode == RESULT_OK && data.getData() != null) {
                    capImageURI = data.getData();
                    setProfileImage(data.getData());
                } else {
                    Toast.makeText(this, "No Image Selected! Try AgainNo Image Selected! Try Again", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK ) {
                    String[] projection={MediaStore.Images.Media.DATA};
                    Cursor cursor=managedQuery(capImageURI,projection,null,null,null);
                    int column_index_data=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturepath=cursor.getString(column_index_data);
                    capImageURI= Uri.parse("file://"+ picturepath);
                    setProfileImage(capImageURI);
                } else {
                    Toast.makeText(this, "No Image Captured! Try Again", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void setProfileImage(Uri data) {
        addphoto.setImageURI(data);
        scrollView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText());
            log = "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude();
            add = addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2);
            getLocation(log, add);
        } catch (Exception e) {

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, location_REQUEST);

        }
        Toast.makeText(getApplicationContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }


    @SuppressLint("RestrictedApi")
    private void getLocation(String log, String add) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getApplicationContext(),
                    "No answer has been selected",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            RadioButton radioButton
                    = (RadioButton) radioGroup
                    .findViewById(selectedId);
            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            type = radioButton.getText().toString();
            id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            did.setText(id);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm a");
            currentDate = date.format(new Date());
            currentDateandTime = time.format(new Date());
            currentimev.setText(currentDateandTime);
            useraddress.setText(add);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), capImageURI);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);

            final byte[] fileInBytes = outputStream.toByteArray();

            final StorageReference file=storageReference.child(id).child("Profile")
                    .child(capImageURI.getLastPathSegment());

            file.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            url=String.valueOf(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Detail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Detail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void getid() {
       progressDialog.show();

        final String mobdate=mobilno+"_"+currentDate;
        Student stu = new Student(id, currentDateandTime, log, add, type, url,user,currentDate,mobilno);
       fb.collection("Employee").document(mobilno).collection("ID")
               .document(generatedPassword).set(stu)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            sheet();
                            Intent intent=new Intent(getApplicationContext(),Admin_Option.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Detail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void sheet() {

        new Detail.SendRequest().execute();
    }

   /* private void savedata() {
        final String mobdate=mobilno+"_"+currentDate;
        Student stu = new Student(id, currentDateandTime, log, add, type, url,user,currentDate,mobilno);
        fb.collection("Admin").document(String.valueOf(currentDate)).collection("Mobile")
                .document(mobilno).set(stu)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Authorized",mobilno);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Detail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(this, "Permission denied to open Camera", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case GALLERY_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Permission denied to open Gallery", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case location_REQUEST: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Location  permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Location  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


  public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //Change your web app deployed URL or u can use this for attributes (name, country)
                URL url = new URL("https://script.google.com/macros/s/AKfycbw0V5VPjI8cBtlZDAYFjJX2xgbpSkBy-7iVd639CIaoPI7irUVB/exec");

                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                String id= "1rjk9fkzleTjWEMryfAkT5TzNY40zzU_I-Hm5jdZQcsk";

                postDataParams.put("Name",user);
                postDataParams.put("Country",log);
                postDataParams.put("id",id);
                postDataParams.put("Mobile",mobilno);
                postDataParams.put("Time",currentDateandTime);
                postDataParams.put("Type",type);
                postDataParams.put("Address",add);

                postDataParams.put("Photo",String.valueOf(url));


                Log.e("params",postDataParams.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}