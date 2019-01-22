package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class EndActivity extends AppCompatActivity {

    TextView gameTime;
    TextView txtDistance;

    //TextView txtPoints;
    float distance;
    int count;
    int hints;
    int penalty;
    int totalsecs;
    long startTime;
    public int msg;
    String UID;
    Location[] locations = new Location[0];
    Location loc1 = new Location() {

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

        //Tijd aanroepen van de vorige intent
        String timeSpent = getIntent().getStringExtra("Time");

        distance = getIntent().getFloatExtra("distance", 0);
        count = getIntent().getIntExtra("timerCount", 0);
        hints = getIntent().getIntExtra("totalHintCount", 0);
        startTime = getIntent().getLongExtra("StartTime", 0);

        penalty = 30 * hints;

        gameTime = (TextView) findViewById(R.id.txtSpent);
        totalsecs = count + penalty;
        int seconds = totalsecs%60;
        int temp = totalsecs - (totalsecs%60);
        int minutestotal = temp/60;
        int minutes = minutestotal%60;
        int temp2 = minutestotal - (minutestotal%60);
        int hours = temp2/60;

        String timeString;
        if(hours == 0 && minutes ==0){
            timeString = String.valueOf(seconds) + " seconds";
        }else if(hours == 0){
            timeString = String.valueOf(minutes) + " minutes " + String.valueOf(seconds) + "seconds";
        }else{
            timeString = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        }


        gameTime.setText(timeString);


        txtDistance = (TextView) findViewById(R.id.txtDistance);
        txtDistance.setText("" + new DecimalFormat("##.##").format(distance / 1000) + " Km");

        /*
        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtPoints.setText("" + count);

        int s = (count + penalty) % 60;
        int min = (count + penalty) / 60;
        int hour = min % 60;
        min = min / 60;
        txtPoints.setText(min + "h" + hour + "m" + s + "s");*/

        final  Button Leaderboard = (Button) findViewById(R.id.bttnLeaderboard) ;
        Leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        final Button backToMain = (Button) findViewById(R.id.bttnMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //API aanspreken
        sendPost();

    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://pilgrimapp.azurewebsites.net/api/pilgrimages");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("FireBaseID", UID);
                    jsonObject.put("username", "temp");
                    jsonObject.put("StartTime", startTime);
                    jsonObject.put("Time", totalsecs);
                    JSONArray jarr = new JSONArray();
                    jsonObject.put("Locations", jarr);
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonObject.toString());
                    Log.d("Post", jsonObject.toString());

                    os.flush();
                    os.close();
                    msg = conn.getResponseCode();
                    conn.disconnect();
                    Log.d("Post", String.valueOf(msg));
                    if (msg == 200) {
                        Toast.makeText(getBaseContext(), "Succes. ",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Something failed. Try again",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
