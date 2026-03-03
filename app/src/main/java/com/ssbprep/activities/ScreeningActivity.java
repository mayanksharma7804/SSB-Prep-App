package com.ssbprep.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ssbprep.R;

public class ScreeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        // Initialize all screening blocks
        Button ppdtgdButton = findViewById(R.id.ppdtgdButton);
        Button oirButton = findViewById(R.id.oirButton);
        Button backButton = findViewById(R.id.backButton);

        // Set click listeners
        ppdtgdButton.setOnClickListener(v -> handleBlockClick("PPDT and GD"));
        oirButton.setOnClickListener(v -> handleBlockClick("OIR (Officer Intelligence Rating)"));
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void handleBlockClick(String blockName) {
        Toast.makeText(this, blockName + " selected!", Toast.LENGTH_SHORT).show();
        // TODO: Navigate to block details screen
    }
}
