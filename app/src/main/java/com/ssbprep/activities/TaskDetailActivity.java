package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.ssbprep.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String tips = getIntent().getStringExtra("tips");

        TextView titleView = findViewById(R.id.taskTitle);
        TextView descView = findViewById(R.id.taskDescription);
        TextView tipsView = findViewById(R.id.taskTips);
        ImageButton backButton = findViewById(R.id.backButton);
        MaterialButton practiceButton = findViewById(R.id.practiceButton);

        if (title != null) titleView.setText(title);
        if (description != null) descView.setText(description);
        if (tips != null) tipsView.setText(tips);

        if (title != null) {
            if (title.contains("Thematic Apperception")) {
                practiceButton.setVisibility(View.VISIBLE);
                practiceButton.setOnClickListener(v -> {
                    startActivity(new Intent(this, TATListActivity.class));
                });
            } else if (title.contains("Word Association")) {
                practiceButton.setVisibility(View.VISIBLE);
                practiceButton.setOnClickListener(v -> {
                    startActivity(new Intent(this, WATListActivity.class));
                });
            }
        }

        backButton.setOnClickListener(v -> finish());
    }
}
