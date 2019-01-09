package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompletedPilgrimagesActivity extends AppCompatActivity {

    int id;
    int startTime;
    int time;

    public int index = 1;
    RequestQueue requestQueue;
    JSONObject json_data;
    JsonArrayRequest arrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_pilgrimages);


        String pilgrimageURL = "http://pilgrimapp.azurewebsites.net/api/pilgrimages";
        requestQueue = Volley.newRequestQueue(this);

        arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                pilgrimageURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        try {
                            Log.d("index", "index: " + index);

                            ArrayList<String> items = new ArrayList<String>();

                            for(int i=0; i <response.length(); i++){
                                    json_data = response.getJSONObject(i);
                                    int id = json_data.getInt("id");
                                    int startTime = json_data.getInt("startTime");
                                    int time = json_data.getInt("time");
                                    items.add(Integer.toString(id));
                                    Log.d(Integer.toString(id),"Output");
                            }


                            /*
                            id = pilgrimage.getInt("id");
                            startTime = pilgrimage.getInt("startTime");
                            time = pilgrimage.getInt("time");

                            /*
                            TextView txtID = (TextView) findViewById(R.id.txtID);
                            TextView txtStart = (TextView) findViewById(R.id.txtStartTime);
                            TextView txtTime = (TextView) findViewById(R.id.txtTime);

                            txtID.setText(Integer.toString(id));
                            txtStart.setText(Integer.toString(startTime));
                            txtTime.setText(Integer.toString(time ));
                            */

                            Log.d("onresponse", "Response: " + id);
                            Log.d("starttime", "starttime: " + startTime);
                            Log.d("timespent", "timespent: " + time);


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
        requestQueue.add(arrayRequest);


        //Convert unixtime naar leesbare tijd
        Date startDate = new Date(startTime*1000L);
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        String formattedDate = dateFormat.format(startDate);


        /*
        txtID.setText(Integer.toString(id));
        txtStart.setText(Integer.toString(formattedDate));
        txtTime.setText(Integer.toString(time ));*/

    }
}
