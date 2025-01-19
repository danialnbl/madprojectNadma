package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InteractiveMap extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListItemBencanaAdapter adapter;
    private List<Map<String, Object>> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_map);

        recyclerView = findViewById(R.id.recyclerView);
        locationList = new ArrayList<>();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListItemBencanaAdapter(this, locationList);
        recyclerView.setAdapter(adapter);

        // Firebase database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("locations");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Object data = dataSnapshot.getValue();
                    if (data instanceof Map) { // Ensure the data is a Map
                        Map<String, Object> location = (Map<String, Object>) data;
                        locationList.add(location);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InteractiveMap.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the button
        Button showEvacuationsButton = findViewById(R.id.show_evacuations_button);

        // Set click listener to redirect to URL
        showEvacuationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InteractiveMap.this, BencanaMap.class);
            startActivity(intent);
        });

        Button backHome = findViewById(R.id.back_to_home_button);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start the EmergencyActivity
                Intent intent = new Intent(InteractiveMap.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
