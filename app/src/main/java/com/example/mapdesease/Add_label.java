package com.example.mapdesease;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;

public class Add_label extends AppCompatActivity {
    final String[] illness = { "Кашель влажный", "Жар", "Боль в Горле", "Температура",
            "Кашель сухой", "Боль в лобной доли головы", "Боль в затылки", "Потеря запахов", "Коронавирус" };

    EditText AgeEditText;
    AutoCompleteTextView SymtomsEditText;
    EditText DateEditText;
    Button saveButton;
    Button mapButton;
    CalendarView calender;
    Location PersonLocation;
    FirebaseFirestore db;
    Double Latitude;
    Double Longitude;
    private FusedLocationProviderClient fusedLocationClient;
    Calendar dateAndTime=Calendar.getInstance();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_label);
        AgeEditText =  findViewById(R.id.AgeEditText);
        SymtomsEditText = findViewById(R.id.SymptomsEditText);

        DateEditText = findViewById(R.id.DateEditText);
        saveButton = findViewById(R.id.SaveButton);
        mapButton  = findViewById(R.id.MapButton);
        calender = findViewById(R.id.calendarView1);
        setInitialDateTime();
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
        DateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    setDate(v);
                }

                return true;
            }
        });
        SymtomsEditText.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, illness));

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
    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(Add_label.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(Add_label.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        DateEditText.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
            setTime(view);
        }
    };
}
