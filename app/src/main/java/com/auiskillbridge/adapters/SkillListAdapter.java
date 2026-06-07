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
 *  SkillListAdapter.java — ListView Custom Adapter for skills list
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

import java.util.List;

/**
 * ListView Custom Adapter: displays skill names in a simple list with dot indicators (Core Concept #2).
 * Used in MentorDetailActivity (skills offered) and ProfileFragment (my skills).
 * Custom XML row layout: item_skill_list.xml with violet dot + skill name.
 */
public class SkillListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> skills;

    public SkillListAdapter(@NonNull Context context, @NonNull List<String> skills) {
        super(context, R.layout.item_skill_list, skills);
        this.context = context;
        this.skills = skills;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // ListView Custom Adapter: inflating custom skill row layout (Core Concept #2)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_skill_list, parent, false);
        }

        // ListView Custom Adapter: binding skill name to custom row (Core Concept #2)
        TextView skillName = convertView.findViewById(R.id.skill_name);
        skillName.setText(skills.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return skills != null ? skills.size() : 0;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return skills != null ? skills.get(position) : null;
    }
}
