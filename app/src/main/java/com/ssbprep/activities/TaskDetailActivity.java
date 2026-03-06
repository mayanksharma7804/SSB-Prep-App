package com.ssbprep.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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

        if (title != null) titleView.setText(title);
        if (description != null) descView.setText(description);
        if (tips != null) tipsView.setText(tips);

        backButton.setOnClickListener(v -> finish());
    }
}
