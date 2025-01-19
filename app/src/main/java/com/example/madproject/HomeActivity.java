package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        Button btnMultiHazardAlerts = findViewById(R.id.btnMultiHazardAlerts);
        Button btnInteractiveMaps = findViewById(R.id.btnInteractiveMaps);
        Button btnCommunityReports = findViewById(R.id.btnCommunityReports);
        Button btnEmergencyServices = findViewById(R.id.btnEmergencyServices);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnRegForm = findViewById(R.id.btnRegForm); // Registration button for Volunteer

        // Navigate to MultiHazardAlert activity
        btnMultiHazardAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MultiHazardAlert.class);
                startActivity(intent);
            }
        });

        // Handle Interactive Maps button click (currently unimplemented)
        btnInteractiveMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Interactive Maps button click
                Intent intent = new Intent(HomeActivity.this, InteractiveMap.class);
                startActivity(intent);
            }
        });

        // Handle Community Reports button click (currently unimplemented)
        btnCommunityReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Community Reports button click
            }
        });

        // Handle Emergency Services button click (currently unimplemented)
        btnEmergencyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Emergency Services button click
                Intent intent = new Intent(HomeActivity.this, EmergencyContact.class);
                startActivity(intent);
            }
        });

        // Handle Logout button click
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Logout button click (end the activity)
                finish();
            }
        });

        // Navigate to the Volunteer Registration Activity
        btnRegForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Volunteer Registration activity
                Intent intent = new Intent(HomeActivity.this, VolunteerRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}