package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Button openCamera = (Button) findViewById(R.id.bttnCamera);
        openCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.issam.PilgrimAr");
                startActivity(intent);
            }
        });
    }
}
