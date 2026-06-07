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
 *  NotificationAdapter.java — ListView Custom Adapter for notifications
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
import com.auiskillbridge.models.Notification;

import java.util.List;

/**
 * ListView Custom Adapter: displays AU-flavoured notifications with unread indicators (Core Concept #2).
 * Used in NotificationsFragment to show activity updates.
 * Custom XML row layout: item_notification.xml with unread dot, message, and time.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {

    private final Context context;
    private final List<Notification> notifications;

    public NotificationAdapter(@NonNull Context context, @NonNull List<Notification> notifications) {
        super(context, R.layout.item_notification, notifications);
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // ListView Custom Adapter: inflating custom notification row layout (Core Concept #2)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        }

        Notification notification = notifications.get(position);

        // ListView Custom Adapter: binding notification data to custom row (Core Concept #2)
        View unreadDot = convertView.findViewById(R.id.unread_dot);
        TextView messageView = convertView.findViewById(R.id.notification_message);
        TextView timeView = convertView.findViewById(R.id.notification_time);

        messageView.setText(notification.getMessage());
        timeView.setText(notification.getTimeAgo());

        // Show unread dot for unread notifications
        if (!notification.isRead()) {
            unreadDot.setVisibility(View.VISIBLE);
        } else {
            unreadDot.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return notifications != null ? notifications.size() : 0;
    }
}
