package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateUserProfileActivity extends AppCompatActivity {

    private TextInputEditText nameEditText, addressEditText, contactNumberEditText;
    private Button saveButton;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        // Initialize Views
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        contactNumberEditText = findViewById(R.id.contactNumberEditText);
        saveButton = findViewById(R.id.save_button);
        profileImageView = findViewById(R.id.profileImage);

        // Set existing data if any (e.g., from shared preferences or database)
        loadUserProfileData();

        // Handle Save Button Click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });

        // Handle Profile Image Click (optional: allow user to select an image)
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open gallery or camera for profile picture change
                Intent intent = new Intent(UpdateUserProfileActivity.this, ImagePickerActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void loadUserProfileData() {
        // Assuming the data is retrieved from SharedPreferences or database
        // Replace with actual data loading mechanism
        nameEditText.setText("John Doe");
        addressEditText.setText("1234 Main St, City, Country");
        contactNumberEditText.setText("(+1) 234-567-890");
    }

    private void saveUserProfile() {
        // Get input data from fields
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String contactNumber = contactNumberEditText.getText().toString();

        // Check for empty fields
        if (name.isEmpty() || address.isEmpty() || contactNumber.isEmpty()) {
            Toast.makeText(UpdateUserProfileActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Save updated data (e.g., save to database or SharedPreferences)
            // Replace with actual save mechanism
            Toast.makeText(UpdateUserProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

            // Optionally, navigate back to the profile page or main activity
            finish();
        }
    }

    // Handle result from image picker (e.g., after user picks a profile image)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            // Get the image URI and set it to the profileImageView
            // For example, using Glide or Picasso to load the image into ImageView
            // Uri selectedImageUri = data.getData();
            // Glide.with(this).load(selectedImageUri).into(profileImageView);
        }
    }
}
