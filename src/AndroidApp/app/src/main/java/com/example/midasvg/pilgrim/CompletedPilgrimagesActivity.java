package com.example.midasvg.pilgrim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompletedPilgrimagesActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    JsonArrayRequest arrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_pilgrimages);


        //Pilgrimages ophalen vd server
        String pilgrimageURL = "http://10.0.2.2:52521/api/pilgrimages";

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
                            JSONObject pilgrimage = response.getJSONObject(1);
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
