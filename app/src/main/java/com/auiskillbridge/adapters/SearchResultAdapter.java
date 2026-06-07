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
 *  SearchResultAdapter.java — RecyclerView Adapter for search results
 */

package com.auiskillbridge.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auiskillbridge.MentorDetailActivity;
import com.auiskillbridge.R;
import com.auiskillbridge.models.Mentor;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;

import java.util.List;

/**
 * RecyclerView Adapter: displays search results in a scrollable list (Core Concept #1).
 * Shows mentor info in a compact card format with avatar, name, skills preview, and rating.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder> {

    private final Context context;
    private final List<Mentor> results;

    public SearchResultAdapter(Context context, List<Mentor> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // RecyclerView Adapter: inflating search result item layout (Core Concept #1)
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Mentor mentor = results.get(position);

        // Avatar
        holder.avatar.setText(mentor.getInitials());
        GradientDrawable avatarBg = (GradientDrawable) holder.avatar.getBackground().mutate();
        try {
            avatarBg.setColor(Color.parseColor(mentor.getAvatarColor()));
        } catch (Exception e) {
            avatarBg.setColor(Color.parseColor("#7C6FFF"));
        }

        // Name
        holder.name.setText(mentor.getName());

        // Dept + Semester
        holder.dept.setText(mentor.getDepartment() + " • " + mentor.getSemester());

        // Skills preview (comma-separated)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(3, mentor.getSkills().size()); i++) {
            if (i > 0) sb.append(", ");
            sb.append(mentor.getSkills().get(i));
        }
        holder.skills.setText(sb.toString());

        // Rating
        holder.rating.setText("⭐ " + String.format("%.1f", mentor.getRating()));

        // Click to open detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MentorDetailActivity.class);
            intent.putExtra(Constants.EXTRA_MENTOR_ID, mentor.getId());
            context.startActivity(intent);
        });

        // RecyclerView item animation (Core Concept #1)
        AnimationUtils.animateRecyclerViewItem(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    /**
     * RecyclerView ViewHolder: holds references to search result views (Core Concept #1)
     */
    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView avatar, name, dept, skills, rating;

        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.result_avatar);
            name = itemView.findViewById(R.id.result_name);
            dept = itemView.findViewById(R.id.result_dept);
            skills = itemView.findViewById(R.id.result_skills);
            rating = itemView.findViewById(R.id.result_rating);
        }
    }
}
