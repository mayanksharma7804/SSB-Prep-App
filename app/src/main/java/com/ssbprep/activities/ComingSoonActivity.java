package com.ssbprep.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ssbprep.R;

public class ComingSoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        String title = getIntent().getStringExtra("title");
        if (title != null) {
            TextView titleView = findViewById(R.id.moduleTitle);
            titleView.setText(title);
        }

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}
