package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setTitle("Collection");

        String locationUrl = "http://pilgrimapp.azurewebsites.net/api/locations";
        final List<Location> locationList = new ArrayList<Location>();

        requestQueue = Volley.newRequestQueue(this);
        JsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                locationUrl,
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {

                            JSONObject locationsList = response;
                            JSONArray locations = locationsList.getJSONArray("pilgrimages");
                            Log.d("LocationLength", "onResponse: " + locations.length());
                            for (int i = 0; i < locations.length(); i++) {
                                final JSONObject temp = locations.getJSONObject(i);
                                Location tempLocation = new Location(){{
                                    id = temp.getInt("id");
                                    naam = temp.getString("naam");
                                    img = temp.getString("base64");
                                }};
                                locationList.add(tempLocation);

                            }
                            Location[] locationsarray = new Location[locationList.size()];

                            locationsarray = locationList.toArray(locationsarray);
                            ListAdapter Adapter = new CollectionAdapter(getBaseContext(),locationsarray);
                            ListView locList = (ListView) findViewById(R.id.pilgrimList);
                            locList.setAdapter(Adapter);

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
