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
 *  SplashActivity.java — Animated splash screen with onboarding check
 */

package com.auiskillbridge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.PreferencesManager;

/**
 * Splash screen shown on app launch.
 * Displays the AUSkillBridge logo with scale+fade animation.
 * Checks if onboarding has been completed to decide where to navigate next.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_logo);
        TextView appName = findViewById(R.id.splash_app_name);
        TextView tagline = findViewById(R.id.splash_tagline);

        // Splash animation: logo scale 0.6→1.0 with fade, 500ms
        AnimationUtils.animateSplash(logo, appName);

        // Tagline fade in
        tagline.setAlpha(0f);
        tagline.animate()
                .alpha(1f)
                .setDuration(400)
                .setStartDelay(500)
                .start();

        // SharedPreferences: checking onboarding flag to decide navigation (Core Concept #3)
        PreferencesManager prefs = PreferencesManager.getInstance(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            // SharedPreferences: reading onboarding completion state (Core Concept #3)
            if (!prefs.isOnboardingDone()) {
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, Constants.SPLASH_DURATION_MS);
    }
}
