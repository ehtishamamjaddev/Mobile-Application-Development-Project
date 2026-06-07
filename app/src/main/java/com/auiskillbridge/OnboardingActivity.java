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
 *  OnboardingActivity.java — 3-slide onboarding with ViewPager2
 */

package com.auiskillbridge;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.auiskillbridge.utils.PreferencesManager;

/**
 * Onboarding activity shown on first launch only.
 * 3 AU-specific slides with ViewPager2, dot indicators, Skip and Next/Get Started buttons.
 * Onboarding flag saved to SharedPreferences so it's shown only once.
 */
public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private LinearLayout dotContainer;
    private TextView btnSkip, btnNext;

    private final String[] titles = {
            "Built for AU Students 🎓",
            "Offer Your Skills 💡",
            "Find Your Mentor 🔍"
    };

    private final String[] descriptions = {
            "Struggling with your DSA assignment or CAD project? Your senior batchmate probably knows exactly how to help.",
            "Good at Flutter? Strong in MATLAB? List your skills and help fellow AU students while building your own profile.",
            "Browse students by department, semester, and skill. Connect. Learn. Grow — together at Air University."
    };

    private final String[] icons = {"🎓", "💡", "🔍"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.onboarding_viewpager);
        dotContainer = findViewById(R.id.dot_container);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        viewPager.setAdapter(new OnboardingAdapter());
        setupDots(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setupDots(position);
                if (position == titles.length - 1) {
                    btnNext.setText(R.string.get_started);
                } else {
                    btnNext.setText(R.string.next);
                }
            }
        });

        btnSkip.setOnClickListener(v -> finishOnboarding());

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < titles.length - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                finishOnboarding();
            }
        });
    }

    private void finishOnboarding() {
        // SharedPreferences: marking onboarding as complete (Core Concept #3)
        PreferencesManager.getInstance(this).setOnboardingDone();

        // Set default user profile if not logged in
        if (!PreferencesManager.getInstance(this).isLoggedIn()) {
            com.auiskillbridge.models.UserProfile defaultProfile = new com.auiskillbridge.models.UserProfile();
            defaultProfile.setName("AU Student");
            defaultProfile.setRollNumber("000000");
            defaultProfile.setDepartment("Computer Science");
            defaultProfile.setSemester("5th Semester");
            defaultProfile.setBio("Hello! I'm an AU student looking to connect and learn.");
            defaultProfile.setAvatarColor("#7C6FFF");
            defaultProfile.setDarkMode(true);
            // SharedPreferences: saving default user profile (Core Concept #3)
            PreferencesManager.getInstance(this).saveUserProfile(defaultProfile);
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * Creates dot indicators for the current page.
     */
    private void setupDots(int currentPage) {
        dotContainer.removeAllViews();
        for (int i = 0; i < titles.length; i++) {
            View dot = new View(this);
            int size = (int) (8 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            int margin = (int) (4 * getResources().getDisplayMetrics().density);
            params.setMargins(margin, 0, margin, 0);
            dot.setLayoutParams(params);
            dot.setBackgroundResource(R.drawable.bg_avatar_circle);

            if (i == currentPage) {
                dot.getBackground().setTint(Color.parseColor("#7C6FFF"));
            } else {
                dot.getBackground().setTint(Color.parseColor("#2A2A3A"));
            }
            dotContainer.addView(dot);
        }
    }

    /**
     * ViewPager2 adapter for onboarding slides.
     */
    private class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.SlideViewHolder> {

        @NonNull
        @Override
        public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_onboarding_slide, parent, false);
            return new SlideViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
            holder.icon.setText(icons[position]);
            holder.title.setText(titles[position]);
            holder.description.setText(descriptions[position]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        class SlideViewHolder extends RecyclerView.ViewHolder {
            TextView icon, title, description;

            SlideViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.slide_icon);
                title = itemView.findViewById(R.id.slide_title);
                description = itemView.findViewById(R.id.slide_description);
            }
        }
    }
}
