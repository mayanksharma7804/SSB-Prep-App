package com.ssbprep.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ssbprep.R;

public class PsychologyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology);

        // Initialize all psychology blocks
        Button tatButton = findViewById(R.id.tatButton);
        Button watButton = findViewById(R.id.watButton);
        Button srtButton = findViewById(R.id.srtButton);
        Button sdtButton = findViewById(R.id.sdtButton);
        Button backButton = findViewById(R.id.backButton);

        // Set click listeners
        tatButton.setOnClickListener(v -> handleBlockClick("TAT (Thematic Apperception Test)"));
        watButton.setOnClickListener(v -> handleBlockClick("WAT (Word Association Test)"));
        srtButton.setOnClickListener(v -> handleBlockClick("SRT (Situation Reaction Test)"));
        sdtButton.setOnClickListener(v -> handleBlockClick("SDT (Self Description Test)"));
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void handleBlockClick(String blockName) {
        Toast.makeText(this, blockName + " selected!", Toast.LENGTH_SHORT).show();
        // TODO: Navigate to block details screen
    }
}
