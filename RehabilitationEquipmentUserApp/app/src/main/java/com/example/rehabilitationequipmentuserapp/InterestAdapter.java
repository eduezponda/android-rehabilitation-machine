package com.example.rehabilitationequipmentuserapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rehabilitationequipmentuserapp.Models.InterestPoint;

import java.util.List;

public class InterestAdapter extends ArrayAdapter<InterestPoint> {

    public InterestAdapter(Context context, List<InterestPoint> points) {
        super(context, 0, points);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InterestPoint interestPoint = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        TextView textName = convertView.findViewById(R.id.text_bold);
        TextView textData = convertView.findViewById(R.id.text_grey);
        ImageView image = convertView.findViewById(R.id.image);

        textName.setText(interestPoint.getName());
        textData.setText(interestPoint.getData());
        image.setImageBitmap(interestPoint.getImage());

        return convertView;
    }
}

