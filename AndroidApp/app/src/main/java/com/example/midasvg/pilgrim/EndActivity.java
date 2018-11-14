package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class EndActivity extends AppCompatActivity {

    TextView gameTime;
    TextView txtDistance;
    float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //Tijd aanroepen van de vorige intent
        String timeSpent = getIntent().getStringExtra("Time");

        distance = getIntent().getFloatExtra("distance", 0);
        gameTime = (TextView) findViewById(R.id.txtSpent);
        gameTime.setText(timeSpent);

        txtDistance = (TextView)findViewById(R.id.txtDistance);
        txtDistance.setText(""+new DecimalFormat("##.##").format(distance/1000) + "Km");

        final Button backToMain = (Button) findViewById(R.id.bttnMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
