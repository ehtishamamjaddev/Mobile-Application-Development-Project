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
 *  MainActivity.java — Main activity hosting the BottomNavigationView and 5 tabs
 */

package com.auiskillbridge;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.auiskillbridge.fragments.ExploreFragment;
import com.auiskillbridge.fragments.HomeFragment;
import com.auiskillbridge.fragments.NotificationsFragment;
import com.auiskillbridge.fragments.ProfileFragment;
import com.auiskillbridge.fragments.RequestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Main Activity of the application.
 * Manages the Fragment Container and BottomNavigationView (Core Concept #5: Navigation).
 * Demonstrates switching between 5 tabs with smooth fragment transitions.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private FragmentManager fragmentManager;

    private final Fragment homeFragment = new HomeFragment();
    private final Fragment exploreFragment = new ExploreFragment();
    private final Fragment requestsFragment = new RequestsFragment();
    private final Fragment profileFragment = new ProfileFragment();
    private final Fragment notificationsFragment = new NotificationsFragment();

    private Fragment activeFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        // Add all fragments to manager and hide them, keeping Home visible
        fragmentManager.beginTransaction().add(R.id.fragment_container, notificationsFragment, "5").hide(notificationsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, profileFragment, "4").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, requestsFragment, "3").hide(requestsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, exploreFragment, "2").hide(exploreFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment, "1").commit();

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;

            if (itemId == R.id.nav_home) {
                selectedFragment = homeFragment;
            } else if (itemId == R.id.nav_explore) {
                selectedFragment = exploreFragment;
            } else if (itemId == R.id.nav_requests) {
                selectedFragment = requestsFragment;
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = profileFragment;
            } else if (itemId == R.id.nav_notifications) {
                selectedFragment = notificationsFragment;
            }

            if (selectedFragment != null && selectedFragment != activeFragment) {
                // Perform smooth swap transition
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .hide(activeFragment)
                        .show(selectedFragment)
                        .commit();
                activeFragment = selectedFragment;
                return true;
            }
            return false;
        });

        // Handle possible redirection from other activities or intents (e.g. notifications)
        if (getIntent() != null && getIntent().hasExtra("navigate_to")) {
            String target = getIntent().getStringExtra("navigate_to");
            if ("notifications".equals(target)) {
                navigateToTab(R.id.nav_notifications);
            }
        }
    }

    /**
     * Programmatically navigates to a specific tab by ID.
     */
    public void navigateToTab(int menuItemId) {
        bottomNav.setSelectedItemId(menuItemId);
    }
}
