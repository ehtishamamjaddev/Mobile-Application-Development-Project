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
 *  RequestsFragment.java — Lists skill requests with category chips filtering
 */

package com.auiskillbridge.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auiskillbridge.R;
import com.auiskillbridge.adapters.SkillRequestAdapter;
import com.auiskillbridge.models.Notification;
import com.auiskillbridge.models.SkillRequest;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment showing the Skill Requests screen.
 * Displays a list of requests posted by students looking for help.
 * Features:
 * - Programmatic horizontal category chips (Core Concept #1)
 * - RecyclerView adapters (Core Concept #1)
 * - Filtering logic (Core Concept #1)
 * - Dynamic empty states (Core Concept #1)
 */
public class RequestsFragment extends Fragment implements SkillRequestAdapter.OnOfferHelpListener {

    private LinearLayout chipsContainer;
    private RecyclerView requestsRv;
    private View emptyStateView;

    private List<SkillRequest> allRequests;
    private List<SkillRequest> filteredRequests = new ArrayList<>();
    private SkillRequestAdapter adapter;

    private String selectedFilterLabel = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requests, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipsContainer = view.findViewById(R.id.filter_chips_container);
        requestsRv = view.findViewById(R.id.requests_rv);
        emptyStateView = view.findViewById(R.id.empty_state);

        // Transition fade in
        AnimationUtils.fadeIn(view);

        // Retrieve data source
        allRequests = DataSource.getSkillRequests();

        // Initialize RecyclerView
        requestsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SkillRequestAdapter(getContext(), filteredRequests, this);
        requestsRv.setAdapter(adapter);

        // Generate filter chips
        buildFilterChips();

        // Perform initial filter
        applyFilter();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload in case a new request was posted
        allRequests = DataSource.getSkillRequests();
        applyFilter();
    }

    /**
     * Programmatically creates and populates filter chips.
     */
    private void buildFilterChips() {
        chipsContainer.removeAllViews();
        float density = getResources().getDisplayMetrics().density;

        for (String filter : Constants.FILTER_CHIPS) {
            TextView chip = new TextView(getContext());
            chip.setText(filter);
            chip.setTextSize(12);
            chip.setGravity(Gravity.CENTER);

            // Set padding
            int hPad = (int) (14 * density);
            int vPad = (int) (8 * density);
            chip.setPadding(hPad, vPad, hPad, vPad);

            // Layout Params
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd((int) (8 * density));
            chip.setLayoutParams(params);

            // Click listener
            chip.setOnClickListener(v -> {
                selectedFilterLabel = filter;
                updateChipsSelection();
                applyFilter();
            });

            chipsContainer.addView(chip);
        }
        updateChipsSelection();
    }

    /**
     * Styles chips based on selected state.
     */
    private void updateChipsSelection() {
        for (int i = 0; i < chipsContainer.getChildCount(); i++) {
            TextView chip = (TextView) chipsContainer.getChildAt(i);
            String text = chip.getText().toString();

            if (text.equals(selectedFilterLabel)) {
                // Style as selected (violet)
                chip.setBackgroundResource(R.drawable.bg_chip_violet);
                chip.setTextColor(getResources().getColor(R.color.chip_violet_text));
            } else {
                // Style as unselected (grey/elevated)
                chip.setBackgroundResource(R.drawable.bg_chip_dept);
                chip.setTextColor(getResources().getColor(R.color.text_secondary));
            }
        }
    }

    /**
     * Filters requests and updates lists and empty states.
     */
    private void applyFilter() {
        filteredRequests.clear();
        String mappedCategory = mapChipToCategory(selectedFilterLabel);

        for (SkillRequest req : allRequests) {
            // Only show active (unresolved) requests
            if (req.isResolved()) continue;

            if ("All".equals(mappedCategory) || req.getCategory().equals(mappedCategory)) {
                filteredRequests.add(req);
            }
        }

        adapter.updateList(filteredRequests);

        // Toggle empty state
        if (filteredRequests.isEmpty()) {
            requestsRv.setVisibility(View.GONE);
            emptyStateView.setVisibility(View.VISIBLE);

            // Customize empty state text
            TextView emptyTitle = emptyStateView.findViewById(R.id.empty_title);
            TextView emptySubtitle = emptyStateView.findViewById(R.id.empty_subtitle);
            TextView emptyCta = emptyStateView.findViewById(R.id.empty_cta);
            android.widget.ImageView emptyIcon = emptyStateView.findViewById(R.id.empty_icon);

            emptyIcon.setImageResource(R.drawable.ic_empty_requests);
            emptyTitle.setText("All Caught Up! 🎉");
            emptySubtitle.setText("No pending requests found for " + selectedFilterLabel + ". Post your own help request from the Home tab!");
            emptyCta.setVisibility(View.GONE);
        } else {
            requestsRv.setVisibility(View.VISIBLE);
            emptyStateView.setVisibility(View.GONE);
        }
    }

    private String mapChipToCategory(String chipLabel) {
        switch (chipLabel) {
            case "Programming": return "Programming & Dev";
            case "AI": return "AI & Data Science";
            case "EE": return "Electrical & EE";
            case "CAD": return "CAD & Engineering";
            case "Math": return "Mathematics";
            case "Design": return "Design & Media";
            case "DB": return "Database & Systems";
            case "Business": return "Business & Soft Skills";
            default: return "All";
        }
    }

    // --- OnOfferHelpListener ---
    @Override
    public void onOfferHelp(SkillRequest request) {
        // Build a connection confirmation alert dialog
        new AlertDialog.Builder(getContext(), R.style.AUDialog)
                .setTitle("Offer Help 🤝")
                .setMessage("Would you like to connect with " + request.getRequesterName() + " (" + request.getRequesterRoll() + ") and offer help with \"" + request.getSkillWanted() + "\"?")
                .setPositiveButton("Connect & Help", (dialog, which) -> {
                    // Mark request as resolved
                    request.setResolved(true);

                    // Add Notification (Core Concept #3)
                    Notification notif = new Notification(
                            "n_custom_" + System.currentTimeMillis(),
                            "You offered help to " + request.getRequesterName() + " for " + request.getSkillWanted() + ".",
                            "Just now",
                            false
                    );
                    DataSource.addNotification(notif);

                    // Display success toast
                    Toast.makeText(getContext(), "Help request accepted! Connection details sent to your AU email.", Toast.LENGTH_LONG).show();

                    // Refresh
                    applyFilter();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
