package com.example.madproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder> {

    private List<Volunteer> volunteerList;

    public VolunteerAdapter(List<Volunteer> volunteerList) {
        this.volunteerList = volunteerList;
    }

    @Override
    public VolunteerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_item, parent, false);
        return new VolunteerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VolunteerViewHolder holder, int position) {
        Volunteer volunteer = volunteerList.get(position);
        holder.nameTextView.setText("Name: " + volunteer.getName());
        holder.emailTextView.setText("Email: " + volunteer.getEmail());
        holder.phoneTextView.setText("Phone: " +volunteer.getPhone());
        holder.preferredAreaTextView.setText("Preferred Area: " +volunteer.getPreferredArea()); // Bind the preferred area
    }

    @Override
    public int getItemCount() {
        return volunteerList.size();
    }

    public static class VolunteerViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView, preferredAreaTextView;

        public VolunteerViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            emailTextView = itemView.findViewById(R.id.tv_email);
            phoneTextView = itemView.findViewById(R.id.tv_phone);
            preferredAreaTextView = itemView.findViewById(R.id.tv_preferred_area); // Add this
        }
    }
}

