package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditProfileActivity extends AppCompatActivity {


    private TextView nickName;
    private TextView firstName;
    private TextView lastName;
    private TextView date;
    private Button saveBtn;



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
        nickName = (TextView)findViewById(R.id.txb_NickName);
        firstName = (TextView) findViewById(R.id.txb_FirstName);
        lastName = (TextView) findViewById(R.id.txb_LastName);
        date = (TextView) findViewById(R.id.txb_DoB);
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
            base64Image = "";
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
                    URL url = new URL("https://cloud.requestcatcher.com");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("FirstName", firstName.getText().toString());
                    jsonObject.put("LastName", lastName.getText().toString());
                    jsonObject.put("NickName", nickName.getText().toString());
                    jsonObject.put("Age", 18);
                    jsonObject.put("Country", "Belgium");
                    jsonObject.put("Base64", base64Image);
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


}
