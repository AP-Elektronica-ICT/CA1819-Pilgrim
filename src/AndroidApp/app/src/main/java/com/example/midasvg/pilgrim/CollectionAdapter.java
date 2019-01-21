package com.example.midasvg.pilgrim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CollectionAdapter extends ArrayAdapter<Location> {

    public CollectionAdapter(@NonNull Context context, Location[] locations) {
        super(context, R.layout.collection_row, locations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.collection_row, parent, false);

        Location location = getItem(position);

        TextView locationName = (TextView) customView.findViewById(R.id.locationName);
        ImageView locationImage = (ImageView) customView.findViewById(R.id.locationImage);

        locationName.setText(location.naam);
        locationImage.setImageBitmap(location.img);

        return customView;
    }
}
