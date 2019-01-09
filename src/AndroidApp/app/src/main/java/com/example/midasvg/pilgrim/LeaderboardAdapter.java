package com.example.midasvg.pilgrim;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.constraint.ConstraintLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;




class LeaderboardAdapter extends ArrayAdapter<String>{

    public LeaderboardAdapter(@NonNull Context context, String[] pilgrimages) {
        super(context, R.layout.leaderboard_row ,pilgrimages);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View customView = Inflater.inflate(R.layout.leaderboard_row, parent, false);

        String pilgrimage = getItem(position);
        TextView pos = (TextView) customView.findViewById(R.id.posTxt);
        TextView username = (TextView) customView.findViewById(R.id.nametxt);
        TextView time = (TextView) customView.findViewById(R.id.timetxt);
        final ImageView Leaderboardimage = (ImageView) customView.findViewById(R.id.leaderboardimg);
        //Log.d("Adapter", "getView: " + position);
        pos.setText(position + 1 + ".");
        username.setText(pilgrimage);
        //time.setText(pilgrimage.Time);

        if(position + 1  <= 3){


            /*requestQueue = Volley.newRequestQueue(getContext());

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
            requestQueue.add(JsonRequest);*/
            Leaderboardimage.setImageResource(R.drawable.blank_profile_picture);
        }
        else{
            Leaderboardimage.setMaxWidth(0);
            Leaderboardimage.setMaxHeight(0);
            pos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            username.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);
            ConstraintLayout c = (ConstraintLayout) customView.findViewById(R.id.constraintRow);
            c.setMaxWidth(0);

        }

        return customView;
    }
}


