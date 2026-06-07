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
 *  UserProfile.java — Model for the logged-in AU student's profile
 */

package com.auiskillbridge.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the currently logged-in AU student's profile.
 * Persisted entirely via SharedPreferences through PreferencesManager.
 */
public class UserProfile implements Serializable {

    private String name;
    private String rollNumber;
    private String department;
    private String semester;
    private String bio;
    private List<String> offeredSkills;
    private String avatarColor;
    private boolean isDarkMode;

    public UserProfile() {
        this.offeredSkills = new ArrayList<>();
        this.isDarkMode = true; // Default to dark mode (AU Dark Pro)
    }

    public UserProfile(String name, String rollNumber, String department,
                       String semester, String bio, List<String> offeredSkills,
                       String avatarColor, boolean isDarkMode) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
        this.bio = bio;
        this.offeredSkills = offeredSkills != null ? offeredSkills : new ArrayList<>();
        this.avatarColor = avatarColor;
        this.isDarkMode = isDarkMode;
    }

    // --- Getters ---

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getSemester() {
        return semester;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getOfferedSkills() {
        return offeredSkills;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    // --- Setters ---

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setOfferedSkills(List<String> offeredSkills) {
        this.offeredSkills = offeredSkills;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    /**
     * Returns initials for the avatar circle.
     */
    public String getInitials() {
        if (name == null || name.isEmpty()) return "?";
        String[] parts = name.trim().split("\\s+");
        if (parts.length >= 2) {
            return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
        }
        return parts[0].substring(0, 1).toUpperCase();
    }
}
