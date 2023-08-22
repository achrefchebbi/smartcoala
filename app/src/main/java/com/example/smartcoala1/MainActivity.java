package com.example.smartcoala1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.smartcoala1.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the fragment_container is null (happens in case of configuration changes)
        if (savedInstanceState == null) {
            // Load the DashboardFragment into the fragment_container
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
        }
    }
}
