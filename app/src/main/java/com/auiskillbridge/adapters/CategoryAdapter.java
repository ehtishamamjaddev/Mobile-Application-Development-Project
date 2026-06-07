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
 *  CategoryAdapter.java — ListView Custom Adapter for skill categories
 */

package com.auiskillbridge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auiskillbridge.R;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.DataSource;

/**
 * ListView Custom Adapter: displays AU skill categories with icons and mentor count (Core Concept #2).
 * Used in ExploreFragment to show the 8 AU-relevant skill categories.
 * Custom XML row layout: item_category.xml with emoji icon, name, subtitle, and count badge.
 */
public class CategoryAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] categories;
    private final String[] subtitles;

    // Category emoji icons (distinct for each category)
    private final String[] icons = {
            "💻", // Programming & Dev
            "🤖", // AI & Data Science
            "⚡", // Electrical & EE
            "📐", // CAD & Engineering
            "📊", // Mathematics
            "🎨", // Design & Media
            "🗄️", // Database & Systems
            "💼"  // Business & Soft Skills
    };

    public CategoryAdapter(@NonNull Context context) {
        super(context, R.layout.item_category, Constants.SKILL_CATEGORIES);
        this.context = context;
        this.categories = Constants.SKILL_CATEGORIES;
        this.subtitles = Constants.CATEGORY_SUBTITLES;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // ListView Custom Adapter: inflating custom category row layout (Core Concept #2)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }

        // ListView Custom Adapter: binding category data to custom row (Core Concept #2)
        TextView iconView = convertView.findViewById(R.id.category_icon);
        TextView nameView = convertView.findViewById(R.id.category_name);
        TextView subtitleView = convertView.findViewById(R.id.category_subtitle);
        TextView countView = convertView.findViewById(R.id.category_count);

        iconView.setText(icons[position]);
        nameView.setText(categories[position]);
        subtitleView.setText(subtitles[position]);

        // Get mentor count for this category
        int mentorCount = DataSource.getMentorCountByCategory(categories[position]);
        countView.setText(mentorCount + " mentors");

        return convertView;
    }

    @Override
    public int getCount() {
        return categories.length;
    }
}
