/*
 * ════════════════════════════════════════════════════
 *  AUSkillBridge — Peer Skill Exchange for AU Students
 *  Air University Islamabad
 *  Course  : Mobile Application Development
 *  Instructor: Dr. Adnan Aslam
 * ────────────────────────────────────────────────────
 *  Team Members:
 *    • M Ehtisham Amjad   (231996)
 *    • M Abdullah         (232052)
 *    • Aman Mir           (233002)
 * ════════════════════════════════════════════════════
 *  MentorDetailActivity.java — Detailed profile page of a mentor with favorite and request triggers
 */

package com.auiskillbridge;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auiskillbridge.adapters.SkillListAdapter;
import com.auiskillbridge.models.Mentor;
import com.auiskillbridge.models.Notification;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.DataSource;
import com.auiskillbridge.utils.PreferencesManager;

/**
 * Activity showing detailed mentor profiles.
 * Features:
 * - Details display (initials, online indicator, stats, biography)
 * - SharedPreferences reading/writing to toggle favorites (Core Concept #3)
 * - Dynamic height adjustments for Nested ListView rows (Core Concept #2)
 * - Connection request confirmations with alerts and dynamic notification insertion (Core Concept #3)
 */
public class MentorDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView avatarText;
    private View onlineDot;
    private ImageView btnFavorite;
    private TextView nameText;
    private TextView rollText;
    private TextView deptBadge;
    private TextView onlineStatusText;
    private TextView ratingText;
    private TextView reviewsText;
    private TextView bioText;
    private ListView skillsListView;
    private TextView btnSendRequest;

    private Mentor mentor;
    private PreferencesManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);

        btnBack = findViewById(R.id.btn_back);
        avatarText = findViewById(R.id.detail_avatar);
        onlineDot = findViewById(R.id.online_dot);
        btnFavorite = findViewById(R.id.btn_favorite);
        nameText = findViewById(R.id.detail_name);
        rollText = findViewById(R.id.detail_roll);
        deptBadge = findViewById(R.id.detail_dept_badge);
        onlineStatusText = findViewById(R.id.detail_online_status);
        ratingText = findViewById(R.id.detail_rating);
        reviewsText = findViewById(R.id.detail_reviews);
        bioText = findViewById(R.id.detail_bio);
        skillsListView = findViewById(R.id.skills_listview);
        btnSendRequest = findViewById(R.id.btn_send_request);

        prefs = PreferencesManager.getInstance(this);

        // Fetch Mentor ID from Intent
        String mentorId = getIntent().getStringExtra(Constants.EXTRA_MENTOR_ID);
        if (mentorId == null || mentorId.isEmpty()) {
            Toast.makeText(this, "Mentor not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Find Mentor
        for (Mentor m : DataSource.getMentors()) {
            if (m.getId().equals(mentorId)) {
                mentor = m;
                break;
            }
        }

        if (mentor == null) {
            Toast.makeText(this, "Mentor not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Bind data fields
        bindMentorData();

        // Listeners
        btnBack.setOnClickListener(v -> finish());

        btnFavorite.setOnClickListener(v -> toggleFavorite());

        btnSendRequest.setOnClickListener(v -> showConnectionRequestDialog());
    }

    private void bindMentorData() {
        nameText.setText(mentor.getName());
        rollText.setText("Roll Number: " + mentor.getRollNumber());
        deptBadge.setText(mentor.getDepartment());
        bioText.setText(mentor.getBio());

        // Rating and reviews count
        ratingText.setText("⭐ " + String.format("%.1f", mentor.getRating()));
        reviewsText.setText("(" + mentor.getReviewCount() + " reviews)");

        // Online status
        if (mentor.isOnline()) {
            onlineDot.setVisibility(View.VISIBLE);
            GradientDrawable dotBg = (GradientDrawable) onlineDot.getBackground().mutate();
            dotBg.setColor(Color.parseColor("#00E5A0"));
            onlineStatusText.setText("Online Now");
            onlineStatusText.setTextColor(Color.parseColor("#00E5A0"));
        } else {
            onlineDot.setVisibility(View.GONE);
            onlineStatusText.setText("Offline");
            onlineStatusText.setTextColor(Color.parseColor("#8A8A9A"));
        }

        // Avatar
        avatarText.setText(mentor.getInitials());
        GradientDrawable avatarBg = (GradientDrawable) avatarText.getBackground().mutate();
        try {
            avatarBg.setColor(Color.parseColor(mentor.getAvatarColor()));
        } catch (Exception e) {
            avatarBg.setColor(Color.parseColor("#7C6FFF"));
        }

        // Favorite Heart state
        updateFavoriteIcon();

        // Skills ListView binding
        SkillListAdapter skillsAdapter = new SkillListAdapter(this, mentor.getSkills());
        skillsListView.setAdapter(skillsAdapter);
        adjustListViewHeight();
    }

    private void toggleFavorite() {
        if (prefs.isFavorite(mentor.getId())) {
            prefs.removeFavorite(mentor.getId());
            Toast.makeText(this, "Removed from saved mentors", Toast.LENGTH_SHORT).show();
        } else {
            prefs.addFavorite(mentor.getId());
            Toast.makeText(this, "Saved to your mentors list", Toast.LENGTH_SHORT).show();
        }
        AnimationUtils.bounceHeart(btnFavorite);
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        if (prefs.isFavorite(mentor.getId())) {
            btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    private void showConnectionRequestDialog() {
        // Find first skill as pre-fill suggestion
        String firstSkill = (mentor.getSkills() != null && !mentor.getSkills().isEmpty())
                ? mentor.getSkills().get(0) : "General Guidance";

        new AlertDialog.Builder(this, R.style.AUDialog)
                .setTitle("Connect with " + mentor.getName() + " 🤝")
                .setMessage("Would you like to send a connection request to " + mentor.getName() + " for help with \"" + firstSkill + "\"?\n\nThey will receive your roll number (" + prefs.getUserProfile().getRollNumber() + ") and contact information.")
                .setPositiveButton("Send Request", (dialog, which) -> {
                    // Log dynamic notification
                    Notification notif = new Notification(
                            "n_custom_" + System.currentTimeMillis(),
                            "Connection request sent to " + mentor.getName() + " for " + firstSkill + ".",
                            "Just now",
                            false
                    );
                    DataSource.addNotification(notif);

                    Toast.makeText(this, "Request sent successfully! Connect email dispatched.", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Programmatic adjustment of ListView height inside ScrollView.
     */
    private void adjustListViewHeight() {
        if (skillsListView.getAdapter() == null) return;
        int totalHeight = 0;
        int count = skillsListView.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            View listItem = skillsListView.getAdapter().getView(i, null, skillsListView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = skillsListView.getLayoutParams();
        params.height = totalHeight + (skillsListView.getDividerHeight() * (count - 1));
        skillsListView.setLayoutParams(params);
        skillsListView.requestLayout();
    }
}
