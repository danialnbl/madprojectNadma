package com.example.madproject;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.madproject.databinding.ActivityBencanaMapBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BencanaMap extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityBencanaMapBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBencanaMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("locations");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable zoom controls on the map
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable gestures for zooming
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        // Enable user location if permission is granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Load markers from Firebase
        loadMarkersFromFirebase();

        // Add a sample marker
        //LatLng sampleLocation = new LatLng(4.23159200, 103.42535180);
        //mMap.addMarker(new MarkerOptions().position(sampleLocation).title("Banjir"));
    }

    private void loadMarkersFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing markers
                mMap.clear();

                // Loop through Firebase data and add markers
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    double latitude = snapshot.child("latitude").getValue(Double.class);
                    double longitude = snapshot.child("longitude").getValue(Double.class);
                    String title = snapshot.child("title").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    String severity = snapshot.child("severity").getValue(String.class);

                    LatLng location = new LatLng(latitude, longitude);

                    // Check for type and add respective markers
                    if (type.equals("Flood")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(title)
                                .snippet("Severity: " + severity)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.floodicon)));
                    } else if (type.equals("Landslide")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(title)
                                .snippet("Severity: " + severity)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.landslideicon)));  // Custom landslide icon
                    } else if (type.equals("Haze")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(title)
                                .snippet("Severity: " + severity)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hazeicon)));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BencanaMap.this, "Failed to load markers: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // Get current location
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Move camera to current location
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 5));
                    } else {
                        Toast.makeText(this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    getCurrentLocation();
                }
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission is required to show current location", Toast.LENGTH_SHORT).show();
            }
        }
    }
}