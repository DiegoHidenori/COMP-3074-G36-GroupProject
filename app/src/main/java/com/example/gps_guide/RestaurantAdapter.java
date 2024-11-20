package com.example.gps_guide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        super(context, 0, restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant, parent, false);
        }

        Restaurant restaurant = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.name_txt_view);
        TextView addressTextView = convertView.findViewById(R.id.address_txt_view);
        TextView tagsTextView = convertView.findViewById(R.id.tags_txt_view);

        nameTextView.setText(restaurant.getName());
        addressTextView.setText(restaurant.getAddress());
        tagsTextView.setText(String.format("Tags: '%s'", restaurant.getTags()));

        return convertView;
    }

}
