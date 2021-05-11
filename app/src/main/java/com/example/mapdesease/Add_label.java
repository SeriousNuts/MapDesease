package com.example.mapdesease;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Add_label extends AppCompatActivity {
    EditText AgeEditText;
    EditText SymtomsEditText;
    EditText DateEditText;
    Button saveButton;
    Button mapButton;
    Location PersonLocation;
    FirebaseFirestore db;
    Double Latitude;
    Double Longitude;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_label);
        AgeEditText =  findViewById(R.id.AgeEditText);
        SymtomsEditText = findViewById(R.id.SymptomsEditText);
        DateEditText = findViewById(R.id.DateEditText);
        saveButton = findViewById(R.id.SaveButton);
        mapButton  = findViewById(R.id.MapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapActivity = new Intent(Add_label.this, MapsActivity.class);
                startActivity(mapActivity);
            }
        });
        getLocation();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AgeEditText.length()!=0 && SymtomsEditText.length()!=0 && DateEditText.length()!=0) {

                    SaveOnDatabase();
                    Intent MapsActivityIntent =  new Intent(Add_label.this, MapsActivity.class);
                    startActivity(MapsActivityIntent);
                }
                else {
                    Toast.makeText(Add_label.this, "Заполните все поля", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void SaveOnDatabase(){
        db = FirebaseFirestore.getInstance();
        AgeEditText =  findViewById(R.id.AgeEditText);
        SymtomsEditText = findViewById(R.id.SymptomsEditText);
        DateEditText = findViewById(R.id.DateEditText);
        Person person;
        person = new Person("Пёстрый Селезень", Integer.parseInt(AgeEditText.getText().toString()));
        Label label =  new Label(Longitude,Latitude,person, SymtomsEditText.getText().toString(),DateEditText.getText().toString());
        db.collection("labels").add(label);
    }
    public void  getLocation(){
        int permissionStatus = ContextCompat.checkSelfPermission(Add_label.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionStatus == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Latitude = location.getLatitude();
                                Longitude = location.getLongitude();
                                //PersonLocation.setLatitude(location.getLatitude());
                                //PersonLocation.setLongitude(location.getLongitude());

                            }
                        }
                    });
        }
        else {
            ActivityCompat.requestPermissions(Add_label.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

    }
}