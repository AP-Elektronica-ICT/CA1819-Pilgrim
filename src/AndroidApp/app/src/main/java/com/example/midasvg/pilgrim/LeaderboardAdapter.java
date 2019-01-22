package com.example.midasvg.pilgrim;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.constraint.ConstraintLayout;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


class LeaderboardAdapter extends ArrayAdapter<Pilgrimage>{

    public LeaderboardAdapter(@NonNull Context context, Pilgrimage[] pilgrimages) {
        super(context, R.layout.leaderboard_row ,pilgrimages);
    }

    RequestQueue requestQueue;
    JsonObjectRequest JsonRequest;
    String URL = "http://pilgrimapp.azurewebsites.net/api/profiles";

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View customView = Inflater.inflate(R.layout.leaderboard_row, parent, false);

        Pilgrimage pilgrimage = getItem(position);
        TextView pos = (TextView) customView.findViewById(R.id.posTxt);
        TextView username = (TextView) customView.findViewById(R.id.nametxt);
        TextView time = (TextView) customView.findViewById(R.id.timetxt);
        final ImageView Leaderboardimage = (ImageView) customView.findViewById(R.id.leaderboardimg);
        //Log.d("Adapter", "getView: " + position);
        pos.setText(position + 1 + ".");
        username.setText(pilgrimage.username);
        //time.setText(String.valueOf(pilgrimage.Time));


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


        time.setText(timeString);

        if(position + 1  <= 3){

            URL = "http://pilgrimapp.azurewebsites.net/api/profiles/" + pilgrimage.FireBaseID;
            requestQueue = Volley.newRequestQueue(getContext());

            JsonRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());

                            try {

                                JSONObject Profile = response;

                                String base64 = Profile.getString("base64");
                                //base64Image = base64;
                                //Log.d("Date Of Birth: ", "long: " + base64Image);
                                byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                Leaderboardimage.setImageBitmap(decodedByte);


                                //Log.d("JSONGET", "FirstName: " + FirstName + " LastName: " + LastName + " Nickname: " + NickName + " Base64: " + base64);
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("REST response", error.toString());
                        }
                    }
            );
            requestQueue.add(JsonRequest);
            Leaderboardimage.setImageResource(R.drawable.blank_profile_picture);
            LinearLayout test = (LinearLayout) customView.findViewById(R.id.smallLine);
            ViewGroup.LayoutParams params = test.getLayoutParams();
            params.height = 0;
            test.setLayoutParams(params);
        }
        else{
            ConstraintLayout d = (ConstraintLayout) customView.findViewById(R.id.GOK);
            d.setMaxHeight(Math.round((float) 30 * getContext().getResources().getDisplayMetrics().density));
            TextView user = (TextView) customView.findViewById(R.id.nametxt2);
            TextView timesmoll = (TextView) customView.findViewById(R.id.timetxt2);
            TextView pos2  = (TextView) customView.findViewById(R.id.posTxt2) ;
            user.setText(pilgrimage.username);
            timesmoll.setText(timeString);
            pos2.setText(position + 1 + ".");


        }

        return customView;
    }
}


