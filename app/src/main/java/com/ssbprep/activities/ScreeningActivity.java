package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.ssbprep.R;

public class ScreeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        CardView oirButton = findViewById(R.id.oirButton);
        CardView ppdtButton = findViewById(R.id.ppdtButton);

        oirButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComingSoonActivity.class);
            intent.putExtra("title", "OIR Tests");
            startActivity(intent);
        });

        ppdtButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComingSoonActivity.class);
            intent.putExtra("title", "PPDT Preparation");
            startActivity(intent);
        });
    }
}
