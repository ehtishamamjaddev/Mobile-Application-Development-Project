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
 *  AnimationUtils.java — Reusable animation helpers for AU Clarity UI
 */

package com.auiskillbridge.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Utility class providing reusable animation methods for the AUSkillBridge UI.
 * Includes RecyclerView item animations, FAB pulse, heart bounce, and splash effects.
 */
public class AnimationUtils {

    /**
     * Animates a RecyclerView item with slide-up + fade-in effect.
     * Used for staggered entrance animations on mentor cards and request cards.
     *
     * @param itemView The view to animate
     * @param position The adapter position (used for stagger delay)
     */
    public static void animateRecyclerViewItem(View itemView, int position) {
        itemView.setAlpha(0f);
        itemView.setTranslationY(40f);

        itemView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(250)
                .setStartDelay((long) position * 50)
                .setInterpolator(new DecelerateInterpolator(1.8f))
                .start();
    }

    /**
     * Creates a pulse animation for the FAB button.
     * Scales from 1.0 → 1.06 → 1.0 every 3 seconds when idle.
     *
     * @param fab The FloatingActionButton view
     */
    public static void startFabPulse(View fab) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(fab, "scaleX", 1f, 1.06f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(fab, "scaleY", 1f, 1.06f, 1f);

        AnimatorSet pulseSet = new AnimatorSet();
        pulseSet.playTogether(scaleX, scaleY);
        pulseSet.setDuration(1200);

        // Repeat the pulse
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.RESTART);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.RESTART);

        // Add initial delay between pulses
        scaleX.setStartDelay(3000);
        scaleY.setStartDelay(3000);

        pulseSet.start();
    }

    /**
     * Bounces the heart/favorite icon on tap.
     * Scale: 1.0 → 1.3 → 1.0 with overshoot.
     *
     * @param heartView The heart icon view
     */
    public static void bounceHeart(View heartView) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(heartView, "scaleX", 1f, 1.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(heartView, "scaleY", 1f, 1.3f, 1f);

        AnimatorSet bounceSet = new AnimatorSet();
        bounceSet.playTogether(scaleX, scaleY);
        bounceSet.setDuration(150);
        bounceSet.setInterpolator(new OvershootInterpolator(2.5f));
        bounceSet.start();
    }

    /**
     * Splash screen logo animation: scale 0.6 → 1.0 with fade-in.
     *
     * @param logoView The logo view
     * @param textView The app name text view
     */
    public static void animateSplash(View logoView, View textView) {
        // Logo scale + fade
        logoView.setScaleX(0.6f);
        logoView.setScaleY(0.6f);
        logoView.setAlpha(0f);

        logoView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // Text fade in with slight delay
        textView.setAlpha(0f);
        textView.setTranslationY(20f);

        textView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    /**
     * Fade-in animation for fragment transitions.
     *
     * @param view The fragment root view
     */
    public static void fadeIn(View view) {
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setDuration(140) // 140ms
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    /**
     * Slide up animation for bottom sheets and dialogs.
     *
     * @param view The view to slide up
     */
    public static void slideUp(View view) {
        view.setTranslationY(view.getHeight());
        view.setAlpha(0f);

        view.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(240)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}
