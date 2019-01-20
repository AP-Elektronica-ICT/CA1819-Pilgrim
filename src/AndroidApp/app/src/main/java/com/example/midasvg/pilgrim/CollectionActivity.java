package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private DrawerLayout nDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle nToggle;

    private FirebaseAuth mAuth;

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setTitle("Collection");

        //De button wordt ge-enabled op de Action Bar
        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);
        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();

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

    //Nu kan er op de button gedrukt worden
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (nToggle.onOptionsItemSelected(item)){

            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item){
        switch (item.getItemId()){
            case R.id.nav_collections:
                Intent intentCollection = new Intent(CollectionActivity.this, CollectionActivity.class);
                startActivity(intentCollection);
                break;

            case R.id.nav_game:
                Intent intentGame = new Intent(CollectionActivity.this, MainActivity.class);;
                startActivity(intentGame);
                break;
            case R.id.nav_leaderboard:
                Intent intentLeaderboard = new Intent(CollectionActivity.this, LeaderboardActivity.class);
                startActivity(intentLeaderboard);
                break;
            case  R.id.nav_profile:
                Intent intentProfile = new Intent(CollectionActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_guide:
                Intent intentGuide = new Intent(CollectionActivity.this, GuideActivity.class);
                startActivity(intentGuide);
                break;
            case R.id.nav_about:
                Intent intentAbout = new Intent(CollectionActivity.this, MainActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Toast.makeText(CollectionActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent logOut = new Intent(CollectionActivity.this, LoginActivity.class);
                startActivity(logOut);
                break;
        }
    }
}
