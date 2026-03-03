package com.ssbprep.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ssbprep.R;

public class InterviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        // Initialize all interview blocks
        Button introButton = findViewById(R.id.introButton);
        Button tipsButton = findViewById(R.id.tipsButton);
        Button questionsButton = findViewById(R.id.questionsButton);
        Button ciqButton = findViewById(R.id.ciqButton);
        Button backButton = findViewById(R.id.backButton);

        // Set click listeners
        introButton.setOnClickListener(v -> handleBlockClick("Introduction"));
        tipsButton.setOnClickListener(v -> handleBlockClick("Tips and Techniques"));
        questionsButton.setOnClickListener(v -> handleBlockClick("Questions Asked"));
        ciqButton.setOnClickListener(v -> handleBlockClick("CIQ's (Combined Interview Questionnaire)"));
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void handleBlockClick(String blockName) {
        Toast.makeText(this, blockName + " selected!", Toast.LENGTH_SHORT).show();
        // TODO: Navigate to block details screen
    }
}
