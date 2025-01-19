package com.example.madproject;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MultiHazardAlert extends AppCompatActivity {

    private TextView weatherAlert, floodAlert, hazeAlert, tsunamiAlert, temperatureValue;
    private Button refreshButton, refreshTemperatureButton; // Added new button for temperature

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_hazard_alert);

        // Initialize UI elements
        weatherAlert = findViewById(R.id.weatherAlert);
        floodAlert = findViewById(R.id.floodAlert);
        hazeAlert = findViewById(R.id.hazeAlert);
        tsunamiAlert = findViewById(R.id.tsunamiAlert);
        temperatureValue = findViewById(R.id.temperatureValue);
        refreshButton = findViewById(R.id.refreshButton);
        refreshTemperatureButton = findViewById(R.id.refreshTemperatureButton); // Link new button for refreshing temperature

        // Set initial alerts and temperature
        updateAlerts();
        updateTemperature();

        // Refresh button action (refresh both alerts and temperature)
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlerts();
                updateTemperature();  // Refresh both alerts and temperature
            }
        });

        // New refresh temperature button action (refresh only temperature)
        refreshTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTemperature();  // Only refresh temperature
            }
        });
    }

    // Method to update alerts with random mock data
    private void updateAlerts() {
        String[] mockAlerts = {"No alerts at the moment.", "Minor issues reported.", "Severe alert! Take action immediately!"};
        Random random = new Random();

        weatherAlert.setText("Weather Alerts: " + mockAlerts[random.nextInt(mockAlerts.length)]);
        floodAlert.setText("Flood Alerts: " + mockAlerts[random.nextInt(mockAlerts.length)]);
        hazeAlert.setText("Haze Alerts: " + mockAlerts[random.nextInt(mockAlerts.length)]);
        tsunamiAlert.setText("Tsunami Alerts: " + mockAlerts[random.nextInt(mockAlerts.length)]);
    }

    // Method to update the temperature with live data from the battery
    private void updateTemperature() {
        float deviceTemperature = getBatteryTemperature();
        temperatureValue.setText(String.format("%.1f°C", deviceTemperature));
    }

    // Method to get the device's battery temperature
    private float getBatteryTemperature() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, intentFilter);

        if (batteryStatus != null) {
            int temperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            if (temperature != -1) {
                return temperature / 10.0f; // Convert from deci-Celsius to Celsius
            }
        }

        // Return a default value if temperature is unavailable
        return 25.0f;
    }
}
