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
 *  NotificationsFragment.java — Lists notifications with custom ListView Adapter
 */

package com.auiskillbridge.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.auiskillbridge.R;
import com.auiskillbridge.adapters.NotificationAdapter;
import com.auiskillbridge.models.Notification;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.DataSource;

import java.util.List;

/**
 * Fragment showing the Notifications screen.
 * Displays dummy AU-flavoured notifications using a Custom ListView Adapter (Core Concept #2).
 * Tapping a notification marks it as read.
 */
public class NotificationsFragment extends Fragment {

    private ListView notificationsList;
    private NotificationAdapter adapter;
    private List<Notification> notifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationsList = view.findViewById(R.id.notifications_listview);

        // Transition fade in
        AnimationUtils.fadeIn(view);

        // Fetch notifications list
        notifications = DataSource.getNotifications();

        // Custom ListView Adapter binding (Core Concept #2)
        adapter = new NotificationAdapter(getContext(), notifications);
        notificationsList.setAdapter(adapter);

        // Item click listener -> marks read
        notificationsList.setOnItemClickListener((parent, v, position, id) -> {
            Notification item = notifications.get(position);
            if (!item.isRead()) {
                item.setRead(true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update notifications list in case new ones were generated at runtime
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
