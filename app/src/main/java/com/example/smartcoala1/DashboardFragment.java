package com.example.smartcoala1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.smartcoala1.AutoOnOffActivity;
import com.example.smartcoala1.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private Switch switch1, switch2, switch3, switch4;
    private MaterialCardView cardView1, cardView2, cardView3, cardView4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize Views
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);

        switch1 = view.findViewById(R.id.switch1);
        switch2 = view.findViewById(R.id.switch2);
        switch3 = view.findViewById(R.id.switch3);
        switch4 = view.findViewById(R.id.switch4);

        cardView1 = view.findViewById(R.id.cardView1);
        cardView2 = view.findViewById(R.id.cardView2);
        cardView3 = view.findViewById(R.id.cardView3);
        cardView4 = view.findViewById(R.id.cardView4);

        // Retrieve the UID of the logged-in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            final String uid = currentUser.getUid();

            // Set listeners for switches using the UID
            setSwitchListener(uid, "port1", switch1, imageView1, cardView1);
            setSwitchListener(uid, "port2", switch2, imageView2, cardView2);
            setSwitchListener(uid, "port3", switch3, imageView3, cardView3);
            setSwitchListener(uid, "port4", switch4, imageView4, cardView4);

            // Retrieve initial state from Firebase using the UID
            retrieveSwitchState(uid, "port1", switch1, imageView1, cardView1);
            retrieveSwitchState(uid, "port2", switch2, imageView2, cardView2);
            retrieveSwitchState(uid, "port3", switch3, imageView3, cardView3);
            retrieveSwitchState(uid, "port4", switch4, imageView4, cardView4);
        }

        // Set click listeners for imageViews
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutoOnOffActivity("port1");
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutoOnOffActivity("port2");
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutoOnOffActivity("port3");
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutoOnOffActivity("port4");
            }
        });

        return view;
    }

    // Method to set the state of the switch and image view based on Firebase value
    private void retrieveSwitchState(final String uid, final String portName, final Switch switchView, final ImageView imageView, final MaterialCardView cardView) {
        databaseReference.child(uid).child("components").child(portName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String portStatus = dataSnapshot.getValue(String.class);
                if (portStatus != null && portStatus.equals("1")) {
                    switchView.setChecked(true);
                    cardView.setCardBackgroundColor(Color.GREEN);
                } else {
                    switchView.setChecked(false);
                    cardView.setCardBackgroundColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    // Method to set the listener for the switch
    private void setSwitchListener(final String uid, final String portName, final Switch switchView, final ImageView imageView, final MaterialCardView cardView) {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status = isChecked ? "1" : "0";
                databaseReference.child(uid).child("components").child(portName).setValue(status);

                if (isChecked) {
                    cardView.setCardBackgroundColor(Color.GREEN);
                } else {
                    cardView.setCardBackgroundColor(Color.RED);
                }
            }
        });
    }

    private void openAutoOnOffActivity(String portName) {
        // Start the AutoOnOffActivity and pass the portName as an extra
        Intent intent = new Intent(getActivity(), AutoOnOffActivity.class);
        intent.putExtra("portName", portName);
        startActivity(intent);
    }
}
