package com.example.midasvg.pilgrim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity {

    TextView gameTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //Tijd aanroepen van de vorige intent
        String timeSpent = getIntent().getStringExtra("Time");

        gameTime = (TextView) findViewById(R.id.txtSpent);
        gameTime.setText(timeSpent);


    }
}
