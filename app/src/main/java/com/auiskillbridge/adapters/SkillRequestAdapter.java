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
 *  SkillRequestAdapter.java — RecyclerView Adapter for skill request cards
 */

package com.auiskillbridge.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auiskillbridge.R;
import com.auiskillbridge.models.SkillRequest;
import com.auiskillbridge.models.Urgency;
import com.auiskillbridge.utils.AnimationUtils;

import java.util.List;

/**
 * RecyclerView Adapter: binds skill request data to scrollable card list (Core Concept #1).
 * Displays request cards in the Requests tab with urgency stripes and offer help buttons.
 */
public class SkillRequestAdapter extends RecyclerView.Adapter<SkillRequestAdapter.RequestViewHolder> {

    private final Context context;
    private List<SkillRequest> requests;
    private OnOfferHelpListener offerHelpListener;
    private int lastPosition = -1;

    public interface OnOfferHelpListener {
        void onOfferHelp(SkillRequest request);
    }

    public SkillRequestAdapter(Context context, List<SkillRequest> requests, OnOfferHelpListener listener) {
        this.context = context;
        this.requests = requests;
        this.offerHelpListener = listener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // RecyclerView Adapter: inflating skill request card layout (Core Concept #1)
        View view = LayoutInflater.from(context).inflate(R.layout.item_skill_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        SkillRequest request = requests.get(position);

        // Urgency stripe color
        int stripeColor;
        switch (request.getUrgency()) {
            case HIGH:
                stripeColor = Color.parseColor("#FF5C7C");
                break;
            case MEDIUM:
                stripeColor = Color.parseColor("#FFB347");
                break;
            default:
                stripeColor = Color.parseColor("#00E5A0");
                break;
        }
        holder.urgencyStripe.setBackgroundColor(stripeColor);

        // Avatar
        holder.avatar.setText(request.getRequesterInitials());
        GradientDrawable avatarBg = (GradientDrawable) holder.avatar.getBackground().mutate();
        avatarBg.setColor(stripeColor);

        // Name
        holder.name.setText(request.getRequesterName());

        // Dept + Semester
        holder.deptSem.setText(request.getDepartment() + " • " + request.getSemester());

        // Time
        holder.time.setText(request.getTimeAgo());

        // Skill chip
        holder.skill.setText(request.getSkillWanted());

        // Description
        holder.description.setText(request.getDescription());

        // Urgency badge
        holder.urgencyBadge.setText(request.getUrgency().name());
        switch (request.getUrgency()) {
            case HIGH:
                holder.urgencyBadge.setBackgroundResource(R.drawable.bg_badge_high);
                holder.urgencyBadge.setTextColor(Color.parseColor("#FF5C7C"));
                break;
            case MEDIUM:
                holder.urgencyBadge.setBackgroundResource(R.drawable.bg_badge_medium);
                holder.urgencyBadge.setTextColor(Color.parseColor("#FFB347"));
                break;
            default:
                holder.urgencyBadge.setBackgroundResource(R.drawable.bg_badge_low);
                holder.urgencyBadge.setTextColor(Color.parseColor("#00E5A0"));
                break;
        }

        // Offer help button
        holder.offerHelp.setOnClickListener(v -> {
            if (offerHelpListener != null) {
                offerHelpListener.onOfferHelp(request);
            }
        });

        // Long press listener
        holder.itemView.setOnLongClickListener(v -> {
            if (offerHelpListener != null) {
                offerHelpListener.onOfferHelp(request);
            }
            return true;
        });

        // RecyclerView item animation: slide up + fade in (Core Concept #1)
        if (position > lastPosition) {
            AnimationUtils.animateRecyclerViewItem(holder.itemView, position);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return requests != null ? requests.size() : 0;
    }

    public void updateList(List<SkillRequest> newRequests) {
        this.requests = newRequests;
        lastPosition = -1;
        notifyDataSetChanged();
    }

    /**
     * RecyclerView ViewHolder: holds references to request card views (Core Concept #1)
     */
    static class RequestViewHolder extends RecyclerView.ViewHolder {
        View urgencyStripe;
        TextView avatar, name, deptSem, time, skill, description, urgencyBadge, offerHelp;

        RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            urgencyStripe = itemView.findViewById(R.id.urgency_stripe);
            avatar = itemView.findViewById(R.id.request_avatar);
            name = itemView.findViewById(R.id.request_name);
            deptSem = itemView.findViewById(R.id.request_dept_sem);
            time = itemView.findViewById(R.id.request_time);
            skill = itemView.findViewById(R.id.request_skill);
            description = itemView.findViewById(R.id.request_description);
            urgencyBadge = itemView.findViewById(R.id.request_urgency_badge);
            offerHelp = itemView.findViewById(R.id.btn_offer_help);
        }
    }
}
