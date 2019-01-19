package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ViewProfile extends AppCompatActivity {


    private TextView nickName;
    private TextView firstName;
    private TextView lastName;
    private TextView date;
    private Button editButton;
    private ImageView profileView;
    public int DayOfBirth;
    public int MonthOfBirth;
    public int YearOfBirth;
    public int ID;

    Uri imageURI;
    private String base64Image;
    String UID;

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;
    private Profile userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();



        String URL = "http://pilgrimapp.azurewebsites.net/api/profiles/" + UID;

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
                            profileView.setImageBitmap(decodedByte);


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

        nickName   = (TextView)findViewById(R.id.NickNameView);
        firstName = (TextView) findViewById(R.id.FirstNameView);
        lastName = (TextView) findViewById(R.id.LastNameView);
        date = (TextView) findViewById(R.id.DoBView);
        profileView = (ImageView) findViewById(R.id.profile_pictureView);
        editButton = (Button) findViewById(R.id.btn_EditProfile);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
