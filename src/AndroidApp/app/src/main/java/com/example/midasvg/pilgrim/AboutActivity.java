package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AboutActivity extends AppCompatActivity {
    private DrawerLayout nDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle nToggle;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setTitle("About");

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
                Intent intentCollection = new Intent(AboutActivity.this, CollectionActivity.class);
                startActivity(intentCollection);
                break;

            case R.id.nav_game:
                Intent intentGame = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intentGame);
                break;
            case R.id.nav_leaderboard:
                Intent intentLeaderboard = new Intent(AboutActivity.this, LeaderboardActivity.class);
                startActivity(intentLeaderboard);
                break;
            case  R.id.nav_profile:
                Intent intentProfile = new Intent(AboutActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_guide:
                Intent intentGuide = new Intent(AboutActivity.this, GuideActivity.class);
                startActivity(intentGuide);
                break;
            case R.id.nav_about:
                Intent intentAbout = new Intent(AboutActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Toast.makeText(AboutActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent logOut = new Intent(AboutActivity.this, LoginActivity.class);
                startActivity(logOut);
                break;
        }
    }
}
