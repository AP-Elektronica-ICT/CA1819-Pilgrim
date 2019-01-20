package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private DrawerLayout nDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle nToggle;

    private FirebaseAuth mAuth;

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getSupportActionBar().setTitle("Leaderboard");

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


        String URL = "http://pilgrimapp.azurewebsites.net/api/pilgrimages";
        final List<Pilgrimage> pilgrimagesList = new ArrayList<Pilgrimage>();

        requestQueue = Volley.newRequestQueue(this);

        Log.d("GETTER", "onCreate: ");
        JsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());

                        try {

                            JSONObject Leaderboard = response;
                            JSONArray pilgrimages = Leaderboard.getJSONArray("pilgrimages");
                            Log.d("GETTER", "onResponse: " + pilgrimages.length());
                            for (int i = 0; i < pilgrimages.length(); i++) {
                                Log.d("GETTER", "onResponse: TEST");
                                final JSONObject temp = pilgrimages.getJSONObject(i);
                               Pilgrimage pilgrimagetemp = new Pilgrimage(){{
                                   id = temp.getInt("id");
                                   FireBaseID = temp.getString("fireBaseID");
                                   username = temp.getString("username");
                                   startTime = temp.getInt("startTime");
                                   Time = temp.getInt("time");
                                }};
                               pilgrimagesList.add(pilgrimagetemp);

                            }
                            Pilgrimage[] pilgrimagesarray = new Pilgrimage[pilgrimagesList.size()];

                            pilgrimagesarray = pilgrimagesList.toArray(pilgrimagesarray);
                            //String[] pilgrimages = {"Eerste", "Tweede", "Derde", "Vierde"};
                            ListAdapter Adapter = new LeaderboardAdapter(getBaseContext(),pilgrimagesarray);
                            ListView leaderboardList = (ListView) findViewById(R.id.LeaderBoardListView);
                            leaderboardList.setAdapter(Adapter);
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
                Intent intentCollection = new Intent(LeaderboardActivity.this, CollectionActivity.class);
                startActivity(intentCollection);
                break;
            case R.id.nav_game:
                Intent intentGame = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(intentGame);
                break;
            case R.id.nav_leaderboard:
                Intent intentLeaderboard = new Intent(LeaderboardActivity.this, LeaderboardActivity.class);
                startActivity(intentLeaderboard);
                break;
            case  R.id.nav_profile:
                Intent intentProfile = new Intent(LeaderboardActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_guide:
                Intent intentGuide = new Intent(LeaderboardActivity.this, GuideActivity.class);
                startActivity(intentGuide);
                break;
            case R.id.nav_about:
                Intent intentAbout = new Intent(LeaderboardActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Toast.makeText(LeaderboardActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent logOut = new Intent(LeaderboardActivity.this, LoginActivity.class);
                startActivity(logOut);
                break;
        }
    }
}
