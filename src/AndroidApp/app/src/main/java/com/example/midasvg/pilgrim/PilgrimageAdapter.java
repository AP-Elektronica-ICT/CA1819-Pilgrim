package com.example.midasvg.pilgrim;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;

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

        TextView txtStart = (TextView) customView.findViewById(R.id.txtStarted);
        TextView txtTimeSpent = (TextView) customView.findViewById(R.id.txtTimeSpent);

        long dv = Long.valueOf(pilgrimage.startTime)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd/MM/yyyy").format(df);
        txtStart.setText(String.valueOf(vv));

        int seconds = pilgrimage.Time%60;
        int temp = pilgrimage.Time - (pilgrimage.Time%60);
        int minutestotal = temp/60;
        int minutes = minutestotal%60;
        int temp2 = minutestotal - (minutestotal%60);
        int hours = temp2/60;

        String timeString;
        if(hours == 0 && minutes ==0){
            timeString = String.valueOf(seconds) + " seconds";
        }else{
            timeString = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        }
        txtTimeSpent.setText(timeString);

        return customView;
    }
}
