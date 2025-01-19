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

    private EditText volunteerName, volunteerEmail, volunteerContact, volunteerArea;
    private Button submitButton;
    private GridView gridView;
    private RecyclerView recyclerView;
    private VolunteerAdapter adapter;
    private List<Volunteer> volunteerList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference volunteersRef = db.collection("volunteers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_registration);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VolunteerAdapter(volunteerList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new VolunteerAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Volunteer volunteer = volunteerList.get(position);
                showEditDialog(volunteer);
            }

            @Override
            public void onDeleteClick(int position) {
                Volunteer volunteer = volunteerList.get(position);
                deleteVolunteer(volunteer);
            }
        });

        volunteersRef.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            volunteerList.clear();
            if (querySnapshot != null) {
                for (DocumentSnapshot document : querySnapshot) {
                    String docID = document.getId();
                    Volunteer volunteer = document.toObject(Volunteer.class);
                    volunteer.setDocID(docID);
                    volunteerList.add(volunteer);
                }
            }

            adapter.notifyDataSetChanged();
        });

        volunteerName = findViewById(R.id.et_name);
        volunteerEmail = findViewById(R.id.et_email);
        volunteerContact = findViewById(R.id.et_phone);
        volunteerArea = findViewById(R.id.et_area);
        gridView = findViewById(R.id.grid_view);
        submitButton = findViewById(R.id.btn_submit);

        submitButton.setOnClickListener(v -> {
            String name = volunteerName.getText().toString().trim();
            String email = volunteerEmail.getText().toString().trim();
            String contact = volunteerContact.getText().toString().trim();
            String area = volunteerArea.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || area.isEmpty()) {
                Toast.makeText(VolunteerRegistrationActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            } else {
                Volunteer volunteer = new Volunteer(name, email, contact, area, "");
                volunteersRef.add(volunteer)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(VolunteerRegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            volunteerName.setText("");
                            volunteerEmail.setText("");
                            volunteerContact.setText("");
                            volunteerArea.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        List<String> gridItems = new ArrayList<>();
        gridItems.add("Our Volunteer Logo");
        gridItems.add("Banjir Kilat Kelantan");
        gridItems.add("Banjir Kilat Kedah");
        gridItems.add("Banjir Kilat Terengganu");

        int[] itemImages = {
                R.drawable.placeholder_image,
                R.drawable.placeholder_image1,
                R.drawable.placeholder_image2,
                R.drawable.placeholder_image3
        };

        GridAdapter gridAdapter = new GridAdapter(this, gridItems.toArray(new String[0]), itemImages);
        gridView.setAdapter(gridAdapter);
    }

    private void showEditDialog(Volunteer volunteer) {
        // Implement a dialog or new activity to edit the volunteer details
        // For simplicity, we'll just update the Firestore document directly here
        volunteerName.setText(volunteer.getName());
        volunteerEmail.setText(volunteer.getEmail());
        volunteerContact.setText(volunteer.getPhone());
        volunteerArea.setText(volunteer.getPreferredArea());

        submitButton.setText("Update");
        submitButton.setOnClickListener(v -> {
            String name = volunteerName.getText().toString().trim();
            String email = volunteerEmail.getText().toString().trim();
            String contact = volunteerContact.getText().toString().trim();
            String area = volunteerArea.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || area.isEmpty()) {
                Toast.makeText(VolunteerRegistrationActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            } else {
                Volunteer updatedVolunteer = new Volunteer(name, email, contact, area, volunteer.getDocID());
                volunteersRef.document(volunteer.getDocID()).set(updatedVolunteer)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(VolunteerRegistrationActivity.this, "Volunteer Updated!", Toast.LENGTH_SHORT).show();
                            submitButton.setText("Submit");
                            submitButton.setOnClickListener(this::onSubmitButtonClick);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private void deleteVolunteer(Volunteer volunteer) {
        volunteersRef.document(volunteer.getDocID()).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(VolunteerRegistrationActivity.this, "Volunteer Deleted!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void onSubmitButtonClick(View v) {
        String name = volunteerName.getText().toString().trim();
        String email = volunteerEmail.getText().toString().trim();
        String contact = volunteerContact.getText().toString().trim();
        String area = volunteerArea.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || area.isEmpty()) {
            Toast.makeText(VolunteerRegistrationActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        } else {
            Volunteer volunteer = new Volunteer(name, email, contact, area, "");
            volunteersRef.add(volunteer)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(VolunteerRegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        volunteerName.setText("");
                        volunteerEmail.setText("");
                        volunteerContact.setText("");
                        volunteerArea.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(VolunteerRegistrationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}