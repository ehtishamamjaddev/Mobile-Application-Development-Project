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
 *  SearchResultsActivity.java — Search results list activity for search query or categories
 */

package com.auiskillbridge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auiskillbridge.adapters.SearchResultAdapter;
import com.auiskillbridge.models.Mentor;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.DataSource;

import java.util.List;

/**
 * Activity displaying search and category results.
 * Features:
 * - Query and category lookup via intent extras (Core Concept #5)
 * - Filtering logic (Core Concept #1)
 * - RecyclerView adapters (Core Concept #1)
 * - Empty states layout controls (Core Concept #1)
 */
public class SearchResultsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView searchTitle;
    private TextView resultsCount;
    private RecyclerView resultsRv;
    private View emptyStateView;

    private List<Mentor> results;
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        btnBack = findViewById(R.id.btn_back);
        searchTitle = findViewById(R.id.search_title);
        resultsCount = findViewById(R.id.results_count);
        resultsRv = findViewById(R.id.search_results_rv);
        emptyStateView = findViewById(R.id.empty_state);

        btnBack.setOnClickListener(v -> finish());

        resultsRv.setLayoutManager(new LinearLayoutManager(this));

        // Detect launch mode: Query or Category
        if (getIntent().hasExtra(Constants.EXTRA_SEARCH_QUERY)) {
            String query = getIntent().getStringExtra(Constants.EXTRA_SEARCH_QUERY);
            searchTitle.setText("Search: " + query);
            results = DataSource.searchMentors(query);
            displayResults(query);
        } else if (getIntent().hasExtra(Constants.EXTRA_CATEGORY)) {
            String category = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);
            searchTitle.setText(category);
            results = DataSource.getMentorsByCategory(category);
            displayResults(category);
        } else {
            finish();
        }
    }

    private void displayResults(String term) {
        if (results == null || results.isEmpty()) {
            resultsRv.setVisibility(View.GONE);
            emptyStateView.setVisibility(View.VISIBLE);
            resultsCount.setText(getString(R.string.no_results));

            // Populate Empty State text
            TextView emptyTitle = emptyStateView.findViewById(R.id.empty_title);
            TextView emptySubtitle = emptyStateView.findViewById(R.id.empty_subtitle);
            TextView emptyCta = emptyStateView.findViewById(R.id.empty_cta);
            ImageView emptyIcon = emptyStateView.findViewById(R.id.empty_icon);

            emptyIcon.setImageResource(R.drawable.ic_empty_mentors);
            emptyTitle.setText("No Mentors Found 🔍");
            emptySubtitle.setText("We couldn't find any mentors matching \"" + term + "\". Be the first to offer this skill by updating your profile!");

            // CTA button -> finishes activity so user can edit profile in Profile tab
            emptyCta.setText("Go Back");
            emptyCta.setOnClickListener(v -> finish());
        } else {
            resultsRv.setVisibility(View.VISIBLE);
            emptyStateView.setVisibility(View.GONE);

            String countStr = String.format(getString(R.string.mentors_found), results.size(), term);
            resultsCount.setText(countStr);

            adapter = new SearchResultAdapter(this, results);
            resultsRv.setAdapter(adapter);
        }
    }
}
