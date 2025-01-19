package com.example.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private final Context context;
    private final String[] itemNames;
    private final int[] itemImages;

    // Constructor
    public GridAdapter(Context context, String[] itemNames, int[] itemImages) {
        this.context = context;
        this.itemNames = itemNames;
        this.itemImages = itemImages;
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public Object getItem(int position) {
        return itemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout for each grid item
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.gridItemImage);
            viewHolder.textView = convertView.findViewById(R.id.gridItemText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set data for each grid item
        viewHolder.imageView.setImageResource(itemImages[position]);
        viewHolder.textView.setText(itemNames[position]);

        return convertView;
    }

    // ViewHolder class for better performance
    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
