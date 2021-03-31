package com.example.mapdesease;

import java.time.LocalDate;
import java.util.Date;

public class Label {
    Double longitude;
    Double latitude;
    Person person;
    String symptoms;
    String date;

    public Label(Double longitude, Double latitude, Person person, String symptoms, String date) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.person = person;
        this.symptoms = symptoms;
        this.date = date;
    }

    public Label() {
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

