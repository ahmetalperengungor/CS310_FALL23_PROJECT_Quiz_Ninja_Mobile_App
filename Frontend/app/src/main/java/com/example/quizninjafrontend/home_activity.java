package com.example.quizninjafrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

public class home_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.add_button).setOnClickListener(v -> {
            // Create an Intent to open AddActivity
            Intent intent = new Intent(home_activity.this, add_activity.class);
            startActivity(intent);
        });

    }
}