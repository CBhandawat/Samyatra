package com.sahayatra.samyatra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class UsersAdapter1 extends ArrayAdapter<User2> {
    public UsersAdapter1(Context context, ArrayList<User2> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User2 user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user1, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.Name1);
        TextView tvPhone90 = (TextView) convertView.findViewById(R.id.Phone90);

        // Populate the data into the template view using the data object
        tvName.setText(user.name4);
        tvPhone90.setText(user.phone4);

        // Return the completed view to render on screen
        return convertView;
    }
}
