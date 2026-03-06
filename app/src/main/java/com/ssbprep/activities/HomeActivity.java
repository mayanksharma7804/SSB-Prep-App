package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssbprep.R;

public class HomeActivity extends AppCompatActivity {

    private CardView screeningBlock;
    private CardView groundTasksBlock;
    private CardView psychologyBlock;
    private CardView interviewBlock;
    private ImageButton logoutButton;
    private TextView welcomeText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Initialize views
        screeningBlock = findViewById(R.id.screeningBlock);
        groundTasksBlock = findViewById(R.id.groundTasksBlock);
        psychologyBlock = findViewById(R.id.psychologyBlock);
        interviewBlock = findViewById(R.id.interviewBlock);
        logoutButton = findViewById(R.id.logoutButton);
        welcomeText = findViewById(R.id.welcomeText);

        // Display welcome message
<<<<<<< HEAD
        SharedPreferences sharedPreferences = getSharedPreferences("SSBPrepPref", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Candidate");
        welcomeText.setText("Hello, " + username);
=======
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            if (name == null || name.isEmpty()) {
                name = currentUser.getEmail();
            }
            welcomeText.setText("Hello, " + (name != null ? name : "Candidate"));
        } else {
            welcomeText.setText("Hello, Candidate");
        }
>>>>>>> 467c327 (Authentication)

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
        auth.signOut();
        
        // Also sign out from Google to allow account selection next time
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
