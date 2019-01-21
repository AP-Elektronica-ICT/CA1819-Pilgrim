package com.example.midasvg.pilgrim;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

class PilgrimageAdapter extends ArrayAdapter<Pilgrimage> {

    public PilgrimageAdapter(@NonNull Context context, Pilgrimage[] pilgrimages) {
        super(context, R.layout.custom_row, pilgrimages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        Pilgrimage pilgrimage = getItem(position);

        TextView txtID = (TextView) customView.findViewById(R.id.pilgrimageID);
        TextView txtStart = (TextView) customView.findViewById(R.id.txtStarted);
        TextView txtTimeSpent = (TextView) customView.findViewById(R.id.txtTimeSpent);


        txtID.setText("Pilgrimage " +String.valueOf(pilgrimage.id));

        //tijd nog formatteren
        txtStart.setText("Started on: " +String.valueOf(pilgrimage.startTime));

        int seconds = pilgrimage.Time%60;
        int temp = pilgrimage.Time - (pilgrimage.Time%60);
        int minutestotal = temp/60;
        int minutes = minutestotal%60;
        int temp2 = minutestotal - (minutestotal%60);
        int hours = temp2/60;

        String timeString = String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
        txtTimeSpent.setText("Duration: " + timeString);

        return customView;
    }
}
