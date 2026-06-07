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
 *  FeaturedMentorAdapter.java — RecyclerView Adapter for horizontal featured strip
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
 * RecyclerView Adapter: binds top-rated mentor data to horizontal scrollable cards (Core Concept #1).
 * Displays the "⭐ Featured Mentors" strip on the Home tab.
 */
public class FeaturedMentorAdapter extends RecyclerView.Adapter<FeaturedMentorAdapter.FeaturedViewHolder> {

    private final Context context;
    private final List<Mentor> mentors;

    public FeaturedMentorAdapter(Context context, List<Mentor> mentors) {
        this.context = context;
        this.mentors = mentors;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // RecyclerView Adapter: inflating featured mentor card layout (Core Concept #1)
        View view = LayoutInflater.from(context).inflate(R.layout.item_mentor_featured, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        Mentor mentor = mentors.get(position);

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

        // Department
        holder.dept.setText(mentor.getDepartment());
        GradientDrawable deptBg = (GradientDrawable) holder.dept.getBackground().mutate();
        deptBg.setColor(Color.parseColor("#1C1C28"));
        holder.dept.setTextColor(Color.parseColor("#8A8A9A"));

        // Top Skill
        if (mentor.getSkills() != null && !mentor.getSkills().isEmpty()) {
            holder.skill.setText(mentor.getSkills().get(0));
        }

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
        return mentors != null ? mentors.size() : 0;
    }

    /**
     * RecyclerView ViewHolder: holds references to featured card views (Core Concept #1)
     */
    static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        TextView avatar, name, dept, skill, rating;

        FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.featured_avatar);
            name = itemView.findViewById(R.id.featured_name);
            dept = itemView.findViewById(R.id.featured_dept);
            skill = itemView.findViewById(R.id.featured_skill);
            rating = itemView.findViewById(R.id.featured_rating);
        }
    }
}
