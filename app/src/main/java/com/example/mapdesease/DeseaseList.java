package com.example.mapdesease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DeseaseList extends AppCompatActivity {
    RecyclerView  recyclerViewDeseaseList;
    private DeseaseListRecView DeseaseListFirestoreAdapter;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Query queryChart = firebaseFirestore.collection("labels");
    int ViewPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desease_list);
        recyclerViewDeseaseList = findViewById(R.id.DeseaseListRecyleView);

        FirestoreRecyclerOptions<Label> options =
                new FirestoreRecyclerOptions.Builder<Label>()
                        .setQuery(queryChart, Label.class)
                        .build();

        recyclerViewDeseaseList.setLayoutManager(new LinearLayoutManager(this));
        DeseaseListFirestoreAdapter = new DeseaseListRecView(options);


        recyclerViewDeseaseList.setAdapter(DeseaseListFirestoreAdapter);
    }
    @Override
    public void onStop(){
        super.onStop();
        DeseaseListFirestoreAdapter.stopListening();
    }

    @Override
    public void onStart(){
        super.onStart();
        DeseaseListFirestoreAdapter.startListening();
    }
}