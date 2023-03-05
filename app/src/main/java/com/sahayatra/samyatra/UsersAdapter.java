package com.sahayatra.samyatra;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.Name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.Time);
        ImageView iv1=(ImageView) convertView.findViewById(R.id.profile);
        // Populate the data into the template view using the data object
        tvName.setText(user.name);
        tvHome.setText(user.time);
        Log.i(TAG, "Lucky6777 : " + user.pictu);
        Glide.with(getContext()).load(user.pictu).apply(RequestOptions.circleCropTransform()).into(iv1);
        // Return the completed view to render on screen
        return convertView;
    }
}
