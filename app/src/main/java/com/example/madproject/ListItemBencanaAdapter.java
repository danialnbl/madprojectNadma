package com.example.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class ListItemBencanaAdapter extends RecyclerView.Adapter<ListItemBencanaAdapter.ViewHolder> {

    private Context context;
    private List<Map<String, Object>> locationList;

    public ListItemBencanaAdapter(Context context, List<Map<String, Object>> locations) {
        this.context = context;
        this.locationList = locations;
    }

    // ViewHolder class to hold the views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView details;
        ImageView locationImage;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            locationImage = itemView.findViewById(R.id.location_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View itemView = LayoutInflater.from(context).inflate(R.layout.listitem_bencana, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String, Object> location = locationList.get(position);

        if (location != null) {
            // Get values with null and type checks
            String titleText = location.get("title") != null ? location.get("title").toString() : "Unknown Title";
            String type = location.get("type") != null ? location.get("type").toString() : "Unknown Type";
            String severity = location.get("severity") != null ? location.get("severity").toString() : "Unknown Severity";

            double latitude = 0.0;
            double longitude = 0.0;

            try {
                if (location.get("latitude") instanceof Double) {
                    latitude = (double) location.get("latitude");
                } else if (location.get("latitude") instanceof Long) {
                    latitude = ((Long) location.get("latitude")).doubleValue();
                }

                if (location.get("longitude") instanceof Double) {
                    longitude = (double) location.get("longitude");
                } else if (location.get("longitude") instanceof Long) {
                    longitude = ((Long) location.get("longitude")).doubleValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Set the data to the views in ViewHolder
            holder.title.setText(titleText);
            String detailsText = "Type: " + type +
                    "\nSeverity: " + severity +
                    "\nLatitude: " + latitude +
                    "\nLongitude: " + longitude;
            holder.details.setText(detailsText);

            // Set image based on location type
            int imageResId = getImageForType(type);
            holder.locationImage.setImageResource(imageResId);
        }
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    // Method to map location type to image resource
    private int getImageForType(String type) {
        switch (type) {
            case "Flood":
                return R.drawable.floodicon; // Replace with actual image resource
            case "Haze":
                return R.drawable.hazeicon; // Replace with actual image resource
            case "Landslide":
                return R.drawable.landslideicon; // Replace with actual image resource
            // Add more cases as needed
            default:
                return R.drawable.logomadapp; // Default image if type is unknown
        }
    }
}
