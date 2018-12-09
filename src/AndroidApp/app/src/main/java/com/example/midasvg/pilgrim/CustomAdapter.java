package com.example.midasvg.pilgrim;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(Context context, String[] pilgrimages) {
        super(context, R.layout.custom_row, pilgrimages);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        String pilgrimID = getItem(position);

        TextView txtID = (TextView) customView.findViewById(R.id.pilgrimageID);
        TextView txtStart = (TextView) customView.findViewById(R.id.txtStartTime);
        TextView txtTimeSpent = (TextView) customView.findViewById(R.id.txtTimeSpent);

        txtID.setText(pilgrimID);

        return customView;
    }
}
