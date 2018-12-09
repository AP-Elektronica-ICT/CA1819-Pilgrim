package com.example.midasvg.pilgrim;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {


    private TextView nickName;
    private TextView firstName;
    private TextView lastName;
    private TextView date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button saveBtn;
    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;
    public int DayOfBirth;
    public int MonthOfBirth;
    public int YearOfBirth;
    public int ID;



    private ImageView profilePicture;
    public static final int PICK_IMAGE = 100;
    Uri imageURI;
    private String base64Image;
    String UID;
    String UIDToken;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_editprofile);

        getSupportActionBar().setTitle("My Profile");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();



        String URL = "http://pilgrim.azurewebsites.net/api/profiles/" + UID;

        requestQueue = Volley.newRequestQueue(this);

        JsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());

                        try {

                            JSONObject Profile = response;
                            String FirstName = Profile.getString("firstName");
                            String LastName = Profile.getString("lastName");
                            String base64 = Profile.getString("base64");
                            String NickName = Profile.getString("nickName");
                            long dateOfBirth = Profile.getLong("dateOfBirth");
                            long dateCreated = Profile.getLong("dateCreated");
                            ID = Profile.getInt("id");
                            //Date date = new Date(dateOfBirth);
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(dateOfBirth*1000);
                            Log.d("Date Of Birth: ", "Date: " + date.toString());
                            MonthOfBirth = cal.get(Calendar.MONTH) + 1;
                            DayOfBirth = cal.get(Calendar.DAY_OF_MONTH);
                            YearOfBirth = cal.get(Calendar.YEAR);

                            String _date = String.format("%02d", DayOfBirth) + "/" + String.format("%02d", MonthOfBirth) + "/" + YearOfBirth;
                            Log.d("Date Of Birth: ", _date );
                            date.setText(_date);
                            nickName.setText(NickName);
                            firstName.setText(FirstName);
                            lastName.setText(LastName);
                            base64Image = base64;
                            Log.d("Date Of Birth: ", "long: " + base64Image);
                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profilePicture.setImageBitmap(decodedByte);


                            //Log.d("JSONGET", "FirstName: " + FirstName + " LastName: " + LastName + " Nickname: " + NickName + " Base64: " + base64);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST response", error.toString());
                    }
                }
        );
        requestQueue.add(JsonRequest);


        nickName   = (TextView)findViewById(R.id.txb_NickName);
        firstName = (TextView) findViewById(R.id.txb_FirstName);
        lastName = (TextView) findViewById(R.id.txb_LastName);
        date = (TextView) findViewById(R.id.txb_DoB);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calendar cal = Calendar.getInstance();
                int year = YearOfBirth;
                int month = MonthOfBirth - 1;
                int day = DayOfBirth;

                DatePickerDialog dialog = new DatePickerDialog(
                        EditProfileActivity.this,
                        android.R.style.Theme_Holo,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month ++ ;
                Log.d("Date", "onDateSet: " + day + "/" + month + "/" + year);
                String _date = day + "/" + month + "/" + year;
                date.setText(_date);

            }
        };
        profilePicture = (ImageView) findViewById(R.id.profile_picture);
        saveBtn = (Button) findViewById(R.id.btn_saveProfile);





        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try{Save();}
                catch (IOException ie){
                    ie.printStackTrace();
                }
            }
        });


/*
        final Button signOut = (Button) findViewById(R.id.logoutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(intent);

                Toast.makeText(EditProfileActivity.this, "You are now signed out.",
                        Toast.LENGTH_LONG).show();
            }
        });
*/

        //API aanspreken
        String locationURL = "http://localhost:44384/locations";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                locationURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //profile picture ophalen
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST response", error.toString());
                    }
                }

        );
        requestQueue.add(objectRequest);

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            profilePicture.setImageURI(imageURI);

            try{
            InputStream inputStream = getContentResolver().openInputStream(imageURI);
            byte[] imageArray = getBytes(inputStream);
            /*
            Bitmap decodeByte = BitmapFactory.decodeByteArray(backtoImage, 0 , backtoImage.length);
            profilePicture.setImageBitmap(decodeByte);*/
            }catch(IOException ie){

            }
        }
    }
    public byte[] getBytes(InputStream inputStream) throws IOException{
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1){
            byteBuffer.write(buffer,0,len);
        }
        return  byteBuffer.toByteArray();
    }

    public void Save() throws  IOException{

        String finalBase64 = "";

        if(imageURI == null)
        {
            if(base64Image == null){
                base64Image = "";
            }

            Log.d("Test", "Save: empty");
        }
        else{
            InputStream inputStream = getContentResolver().openInputStream(imageURI);
            byte[] imageArray = getBytes(inputStream);
            base64Image = Base64.encodeToString(imageArray, Base64.NO_WRAP);
            Log.d("Test", "Save:" + base64Image);
            finalBase64 = base64Image;
        }

        Log.d("profileCall", "Nickname: " + nickName.getText() + ", FirstName: " + firstName.getText() + ", LastName: " + lastName.getText() + ", DateOfBirth: " + date.getText() + ", UID: " + UID);
        sendPost();
        /*RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://cloud.requestcatcher.com/";
        StringRequest MyStringReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //test
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){protected Map<String, String> getParams(){
            Map<String,String> myData = new HashMap<String, String>();
            myData.put("FirstName", firstName.getText().toString());
            myData.put("LastName", lastName.getText().toString());
            myData.put("NickName", nickName.getText().toString());
            myData.put("Age", "18");
            myData.put("Country", "Belgium");
            myData.put("fireBaseID", UID);

            Log.d("myData", "getParams: " + myData);
            return myData;


        }
        };
        MyRequestQueue.add(MyStringReq);
        */
    }

    public void sendPost(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String _url = "http://pilgrim.azurewebsites.net/api/profiles/";
                    URL url = new URL(_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Calendar cal = Calendar.getInstance();
                    cal.set(YearOfBirth,MonthOfBirth-1,DayOfBirth);
                    long epoch = cal.getTime().getTime()/1000;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ID);
                    jsonObject.put("FirstName", firstName.getText().toString());
                    jsonObject.put("LastName", lastName.getText().toString());
                    jsonObject.put("NickName", nickName.getText().toString());
                    jsonObject.put("Base64", base64Image);
                    jsonObject.put("DateOfBirth", epoch);
                    jsonObject.put("fireBaseID", UID);
                    // Need to delete \ from string in backend !!!

                    Log.i("image", base64Image);
                    Log.i("json", jsonObject.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonObject.toString());

                    os.flush();
                    os.close();
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void GetProfile(){

    }

}
