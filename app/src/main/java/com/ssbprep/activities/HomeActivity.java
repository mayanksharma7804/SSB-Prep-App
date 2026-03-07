package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssbprep.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardView screeningBlock, groundTasksBlock, psychologyBlock, interviewBlock;
    private ImageButton logoutButton;
    private TextView welcomeText;
    private CircleImageView profileAvatar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        // Initialize UI
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        screeningBlock = findViewById(R.id.screeningBlock);
        groundTasksBlock = findViewById(R.id.groundTasksBlock);
        psychologyBlock = findViewById(R.id.psychologyBlock);
        interviewBlock = findViewById(R.id.interviewBlock);
        logoutButton = findViewById(R.id.logoutButton);
        welcomeText = findViewById(R.id.welcomeText);
        profileAvatar = findViewById(R.id.profileAvatar);

        setupDrawer();

        // Open Drawer
        profileAvatar.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Block Listeners
        screeningBlock.setOnClickListener(v -> handleBlockClick("Screening"));
        groundTasksBlock.setOnClickListener(v -> handleBlockClick("Ground Tasks"));
        psychologyBlock.setOnClickListener(v -> handleBlockClick("Psychology"));
        interviewBlock.setOnClickListener(v -> handleBlockClick("Interview"));

        logoutButton.setOnClickListener(v -> handleLogout());
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshUserInfo();
    }

    private void refreshUserInfo() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Refresh the user to get latest profile updates
            currentUser.reload().addOnCompleteListener(task -> {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    updateUI(user);
                }
            });
        }
    }

    private void updateUI(FirebaseUser user) {
        String name = user.getDisplayName();
        if (name == null || name.isEmpty()) {
            name = user.getEmail();
        }
        welcomeText.setText("Hello, " + (name != null ? name : "Candidate"));

        Glide.with(this)
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .circleCrop()
                .into(profileAvatar);

        // Update Drawer Header as well
        View headerView = navigationView.getHeaderView(0);
        CircleImageView headerImage = headerView.findViewById(R.id.navHeaderImage);
        TextView headerName = headerView.findViewById(R.id.navHeaderName);
        TextView headerEmail = headerView.findViewById(R.id.navHeaderEmail);

        headerName.setText(user.getDisplayName() != null ? user.getDisplayName() : "Candidate");
        headerEmail.setText(user.getEmail());
        Glide.with(this)
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .circleCrop()
                .into(headerImage);
    }

    private void setupDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileSettingsActivity.class));
            } else if (id == R.id.nav_personal_info) {
                startActivity(new Intent(this, PersonalInfoActivity.class));
            } else if (id == R.id.nav_subscription) {
                // TODO: Open Subscription screen
                Toast.makeText(this, "Subscription Details Coming Soon", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_settings) {
                // TODO: Open App Settings screen
                Toast.makeText(this, "App Settings Coming Soon", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_support) {
                Toast.makeText(this, "Support: mayankews.chanakya1@gmail.com", Toast.LENGTH_LONG).show();
            } else if (id == R.id.nav_delete_account) {
                showDeleteConfirmation();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure? This will permanently delete your data and cannot be undone.")
            .setPositiveButton("Delete", (dialog, which) -> {
                auth.getCurrentUser().delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show();
                        handleLogout();
                    } else {
                        Toast.makeText(this, "Re-authentication required to delete account.", Toast.LENGTH_LONG).show();
                    }
                });
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void handleBlockClick(String blockName) {
        Intent intent = null;
        switch (blockName) {
            case "Screening": intent = new Intent(this, ScreeningActivity.class); break;
            case "Ground Tasks": intent = new Intent(this, GroundTasksActivity.class); break;
            case "Psychology": intent = new Intent(this, PsychologyActivity.class); break;
            case "Interview": intent = new Intent(this, InterviewActivity.class); break;
        }
        if (intent != null) startActivity(intent);
    }

    private void handleLogout() {
        auth.signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener(task -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
