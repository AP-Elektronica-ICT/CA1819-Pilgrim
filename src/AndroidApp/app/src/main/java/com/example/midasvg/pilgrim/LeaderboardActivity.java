package com.example.midasvg.pilgrim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getSupportActionBar().setTitle("Leaderboard");

        Pilgrimage pilgrimage = new Pilgrimage(){{
            id = 1;
            FireBaseID = "a";
            startTime = 1;
            Time = 1;
            username = "Robin";

        }};
        Pilgrimage pilgrimage1 = new Pilgrimage(){{
            id = 2;
            FireBaseID = "b";
            startTime = 1;
            Time = 2;
            username = "Robin1";
        }};
        Pilgrimage pilgrimage2 = new Pilgrimage(){{
            id = 3;
            FireBaseID = "c";
            startTime = 1;
            Time = 3;
            username = "Robin2";
        }};
        String[] pilgrimages = {"Eerste", "Tweede", "Derde", "Vierde"};
        ListAdapter Adapter = new LeaderboardAdapter(this,pilgrimages);
        ListView leaderboardList = (ListView) findViewById(R.id.LeaderBoardListView);
        leaderboardList.setAdapter(Adapter);
    }
}
