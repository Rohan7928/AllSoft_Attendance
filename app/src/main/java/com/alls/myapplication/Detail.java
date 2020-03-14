package com.alls.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.alls.myapplication.Configuration.ADD_USER_URL;
import static com.alls.myapplication.Configuration.KEY_ACTION;
import static com.alls.myapplication.Configuration.KEY_ADDRESS;
import static com.alls.myapplication.Configuration.KEY_COUNTRY;
import static com.alls.myapplication.Configuration.KEY_ID;
import static com.alls.myapplication.Configuration.KEY_IMAGE;
import static com.alls.myapplication.Configuration.KEY_MOBILE;
import static com.alls.myapplication.Configuration.KEY_NAME;
import static com.alls.myapplication.Configuration.KEY_TIME;
import static com.alls.myapplication.Configuration.KEY_TYPE;

public class Detail extends AppCompatActivity implements LocationListener {
    CircularImageView addphoto;
    ScrollView scrollView;
    RadioGroup radioGroup;
    RelativeLayout linearLayout;
    TextView locationText, currentimev,useraddress,username;
    FirebaseAuth auth;
    FirebaseFirestore fb;
    FloatingActionButton getLocationBtn,btnrefresh;
    private String log, add ;
    private String id, type,urll;
    private String currentDateandTime, currentDate;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    Uri picUri,outputFileUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    private static final int CAMERA_REQUEST = 2;
    private static final int location_REQUEST = 3;
    private StorageReference storageReference;
    String user,mobilno, generatedPassword;
    String userImage;
    String imagePath;
    File mphotoFile;
    Bitmap bitmap,rbitmap;
    private ArrayList<String> wanted;
    private static final int REQUEST_CAPTURE_IMAGE = 200;
    Uri photoURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressDialog = new ProgressDialog(this, R.style.CustomDialogTheme);
        radioGroup = findViewById(R.id.current);
        progressDialog.setTitle("Getting your location please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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
        linearLayout=findViewById(R.id.liner);
        storageReference = FirebaseStorage.getInstance().getReference().child("Database").child("Users");
        Intent intent=getIntent();
        user= intent.getStringExtra("User_Name");
        mobilno=intent.getStringExtra("Autho_rized");
        generatedPassword=intent.getStringExtra("Random");
        username.setText(user);
        radioGroup.clearCheck();
        btnrefresh=findViewById(R.id.refresh);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean connection=isNetworkAvailable();
        boolean gps=isGpsAvailable();
        if(connection && gps==true){
            Snackbar snackbar=Snackbar.make(linearLayout,"Welcome",Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#00B9F5"));
            snackbar.show();
           }
        else{
            Snackbar snackbar=Snackbar.make(linearLayout,"Check Your GPS or Internet Connetcion",Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#00B9F5"));
            snackbar.show();
            startActivity(new Intent(getApplicationContext(),Gps.class));

        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                getLocation(log, add);
                progressDialog.show();
            }
        });
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                getLocation(log,add);
            }
        });
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getid();

            }
        });
       addphoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openCamera();
           }
       });
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }
    void openCamera(){

        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                mphotoFile = photoFile;
                outputFileUri=photoURI;
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_IMAGE);

            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        imagePath = image.getAbsolutePath();
        return image;
    }
    private boolean isGpsAvailable() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            return true;
        }
        else {
            return false;
        }    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
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
        // switch (requestCode) {
           /* case GALLERY_REQUEST: {
                if (resultCode == RESULT_OK && data.getData() != null) {
                    capImageURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), capImageURI);
                        rbitmap = getResizedBitmap(bitmap,250);//Setting the Bitmap to ImageView
                        userImage= String.valueOf(getStringImage(rbitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    setProfileImage(data.getData());
                } else {
                    Toast.makeText(this, "No Image Selected! Try AgainNo Image Selected! Try Again", Toast.LENGTH_SHORT).show();
                }
                break;
            }*/
        if (requestCode == REQUEST_CAPTURE_IMAGE) {
            if (resultCode == RESULT_OK) {
//                Bitmap imageData = (Bitmap) data.getExtras().get("data");
                Bitmap selectedImage = BitmapFactory.decodeFile(String.valueOf(mphotoFile));
                rbitmap = getResizedBitmap(selectedImage, 250);
                userImage = String.valueOf(getStringImage(rbitmap));

                Glide.with(Detail.this)
                        .load(mphotoFile)
                        .into(addphoto);

                //addphoto.setImageBitmap(imageData);
                scrollView.setVisibility(View.VISIBLE);
               // rbitmap = getResizedBitmap(imageData, 250);
                //userImage = String.valueOf(getStringImage(rbitmap));
            }
        }
      /*  if (resultCode == Activity.RESULT_OK) {
            CircularImageView addphoto = findViewById(R.id.add_photo);
            if (requestCode == IMAGE_RESULT) {
                String filePath = getImageFilePath(data);
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    addphoto.setImageBitmap(selectedImage);
                    scrollView.setVisibility(View.VISIBLE);
                    rbitmap = getResizedBitmap(selectedImage, 250);
                    userImage = String.valueOf(getStringImage(rbitmap));
                }
            }
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }
private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted)
{
    this.wanted = wanted;
    ArrayList<String> result = new ArrayList<String>();
    for (String perm : wanted) {
        if (!hasPermission(perm)) {
            result.add(perm);
        }
    }
    return result;
}
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    private Object getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private Bitmap getResizedBitmap(Bitmap image, int i) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = i;
            height = (int) (width / bitmapRatio);
        } else {
            height = i;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private void setProfileImage(Uri data) {
        addphoto.setImageURI(data);
        scrollView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText());
            log = "  Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
            add = addresses.get(0).getAddressLine(0);
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
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
            SimpleDateFormat time = new SimpleDateFormat("HH:MM a");
            currentDate = date.format(new Date());
            currentDateandTime = mdformat.format(calendar.getTime());
            currentimev.setText(currentDateandTime) ;
            useraddress.setText(add);
           try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), outputFileUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
            final byte[] fileInBytes = outputStream.toByteArray();
            final StorageReference file=storageReference.child(id).child("Profile")
                    .child(outputFileUri.getLastPathSegment());
            file.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            urll= String.valueOf(uri);
                           String afterloc=locationText.getText().toString();
                           String afteradd=useraddress.getText().toString();
                           if(afterloc.isEmpty() || afteradd.isEmpty())
                           {
                               Snackbar snackbar=Snackbar.make(linearLayout,"Try to Fetch Location Again",Snackbar.LENGTH_SHORT);
                               View snackbarView = snackbar.getView();
                               snackbarView.setBackgroundColor(Color.parseColor("#00B9F5"));
                               snackbar.show();
                               btnrefresh.setVisibility(View.VISIBLE);
                           }
                           else
                           {
                               getLocationBtn.setVisibility(View.VISIBLE);
                           }

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
        Student stu = new Student(id, currentDateandTime, log, add, type, urll,user,currentDate,mobilno);
       fb.collection("Employee").document(mobilno).collection("ID")
               .document(generatedPassword).set(stu)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_SHORT).show();
                            addUser();
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
    private void addUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,ADD_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Detail.this,response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //uerror.setText(error.toString());
                        Toast.makeText(Detail.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ACTION,"insert");
                params.put(KEY_ID,currentDate);
                params.put(KEY_NAME,user);
                params.put(KEY_COUNTRY,log);
                params.put(KEY_MOBILE,mobilno);
                params.put(KEY_TIME,currentDateandTime);
                params.put(KEY_TYPE,type);
                params.put(KEY_ADDRESS,add);
                params.put(KEY_IMAGE,userImage);
                return params;
            }
        };
        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
            case CAMERA_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //startCamera();
                } else {
                    Toast.makeText(this, "Permission denied to open Camera", Toast.LENGTH_SHORT).show();
                }
                break;
            }
          /*  case GALLERY_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Permission denied to open Gallery", Toast.LENGTH_SHORT).show();
                }
                break;
            }*/
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
                URL url = new URL("https://script.google.com/macros/s/AKfycbwunkOVSia8gfGIFhKyQwPyikV4k0nm6F3SEQwoqjtXQ9JzZrSa/exec");
                JSONObject postDataParams = new JSONObject();
                //int i;
                //for(i=1;i<=70;i++)
                //    String usn = Integer.toString(i);
                String id= "1Ec_kMJFfpfU0yY0dN1mTc41fvPLUmMoD2eQBjqdiALs";
                postDataParams.put("Name",user);
                postDataParams.put("Country",log);
                postDataParams.put("id",id);
                postDataParams.put("Mobile",mobilno);
                postDataParams.put("Time",currentDateandTime);
                postDataParams.put("Type",type);
                postDataParams.put("Address",add);
                postDataParams.put("Photo",String.valueOf(urll));
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

