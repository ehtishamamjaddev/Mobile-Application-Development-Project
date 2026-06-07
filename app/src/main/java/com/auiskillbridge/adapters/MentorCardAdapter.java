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
 *  MentorCardAdapter.java — RecyclerView Adapter for mentor cards
 */

package com.auiskillbridge.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auiskillbridge.MentorDetailActivity;
import com.auiskillbridge.R;
import com.auiskillbridge.models.Mentor;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.PreferencesManager;

import java.util.List;

/**
 * RecyclerView Adapter: binds mentor data to scrollable card list (Core Concept #1).
 * Displays mentor cards in the Home tab's vertical "All AU Mentors" section.
 * Each card shows avatar, name, department badge, skill chips, and favorite toggle.
 */
public class MentorCardAdapter extends RecyclerView.Adapter<MentorCardAdapter.MentorViewHolder> {

    private final Context context;
    private List<Mentor> mentors;
    private int lastPosition = -1;

    public MentorCardAdapter(Context context, List<Mentor> mentors) {
        this.context = context;
        this.mentors = mentors;
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // RecyclerView Adapter: inflating custom card layout for each mentor (Core Concept #1)
        View view = LayoutInflater.from(context).inflate(R.layout.item_mentor_card, parent, false);
        return new MentorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        Mentor mentor = mentors.get(position);

        // Set avatar initials and color
        holder.avatar.setText(mentor.getInitials());
        GradientDrawable avatarBg = (GradientDrawable) holder.avatar.getBackground().mutate();
        try {
            avatarBg.setColor(Color.parseColor(mentor.getAvatarColor()));
        } catch (Exception e) {
            avatarBg.setColor(Color.parseColor("#7C6FFF"));
        }

        // Online dot
        if (mentor.isOnline()) {
            holder.onlineDot.setVisibility(View.VISIBLE);
            GradientDrawable dotBg = (GradientDrawable) holder.onlineDot.getBackground().mutate();
            dotBg.setColor(Color.parseColor("#00E5A0"));
        } else {
            holder.onlineDot.setVisibility(View.GONE);
        }

        // Name
        holder.name.setText(mentor.getName());

        // Department badge
        holder.deptBadge.setText(mentor.getDepartment());
        setDeptBadgeColors(holder.deptBadge, mentor.getDepartment());

        // Stats: X skills • ⭐ Y.Y
        String stats = mentor.getSkills().size() + " skills • ⭐ " + String.format("%.1f", mentor.getRating());
        holder.stats.setText(stats);

        // Favorite heart
        // SharedPreferences: checking if this mentor is in the user's favorites (Core Concept #3)
        PreferencesManager prefs = PreferencesManager.getInstance(context);
        boolean isFav = prefs.isFavorite(mentor.getId());
        holder.heart.setImageResource(isFav ?
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        holder.heart.setOnClickListener(v -> {
            // SharedPreferences: toggling favorite status and persisting (Core Concept #3)
            if (prefs.isFavorite(mentor.getId())) {
                prefs.removeFavorite(mentor.getId());
                holder.heart.setImageResource(android.R.drawable.btn_star_big_off);
            } else {
                prefs.addFavorite(mentor.getId());
                holder.heart.setImageResource(android.R.drawable.btn_star_big_on);
            }
            AnimationUtils.bounceHeart(holder.heart);
        });

        // Skill chips (first 3)
        holder.skillsContainer.removeAllViews();
        int chipCount = Math.min(3, mentor.getSkills().size());
        for (int i = 0; i < chipCount; i++) {
            TextView chip = new TextView(context);
            chip.setText(mentor.getSkills().get(i));
            chip.setBackgroundResource(R.drawable.bg_chip_violet);
            chip.setTextColor(context.getResources().getColor(R.color.chip_violet_text));
            chip.setTextSize(10);
            int hPad = (int) (8 * context.getResources().getDisplayMetrics().density);
            int vPad = (int) (3 * context.getResources().getDisplayMetrics().density);
            chip.setPadding(hPad, vPad, hPad, vPad);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int) (6 * context.getResources().getDisplayMetrics().density));
            chip.setLayoutParams(params);
            holder.skillsContainer.addView(chip);
        }

        // Click to open detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MentorDetailActivity.class);
            intent.putExtra(Constants.EXTRA_MENTOR_ID, mentor.getId());
            context.startActivity(intent);
        });

        // RecyclerView item animation: slide up + fade in with staggered delay (Core Concept #1)
        if (position > lastPosition) {
            AnimationUtils.animateRecyclerViewItem(holder.itemView, position);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mentors != null ? mentors.size() : 0;
    }

    public void updateList(List<Mentor> newMentors) {
        this.mentors = newMentors;
        notifyDataSetChanged();
    }

    /**
     * Sets department badge background and text color based on department code.
     */
    private void setDeptBadgeColors(TextView badge, String dept) {
        GradientDrawable bg = (GradientDrawable) badge.getBackground().mutate();
        switch (dept) {
            case "CS":
                bg.setColor(Color.parseColor("#2D2058"));
                badge.setTextColor(Color.parseColor("#A994FF"));
                break;
            case "SE":
                bg.setColor(Color.parseColor("#1A2D4A"));
                badge.setTextColor(Color.parseColor("#4DA6FF"));
                break;
            case "EE":
                bg.setColor(Color.parseColor("#3A2800"));
                badge.setTextColor(Color.parseColor("#FFB347"));
                break;
            case "CE":
                bg.setColor(Color.parseColor("#003A2D"));
                badge.setTextColor(Color.parseColor("#00E5A0"));
                break;
            case "AI":
                bg.setColor(Color.parseColor("#3A1020"));
                badge.setTextColor(Color.parseColor("#FF5C7C"));
                break;
            case "DS":
                bg.setColor(Color.parseColor("#1A3010"));
                badge.setTextColor(Color.parseColor("#7FD460"));
                break;
            case "ME":
                bg.setColor(Color.parseColor("#282830"));
                badge.setTextColor(Color.parseColor("#9090A8"));
                break;
            case "BBA":
                bg.setColor(Color.parseColor("#3A1535"));
                badge.setTextColor(Color.parseColor("#E080C0"));
                break;
            default:
                bg.setColor(Color.parseColor("#1C1C28"));
                badge.setTextColor(Color.parseColor("#8A8A9A"));
                break;
        }
    }

    /**
     * RecyclerView ViewHolder: holds references to mentor card views (Core Concept #1)
     */
    static class MentorViewHolder extends RecyclerView.ViewHolder {
        TextView avatar, name, deptBadge, stats;
        View onlineDot;
        ImageView heart;
        LinearLayout skillsContainer;

        MentorViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.mentor_avatar);
            name = itemView.findViewById(R.id.mentor_name);
            deptBadge = itemView.findViewById(R.id.mentor_dept_badge);
            stats = itemView.findViewById(R.id.mentor_stats);
            onlineDot = itemView.findViewById(R.id.online_dot);
            heart = itemView.findViewById(R.id.btn_heart);
            skillsContainer = itemView.findViewById(R.id.skills_container);
        }
    }
}
