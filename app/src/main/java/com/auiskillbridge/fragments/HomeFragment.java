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
 *  HomeFragment.java — Main dashboard with greeting, search, and mentor lists
 */

package com.auiskillbridge.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auiskillbridge.MainActivity;
import com.auiskillbridge.PostRequestActivity;
import com.auiskillbridge.R;
import com.auiskillbridge.SearchResultsActivity;
import com.auiskillbridge.adapters.FeaturedMentorAdapter;
import com.auiskillbridge.adapters.MentorCardAdapter;
import com.auiskillbridge.models.UserProfile;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.DataSource;
import com.auiskillbridge.utils.PreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Fragment showing the Home / Dashboard screen.
 * Displays greeting, search, horizontal list of featured mentors, and vertical list of all mentors.
 * Features:
 * - RecyclerView adapters for scrollable lists (Core Concept #1)
 * - Navigation redirection (Core Concept #5)
 * - Persistence lookup for user info & search history (Core Concept #3)
 * - UI micro-animations (Core Concept #4)
 */
public class HomeFragment extends Fragment {

    private TextView greetingText;
    private EditText searchBar;
    private ImageView btnBell;
    private RecyclerView featuredRv;
    private RecyclerView allMentorsRv;
    private FloatingActionButton fabPost;

    private FeaturedMentorAdapter featuredAdapter;
    private MentorCardAdapter allMentorsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        greetingText = view.findViewById(R.id.greeting_text);
        searchBar = view.findViewById(R.id.search_bar);
        btnBell = view.findViewById(R.id.btn_notification_bell);
        featuredRv = view.findViewById(R.id.featured_mentors_rv);
        allMentorsRv = view.findViewById(R.id.all_mentors_rv);
        fabPost = view.findViewById(R.id.fab_post_request);

        // Transition fade in
        AnimationUtils.fadeIn(view);

        // Set up search action
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                performSearch();
                return true;
            }
            return false;
        });

        // Set up notification bell click -> goes to Notifications tab in MainActivity
        btnBell.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTab(R.id.nav_notifications);
            }
        });

        // Post Request FAB -> launches PostRequestActivity
        fabPost.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PostRequestActivity.class);
            startActivity(intent);
        });

        // Horizontal RecyclerView: Featured Mentors
        featuredRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        featuredAdapter = new FeaturedMentorAdapter(getContext(), DataSource.getFeaturedMentors());
        featuredRv.setAdapter(featuredAdapter);

        // Vertical RecyclerView: All Mentors
        allMentorsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        allMentorsAdapter = new MentorCardAdapter(getContext(), DataSource.getMentors());
        allMentorsRv.setAdapter(allMentorsAdapter);

        // FAB pulse animation
        AnimationUtils.startFabPulse(fabPost);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update greeting based on preferences (e.g. if name was edited in Profile tab)
        UserProfile profile = PreferencesManager.getInstance(getContext()).getUserProfile();
        String name = profile.getName();
        if (name == null || name.isEmpty() || "AU Student".equals(name)) {
            greetingText.setText("Salam, AU Student! 👋");
        } else {
            // Pick first name
            String firstName = name.trim().split("\\s+")[0];
            greetingText.setText("Salam, " + firstName + "! 👋");
        }

        // Fill search bar with last search query for suggestion, but don't focus it
        String lastQuery = PreferencesManager.getInstance(getContext()).getLastSearch();
        if (!lastQuery.isEmpty()) {
            searchBar.setHint("Try searching for \"" + lastQuery + "\"...");
        }

        // Refresh adapters (e.g. if favorites were changed in DetailActivity)
        if (featuredAdapter != null) {
            featuredAdapter.notifyDataSetChanged();
        }
        if (allMentorsAdapter != null) {
            allMentorsAdapter.notifyDataSetChanged();
        }
    }

    private void performSearch() {
        String query = searchBar.getText().toString().trim();
        if (!query.isEmpty()) {
            // SharedPreferences: saving search query to history (Core Concept #3)
            PreferencesManager.getInstance(getContext()).saveLastSearch(query);

            Intent intent = new Intent(getContext(), SearchResultsActivity.class);
            intent.putExtra(Constants.EXTRA_SEARCH_QUERY, query);
            startActivity(intent);

            // Clear search text field
            searchBar.setText("");
        }
    }
}
