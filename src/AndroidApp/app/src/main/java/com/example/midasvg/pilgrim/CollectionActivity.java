package com.example.midasvg.pilgrim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        getSupportActionBar().setTitle("Collection");
    }
}
