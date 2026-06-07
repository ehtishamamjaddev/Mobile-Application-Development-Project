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
 *  ExploreFragment.java — Lists the 8 skill categories using custom ListView Adapter
 */

package com.auiskillbridge.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.auiskillbridge.R;
import com.auiskillbridge.SearchResultsActivity;
import com.auiskillbridge.adapters.CategoryAdapter;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;

/**
 * Fragment showing the Explore screen.
 * Displays the 8 skill categories using a Custom ListView Adapter (Core Concept #2).
 * Tapping a category navigates to SearchResultsActivity to list matching mentors.
 */
public class ExploreFragment extends Fragment {

    private ListView categoriesList;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoriesList = view.findViewById(R.id.categories_listview);

        // Transition fade in
        AnimationUtils.fadeIn(view);

        // ListView Custom Adapter binding (Core Concept #2)
        adapter = new CategoryAdapter(getContext());
        categoriesList.setAdapter(adapter);

        // Item click listener -> search results
        categoriesList.setOnItemClickListener((parent, v, position, id) -> {
            String selectedCategory = Constants.SKILL_CATEGORIES[position];
            Intent intent = new Intent(getContext(), SearchResultsActivity.class);
            intent.putExtra(Constants.EXTRA_CATEGORY, selectedCategory);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update mentor count badge in case custom mentor was updated
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
