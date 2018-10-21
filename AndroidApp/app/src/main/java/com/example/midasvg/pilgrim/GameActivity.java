package com.example.midasvg.pilgrim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    private double[] currentPlace = {1,2};
    private double[] nextPlace ={3,4};
    private double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Deze knop opent de vuforia app
        final Button openCamera = (Button) findViewById(R.id.bttnCamera);
        openCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.issam.PilgrimAr");
                startActivity(intent);
            }
        });

        //Alert message wanneer de gebruiker in game zit en op 'Quit' drukt.
        final Button quitGame = (Button) findViewById(R.id.bttnQuit);
        quitGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to quit?");
                builder.setMessage("The progress you've made will be deleted & you will not recieve any points!");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }

    //afstand tussen twee coördinaten berekenen
    private void checkDistance(){
        distance = Math.sqrt((Math.pow(nextPlace[0]-currentPlace[0],2)+(Math.pow(nextPlace[1]-currentPlace[1],2))));
    }
}