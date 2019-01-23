package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CollectableActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectable);

        getSupportActionBar().setTitle("Collectable");

        final TextView locationName = (TextView) findViewById(R.id.txtTitle);
        final TextView locBeschrijving = (TextView) findViewById(R.id.txtBeschrijving);
        final ImageView locImage = (ImageView) findViewById(R.id.locImage);

        final Button bttnBack = (Button) findViewById(R.id.bttnBack);
        bttnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(CollectableActivity.this, CollectionActivity.class);
                startActivity(back);
            }
        });

        int index = getIntent().getIntExtra("id2",0);

        String URL = "http://pilgrimapp.azurewebsites.net/api/locations/" + index;

        requestQueue = Volley.newRequestQueue(this);

        JsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try{
                            JSONObject Location = response;

                            String title = Location.getString("naam");
                            String beschrijving = Location.getString("description");
                            String img = Location.getString( "base64");

                            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            locationName.setText(title);
                            locBeschrijving.setText(beschrijving);
                            locImage.setImageBitmap(decodedByte);

                        }catch (JSONException e){
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
