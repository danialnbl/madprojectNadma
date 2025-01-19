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

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        Button btnMultiHazardAlerts = findViewById(R.id.btnMultiHazardAlerts);
        Button btnInteractiveMaps = findViewById(R.id.btnInteractiveMaps);
        Button btnHazardReports = findViewById(R.id.btnHazardReports);
        Button btnEmergencyServices = findViewById(R.id.btnEmergencyServices);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnRegForm = findViewById(R.id.btnRegForm); // Registration button for Volunteer
        Button btnProfile = findViewById(R.id.btnProfile); // Profile button)

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
        btnHazardReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(intent);
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
                // Sign out from Firebase Authentication
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();

                // Navigate to the login activity (assuming LoginActivity exists)
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
                startActivity(intent);
                finish(); // End the current activity
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

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Volunteer Registration activity
                Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}