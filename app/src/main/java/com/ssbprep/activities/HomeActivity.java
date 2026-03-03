package com.ssbprep.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ssbprep.R;

public class HomeActivity extends AppCompatActivity {

    private Button screeningBlock;
    private Button groundTasksBlock;
    private Button psychologyBlock;
    private Button interviewBlock;
    private Button logoutButton;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        screeningBlock = findViewById(R.id.screeningBlock);
        groundTasksBlock = findViewById(R.id.groundTasksBlock);
        psychologyBlock = findViewById(R.id.psychologyBlock);
        interviewBlock = findViewById(R.id.interviewBlock);
        logoutButton = findViewById(R.id.logoutButton);
        welcomeText = findViewById(R.id.welcomeText);

        // Display welcome message
        SharedPreferences sharedPreferences = getSharedPreferences("SSBPrepPref", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        welcomeText.setText("Welcome, " + username + "!");

        // Set click listeners for blocks
        screeningBlock.setOnClickListener(v -> handleBlockClick("Screening"));
        groundTasksBlock.setOnClickListener(v -> handleBlockClick("Ground Tasks"));
        psychologyBlock.setOnClickListener(v -> handleBlockClick("Psychology"));
        interviewBlock.setOnClickListener(v -> handleBlockClick("Interview"));

        // Logout button
        logoutButton.setOnClickListener(v -> handleLogout());
    }

    private void handleBlockClick(String blockName) {
        Intent intent = null;
        
        switch (blockName) {
            case "Screening":
                intent = new Intent(HomeActivity.this, ScreeningActivity.class);
                break;
            case "Ground Tasks":
                intent = new Intent(HomeActivity.this, GroundTasksActivity.class);
                break;
            case "Psychology":
                intent = new Intent(HomeActivity.this, PsychologyActivity.class);
                break;
            case "Interview":
                intent = new Intent(HomeActivity.this, InterviewActivity.class);
                break;
        }
        
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void handleLogout() {
        SharedPreferences sharedPreferences = getSharedPreferences("SSBPrepPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }
}
