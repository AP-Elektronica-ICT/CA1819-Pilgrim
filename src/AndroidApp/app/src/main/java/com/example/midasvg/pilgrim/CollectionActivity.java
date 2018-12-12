package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CollectionActivity extends AppCompatActivity {

    public int index = 1;
    RequestQueue requestQueue;
    JsonArrayRequest arrayRequest;

    int id;
    String name;
    String desc;

    TextView txtID;
    TextView txtNaam;
    TextView txtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        getSupportActionBar().setTitle("Collection");

        txtID = (TextView) findViewById(R.id.txtID);
        txtNaam = (TextView) findViewById(R.id.txtNaam);
        txtDescription = (TextView) findViewById(R.id.txtDesc);

        final String locationUrl = "http://pilgrim.azurewebsites.net/api/locations";
        requestQueue = Volley.newRequestQueue(this);

        arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                locationUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            JSONObject location = response.getJSONObject(index);

                            id = location.getInt("id");
                            name = location.getString("naam");
                            desc = location.getString("description");

                            txtID.setText(Integer.toString(id));
                            txtNaam.setText(name);
                            txtDescription.setText(desc);


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


    }
}
