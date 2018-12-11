package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class EndActivity extends AppCompatActivity {

    TextView gameTime;
    TextView txtDistance;
    TextView txtPoints;
    float distance;
    int count;
    int hints;
    int penalty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //Tijd aanroepen van de vorige intent
        String timeSpent = getIntent().getStringExtra("Time");

        distance = getIntent().getFloatExtra("distance", 0);
        count = getIntent().getIntExtra("timerCount",0);
        hints = getIntent().getIntExtra("totalHintCount", 0);

        penalty = 30 * hints;

        gameTime = (TextView) findViewById(R.id.txtSpent);
        gameTime.setText(timeSpent);

        txtDistance = (TextView)findViewById(R.id.txtDistance);
        txtDistance.setText(""+new DecimalFormat("##.##").format(distance/1000) + "Km");

        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtPoints.setText(""+count);

        int s = (count+penalty) % 60;
        int min = (count+penalty) / 60;
        int hour = min % 60;
        min = min / 60;
        txtPoints.setText(min + "h" + hour + "m" + s + "s");


        final Button backToMain = (Button) findViewById(R.id.bttnMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //API aanspreken
        String locationURL = "http://localhost:44384/locations";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                locationURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Nieuwe pilgrimage aanmaken voor persoon
                        //Score Pushen
                        //Gespendeerde tijd pushen
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
}
