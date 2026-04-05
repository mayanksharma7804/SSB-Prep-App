package com.ssbprep.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.ssbprep.R;
import java.util.Calendar;

public class ScheduleInterviewActivity extends AppCompatActivity {

    private ScrollView step1Info;
    private ScrollView step2Form;
    private CalendarView calendarView;
    private EditText nameField, emailField;
    private RadioGroup timeSlotGroup;
    private Spinner entryTypeSpinner;
    private long selectedDate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_interview);

        // Initialize Views
        step1Info = findViewById(R.id.step1_info);
        step2Form = findViewById(R.id.step2_form);
        calendarView = findViewById(R.id.calendarView);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        timeSlotGroup = findViewById(R.id.timeSlotGroup);
        entryTypeSpinner = findViewById(R.id.entryTypeSpinner);
        MaterialButton nextButtonStep1 = findViewById(R.id.nextButtonStep1);
        MaterialButton payButton = findViewById(R.id.nextButtonStep2);
        ImageButton backButton = findViewById(R.id.backButton);

        // Back Navigation
        backButton.setOnClickListener(v -> {
            if (step2Form.getVisibility() == View.VISIBLE) {
                step2Form.setVisibility(View.GONE);
                step1Info.setVisibility(View.VISIBLE);
            } else {
                finish();
            }
        });

        // Step 1 -> Step 2
        nextButtonStep1.setOnClickListener(v -> {
            step1Info.setVisibility(View.GONE);
            step2Form.setVisibility(View.VISIBLE);
        });

        // Setup Entry Type Spinner
        String[] entries = {"NDA", "CDSE", "AFCAT", "TES", "TGC", "NCC Special Entry", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, entries);
        entryTypeSpinner.setAdapter(adapter);

        // Calendar Constraint (Mon-Fri)
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                Toast.makeText(this, "Please select a weekday (Mon-Fri). Weekends are off.", Toast.LENGTH_SHORT).show();
                // Optionally reset the selection visually if needed
                selectedDate = 0;
            } else {
                selectedDate = cal.getTimeInMillis();
            }
        });

        // Final Payment Action
        payButton.setOnClickListener(v -> {
            if (validateForm()) {
                String time = ((RadioButton) findViewById(timeSlotGroup.getCheckedRadioButtonId())).getText().toString();
                String entry = entryTypeSpinner.getSelectedItem().toString();
                
                String message = "Booking confirmed for " + nameField.getText().toString() + 
                                 "\nEntry: " + entry + "\nTime: " + time + 
                                 "\nRedirecting to Payment Gateway (₹299)...";
                
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                // Future: Integrate actual UPI/Payment SDK
            }
        });
    }

    private boolean validateForm() {
        if (nameField.getText().toString().isEmpty()) {
            nameField.setError("Name required");
            return false;
        }
        if (emailField.getText().toString().isEmpty()) {
            emailField.setError("Email required");
            return false;
        }
        if (selectedDate == 0) {
            Toast.makeText(this, "Please select a valid date (Mon-Fri)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (timeSlotGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a time slot (9PM or 11PM)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
