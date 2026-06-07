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
 *  Notification.java — Model for AU-flavoured notifications
 */

package com.auiskillbridge.models;

import java.io.Serializable;

/**
 * Represents a notification displayed in the Notifications tab.
 * These are dummy AU-flavoured notifications for demo purposes.
 */
public class Notification implements Serializable {

    private String id;
    private String message;
    private String timeAgo;
    private boolean isRead;

    public Notification() {
    }

    public Notification(String id, String message, String timeAgo, boolean isRead) {
        this.id = id;
        this.message = message;
        this.timeAgo = timeAgo;
        this.isRead = isRead;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public boolean isRead() {
        return isRead;
    }

    // --- Setters ---

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
