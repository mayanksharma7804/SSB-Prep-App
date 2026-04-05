package com.ssbprep.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ssbprep.R;
import com.ssbprep.adapters.NewsAdapter;
import com.ssbprep.data.Article;
import com.ssbprep.data.NewsApiService;
import com.ssbprep.data.NewsResponse;
import com.ssbprep.utils.SSBCountdownManager;
import com.ssbprep.utils.ThemeManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardView reminderBlock;
    private CardView countdownBlock, addCountdownBlock;
    private ImageButton themeToggleButton;
    private TextView userNameText, reminderDateTime, daysToGoText, ssbNameLabel;
    private CircleImageView profileAvatar;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    // News Section
    private NewsAdapter newsAdapter;
    private NewsApiService newsApiService;
    private final String NEWS_API_KEY = "aa6bb98039f7474fa22a95c046d4c715";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        CardView procedureBlock = findViewById(R.id.procedureBlock);
        CardView screeningBlock = findViewById(R.id.screeningBlock);
        CardView groundTasksBlock = findViewById(R.id.groundTasksBlock);
        CardView psychologyBlock = findViewById(R.id.psychologyBlock);
        CardView interviewBlock = findViewById(R.id.interviewBlock);
        CardView knowledgeBlock = findViewById(R.id.knowledgeBlock);
        CardView olqBlock = findViewById(R.id.olqBlock);
        reminderBlock = findViewById(R.id.reminderBlock);
        reminderDateTime = findViewById(R.id.reminderDateTime);
        
        countdownBlock = findViewById(R.id.countdownBlock);
        addCountdownBlock = findViewById(R.id.addCountdownBlock);
        daysToGoText = findViewById(R.id.daysToGoText);
        ssbNameLabel = findViewById(R.id.ssbNameLabel);

        ImageButton logoutButton = findViewById(R.id.logoutButton);
        themeToggleButton = findViewById(R.id.themeToggleButton);
        userNameText = findViewById(R.id.userNameText);
        profileAvatar = findViewById(R.id.profileAvatar);

        // News UI
        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(new ArrayList<>());
        newsRecyclerView.setAdapter(newsAdapter);
        TextView viewAllNews = findViewById(R.id.viewAllNews);

        setupRetrofit();
        setupDrawer();

        profileAvatar.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        procedureBlock.setOnClickListener(v -> handleBlockClick("Procedure"));
        screeningBlock.setOnClickListener(v -> handleBlockClick("Screening"));
        groundTasksBlock.setOnClickListener(v -> handleBlockClick("Ground Tasks"));
        psychologyBlock.setOnClickListener(v -> handleBlockClick("Psychology"));
        interviewBlock.setOnClickListener(v -> handleBlockClick("Interview"));
        knowledgeBlock.setOnClickListener(v -> handleBlockClick("Knowledge"));
        olqBlock.setOnClickListener(v -> handleBlockClick("OLQ"));

        addCountdownBlock.setOnClickListener(v -> showAddCountdownDialog());
        countdownBlock.setOnClickListener(v -> startActivity(new Intent(this, SSBCountdownDetailsActivity.class)));

        logoutButton.setOnClickListener(v -> showLogoutConfirmation());

        updateThemeIcon();
        themeToggleButton.setOnClickListener(v -> ThemeManager.INSTANCE.toggleThemeWithAnimation(this, v));
        ThemeManager.INSTANCE.checkAndAnimate(this);

        viewAllNews.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NewsActivity.class)));

        fetchNews();
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsApiService = retrofit.create(NewsApiService.class);
    }

    private void fetchNews() {
        // Optimized for Indian context and defense
        String query = "Indian Defence OR \"Indian Army\" OR \"Indian Air Force\" OR \"Indian Navy\"";
        String domains = "timesofindia.indiatimes.com,thehindu.com,indianexpress.com";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7); // Search last 7 days for better reliability
        String fromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime());

        newsApiService.getEverything(query, fromDate, "publishedAt", "en", NEWS_API_KEY, domains)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (isFinishing() || isDestroyed()) return;
                        if (response.isSuccessful() && response.body() != null) {
                            List<Article> articles = response.body().getArticles();
                            if (articles.size() > 2) {
                                newsAdapter.updateData(articles.subList(0, 2));
                            } else {
                                newsAdapter.updateData(articles);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) { }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshUserInfo();
        fetchInterviewReminder();
        updateCountdownUI();
    }

    private void updateCountdownUI() {
        long timestamp = SSBCountdownManager.INSTANCE.getSSBDate(this);
        if (timestamp > 0) {
            String name = SSBCountdownManager.INSTANCE.getSSBName(this);
            int days = SSBCountdownManager.INSTANCE.getDaysRemaining(timestamp);
            
            ssbNameLabel.setText(name != null ? name.toUpperCase(Locale.ROOT) : "IMPORTANT DATE");
            if (days > 0) {
                daysToGoText.setText(String.format(Locale.getDefault(), "%d Days to go", days));
            } else if (days == 0) {
                daysToGoText.setText("Today is the day!");
            } else {
                daysToGoText.setText("Event Passed");
            }
            
            countdownBlock.setVisibility(View.VISIBLE);
            addCountdownBlock.setVisibility(View.GONE);
        } else {
            countdownBlock.setVisibility(View.GONE);
            addCountdownBlock.setVisibility(View.VISIBLE);
        }
    }

    private void showAddCountdownDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 40, 60, 10);

        final TextInputLayout nameLayout = new TextInputLayout(this, null, com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox_ExposedDropdownMenu);
        nameLayout.setHint("What is this for?");
        final AutoCompleteTextView nameInput = new AutoCompleteTextView(nameLayout.getContext());
        String[] suggestions = {"NDA Exam", "CDS Exam", "AFCAT Exam", "NDA SSB", "CDS SSB", "AFCAT SSB", "TES SSB", "UES SSB", "TGC SSB"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        nameInput.setAdapter(adapter);
        nameLayout.addView(nameInput);
        layout.addView(nameLayout);

        final TextInputLayout dateLayout = new TextInputLayout(this, null, com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);
        dateLayout.setHint("Select Date");
        dateLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        dateLayout.setEndIconDrawable(R.drawable.baseline_menu_book_24); 
        final EditText dateInput = new EditText(dateLayout.getContext());
        dateInput.setFocusable(false);
        dateInput.setClickable(true);
        dateLayout.addView(dateInput);
        layout.addView(dateLayout);

        final Calendar calendar = Calendar.getInstance();
        View.OnClickListener datePickerListener = v -> new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            dateInput.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        dateInput.setOnClickListener(datePickerListener);
        dateLayout.setEndIconOnClickListener(datePickerListener);

        new MaterialAlertDialogBuilder(this)
            .setTitle("Add Countdown")
            .setView(layout)
            .setPositiveButton("Save", (dialog, which) -> {
                String name = nameInput.getText().toString();
                if (!name.isEmpty() && !dateInput.getText().toString().isEmpty()) {
                    SSBCountdownManager.INSTANCE.saveSSBDate(this, name, calendar.getTimeInMillis());
                    updateCountdownUI();
                    sendBroadcast(new Intent("com.ssbprep.ACTION_WIDGET_UPDATE"));
                } else {
                    Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void fetchInterviewReminder() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) return;
        db.collection("interview_bookings")
                .whereEqualTo("userId", user.getUid())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isFinishing() || isDestroyed()) return;
                    if (!queryDocumentSnapshots.isEmpty()) {
                        String date = queryDocumentSnapshots.getDocuments().get(0).getString("date");
                        String time = queryDocumentSnapshots.getDocuments().get(0).getString("time");
                        reminderDateTime.setText(String.format("%s at %s", date, time));
                        reminderBlock.setVisibility(View.VISIBLE);
                    } else {
                        reminderBlock.setVisibility(View.GONE);
                    }
                });
    }

    private void handleBlockClick(String blockName) {
        Intent intent;
        switch (blockName) {
            case "Procedure":
                intent = new Intent(this, ProcedureActivity.class);
                break;
            case "Screening":
                intent = new Intent(this, ScreeningActivity.class);
                break;
            case "Ground Tasks":
                intent = new Intent(this, GroundTasksActivity.class);
                break;
            case "Psychology":
                intent = new Intent(this, PsychologyActivity.class);
                break;
            case "Interview":
                intent = new Intent(this, InterviewActivity.class);
                break;
            case "Knowledge":
                intent = new Intent(this, ForcesKnowledgeActivity.class);
                break;
            case "OLQ":
                intent = new Intent(this, OLQAssessmentActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    private void setupDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileSettingsActivity.class));
            } else if (id == R.id.nav_personal_info) {
                startActivity(new Intent(this, PersonalInfoActivity.class));
            } else if (id == R.id.nav_settings) {
                Intent intent = new Intent(this, ComingSoonActivity.class);
                intent.putExtra("title", "App Settings");
                startActivity(intent);
            } else if (id == R.id.nav_support) {
                Intent intent = new Intent(this, ComingSoonActivity.class);
                intent.putExtra("title", "Support");
                startActivity(intent);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void refreshUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            if (name != null && !name.isEmpty()) {
                String firstName = name.split(" ")[0];
                userNameText.setText(firstName);
            } else {
                String email = user.getEmail();
                if (email != null && email.contains("@")) {
                    userNameText.setText(email.split("@")[0]);
                } else {
                    userNameText.setText("Soldier");
                }
            }
            
            // Update Drawer Header
            View headerView = navigationView.getHeaderView(0);
            if (headerView != null) {
                TextView navName = headerView.findViewById(R.id.navHeaderName);
                TextView navEmail = headerView.findViewById(R.id.navHeaderEmail);
                CircleImageView navImage = headerView.findViewById(R.id.navHeaderImage);
                
                if (navName != null) navName.setText(user.getDisplayName());
                if (navEmail != null) navEmail.setText(user.getEmail());
                
                if (user.getPhotoUrl() != null && navImage != null) {
                    if (!isFinishing() && !isDestroyed()) {
                        Glide.with(this)
                            .load(user.getPhotoUrl())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(navImage);
                    }
                }
            }
            
            if (user.getPhotoUrl() != null) {
                if (!isFinishing() && !isDestroyed()) {
                    // Bypass cache to show latest image
                    Glide.with(this)
                        .load(user.getPhotoUrl())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(profileAvatar);
                }
            }
        }
    }

    private void showLogoutConfirmation() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    auth.signOut();
                    GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                            .signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateThemeIcon() {
        if (ThemeManager.INSTANCE.isDarkMode(this)) {
            themeToggleButton.setImageResource(R.drawable.ic_sun);
        } else {
            themeToggleButton.setImageResource(R.drawable.ic_moon);
        }
    }
}
