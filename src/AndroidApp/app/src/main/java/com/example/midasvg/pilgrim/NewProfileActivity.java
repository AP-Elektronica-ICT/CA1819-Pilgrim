package com.example.midasvg.pilgrim;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class NewProfileActivity extends AppCompatActivity {

    private TextView nickName;
    private TextView firstName;
    private TextView lastName;
    private TextView date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button saveBtn;
    private ImageView profilePicture;
    public static final int PICK_IMAGE = 100;
    Uri imageURI;
    private String base64Image;
    String UID;
    String UIDToken;
    int yearOfBirth;
    int monthOfBirth;
    int dayOfBirth;
    public int msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_profile);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();
        nickName = (TextView)findViewById(R.id.txb_NickName);
        firstName = (TextView) findViewById(R.id.txb_FirstName);
        lastName = (TextView) findViewById(R.id.txb_LastName);
        date = (TextView) findViewById(R.id.txb_DoB);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewProfileActivity.this,
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
                dayOfBirth = day;
                monthOfBirth = month;
                yearOfBirth = year;
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

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

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

    }

    public void sendPost(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://pilgrimapp.azurewebsites.net/api/profiles");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    Calendar cal = Calendar.getInstance();
                    cal.set(yearOfBirth,monthOfBirth-1,dayOfBirth);
                    long epoch = cal.getTime().getTime()/1000;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("FirstName", firstName.getText().toString());
                    jsonObject.put("LastName", lastName.getText().toString());
                    jsonObject.put("NickName", nickName.getText().toString());
                    jsonObject.put("DateOfBirth", epoch);
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
                    msg = conn.getResponseCode();
                    conn.disconnect();
                    Log.i("PostTag", "MSG: " + msg);

                    if(msg  == 200){
                        Intent intent = new Intent(NewProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(NewProfileActivity.this, "Something failed. Try again",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
