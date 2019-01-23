package com.example.midasvg.pilgrim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectable);

        getSupportActionBar().setTitle("Collectable");

        int index = getIntent().getIntExtra("id2",0);

        String naam = getIntent().getStringExtra("name");
        Bitmap img = getIntent().getParcelableExtra("data");
        String besch = getIntent().getStringExtra("desc");

        /*
       byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
       Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); */


        TextView locName = (TextView) findViewById(R.id.txtTitle);
        TextView locBeschrijving = (TextView) findViewById(R.id.txtBeschrijving);
        ImageView locImage = (ImageView) findViewById(R.id.locImage);


        locName.setText(naam);
        locImage.setImageBitmap(img);
        locBeschrijving.setText(besch);
    }
}
