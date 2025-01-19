package com.example.madproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VolunteerRegistrationActivity extends AppCompatActivity {

    // Declare the EditText fields for the form
    private EditText volunteerName, volunteerEmail, volunteerContact, volunteerArea;
    private Button submitButton, EditBtn, DeleteBtn;
    private GridView gridView;

    private RecyclerView recyclerView;
    private VolunteerAdapter adapter;
    private List<Volunteer> volunteerList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference volunteersRef = db.collection("volunteers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_registration); // Assuming the registration layout is activity_registration.xml

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VolunteerAdapter(volunteerList);
        recyclerView.setAdapter(adapter);

        // Fetch data from Firestore in real-time
        volunteersRef.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            volunteerList.clear();  // Clear the list before adding updated data
            if (querySnapshot != null) {
                for (DocumentSnapshot document : querySnapshot) {
                    String docID = document.getId();

                    Volunteer volunteer = document.toObject(Volunteer.class);
                    volunteerList.add(volunteer);
                    volunteer.setDocID(docID);
                }
            }

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged();
        });


        // Initialize the EditText fields and button
        volunteerName = findViewById(R.id.et_name);
        volunteerEmail = findViewById(R.id.et_email);
        volunteerContact = findViewById(R.id.et_phone);
        volunteerArea = findViewById(R.id.et_area);
        gridView = findViewById(R.id.grid_view);
        submitButton = findViewById(R.id.btn_submit);

        // Handle the submit button click event
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input data from the form
                String name = volunteerName.getText().toString().trim();
                String email = volunteerEmail.getText().toString().trim();
                String contact = volunteerContact.getText().toString().trim();
                String area = volunteerArea.getText().toString().trim();

                // Check if any of the fields are empty
                if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || area.isEmpty()) {
                    Toast.makeText(VolunteerRegistrationActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new volunteer object
                    Volunteer volunteer = new Volunteer(name, email, contact, area, "");

                    // Add the volunteer data to Firestore
                    volunteersRef.add(volunteer)
                            .addOnSuccessListener(documentReference -> {
                                // Successfully added volunteer data
                                Toast.makeText(VolunteerRegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                // Optionally, clear the form fields after successful submission
                                volunteerName.setText("");
                                volunteerEmail.setText("");
                                volunteerContact.setText("");
                                volunteerArea.setText("");
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                                Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });

        // Set up GridView (example data)
        List<String> gridItems = new ArrayList<>();
        gridItems.add("Our Volunteer Logo");
        gridItems.add("Banjir Kilat Kelantan");
        gridItems.add("Banjir Kilat Kedah");
        gridItems.add("Banjir Kilat Terengganu");

        // Define corresponding image resources (replace with actual image resource IDs)
        int[] itemImages = {
                R.drawable.placeholder_image,  // Replace with actual images
                R.drawable.placeholder_image1,
                R.drawable.placeholder_image2,
                R.drawable.placeholder_image3
        };

        // Create and set the GridAdapter
        GridAdapter gridAdapter = new GridAdapter(this, gridItems.toArray(new String[0]), itemImages);
        gridView.setAdapter(gridAdapter);
    }
}