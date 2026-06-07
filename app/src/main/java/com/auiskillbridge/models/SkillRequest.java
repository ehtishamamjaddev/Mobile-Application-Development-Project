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
 *  SkillRequest.java — Model for a skill help request by an AU student
 */

package com.auiskillbridge.models;

import java.io.Serializable;

/**
 * Represents a skill request posted by an AU student seeking help.
 * Contains requester info, the skill needed, urgency, and timestamp.
 */
public class SkillRequest implements Serializable {

    private String id;
    private String requesterName;
    private String requesterRoll;
    private String department;
    private String semester;
    private String skillWanted;
    private String description;
    private Urgency urgency;
    private long timestamp;
    private boolean isResolved;

    public SkillRequest() {
    }

    public SkillRequest(String id, String requesterName, String requesterRoll,
                        String department, String semester, String skillWanted,
                        String description, Urgency urgency, long timestamp,
                        boolean isResolved) {
        this.id = id;
        this.requesterName = requesterName;
        this.requesterRoll = requesterRoll;
        this.department = department;
        this.semester = semester;
        this.skillWanted = skillWanted;
        this.description = description;
        this.urgency = urgency;
        this.timestamp = timestamp;
        this.isResolved = isResolved;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public String getRequesterRoll() {
        return requesterRoll;
    }

    public String getDepartment() {
        return department;
    }

    public String getSemester() {
        return semester;
    }

    public String getSkillWanted() {
        return skillWanted;
    }

    public String getDescription() {
        return description;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isResolved() {
        return isResolved;
    }

    // --- Setters ---

    public void setId(String id) {
        this.id = id;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public void setRequesterRoll(String requesterRoll) {
        this.requesterRoll = requesterRoll;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setSkillWanted(String skillWanted) {
        this.skillWanted = skillWanted;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    /**
     * Returns initials for the requester's avatar circle.
     */
    public String getRequesterInitials() {
        if (requesterName == null || requesterName.isEmpty()) return "?";
        String[] parts = requesterName.trim().split("\\s+");
        if (parts.length >= 2) {
            return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
        }
        return parts[0].substring(0, 1).toUpperCase();
    }

    /**
     * Returns human-readable time ago string from timestamp.
     */
    public String getTimeAgo() {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        long hours = diff / (1000 * 60 * 60);
        if (hours < 1) return "Just now";
        if (hours < 24) return hours + "h ago";
        long days = hours / 24;
        if (days < 7) return days + "d ago";
        return (days / 7) + "w ago";
    }

    /**
     * Returns the category this request falls under based on skillWanted.
     */
    public String getCategory() {
        String skill = skillWanted.toLowerCase();
        if (skill.contains("python") || skill.contains("java") || skill.contains("c++") ||
                skill.contains("flutter") || skill.contains("web") || skill.contains("react")) {
            return "Programming & Dev";
        } else if (skill.contains("ml") || skill.contains("tensorflow") || skill.contains("ai") ||
                skill.contains("power bi") || skill.contains("nlp") || skill.contains("data")) {
            return "AI & Data Science";
        } else if (skill.contains("matlab") || skill.contains("proteus") || skill.contains("multisim") ||
                skill.contains("plc") || skill.contains("circuit") || skill.contains("laplace") ||
                skill.contains("signals")) {
            return "Electrical & EE";
        } else if (skill.contains("autocad") || skill.contains("solidworks") ||
                skill.contains("engineering drawing") || skill.contains("cad")) {
            return "CAD & Engineering";
        } else if (skill.contains("calculus") || skill.contains("algebra") ||
                skill.contains("math") || skill.contains("dijkstra") || skill.contains("dsa")) {
            return "Mathematics";
        } else if (skill.contains("figma") || skill.contains("canva") ||
                skill.contains("photoshop") || skill.contains("design")) {
            return "Design & Media";
        } else if (skill.contains("sql") || skill.contains("os") ||
                skill.contains("system") || skill.contains("normalization") || skill.contains("db")) {
            return "Database & Systems";
        } else if (skill.contains("office") || skill.contains("presentation") ||
                skill.contains("business") || skill.contains("writing")) {
            return "Business & Soft Skills";
        }
        return "Programming & Dev";
    }
}
