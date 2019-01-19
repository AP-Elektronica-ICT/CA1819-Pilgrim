package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompletedPilgrimagesActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_pilgrimages);
        getSupportActionBar().setTitle("My Pilgrimages");

        String URL = "http://pilgrimapp.azurewebsites.net/api/pilgrimages";
        final List<Pilgrimage> pilgrimagesList = new ArrayList<Pilgrimage>();


        requestQueue = Volley.newRequestQueue(this);
        Log.d("Request", "onCreate: ");
        JsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {

                            JSONObject completedPilgrimages = response;
                            JSONArray pilgrimages = completedPilgrimages.getJSONArray("pilgrimages");
                            Log.d("PilgrimagesLength", "onResponse: " + pilgrimages.length());
                            for (int i = 0; i < pilgrimages.length(); i++) {
                                final JSONObject temp = pilgrimages.getJSONObject(i);
                                Pilgrimage pilgrimagetemp = new Pilgrimage(){{
                                    id = temp.getInt("id");
                                    //FireBaseID = temp.getString("fireBaseID");
                                    //username = temp.getString("username");
                                    startTime = temp.getInt("startTime");
                                    Time = temp.getInt("time");
                                }};
                                pilgrimagesList.add(pilgrimagetemp);

                            }
                            Pilgrimage[] pilgrimagesarray = new Pilgrimage[pilgrimagesList.size()];

                            pilgrimagesarray = pilgrimagesList.toArray(pilgrimagesarray);
                            ListAdapter Adapter = new PilgrimageAdapter(getBaseContext(),pilgrimagesarray);
                            ListView pilgrimageList = (ListView) findViewById(R.id.pilgrimList);
                            pilgrimageList.setAdapter(Adapter);

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


    }
}
