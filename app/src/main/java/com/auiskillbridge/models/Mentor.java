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
 *  Mentor.java — Model representing an AU student mentor
 */

package com.auiskillbridge.models;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a mentor on the AUSkillBridge platform.
 * Each mentor is an AU student who offers specific skills to help peers.
 */
public class Mentor implements Serializable {

    private String id;
    private String name;
    private String rollNumber;
    private String department;
    private String semester;
    private String bio;
    private List<String> skills;
    private float rating;
    private boolean isOnline;
    private boolean isFavorite;
    private int reviewCount;
    private String avatarColor; // Hex string for avatar circle background

    public Mentor() {
    }

    public Mentor(String id, String name, String rollNumber, String department,
                  String semester, String bio, List<String> skills, float rating,
                  boolean isOnline, boolean isFavorite, int reviewCount, String avatarColor) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
        this.bio = bio;
        this.skills = skills;
        this.rating = rating;
        this.isOnline = isOnline;
        this.isFavorite = isFavorite;
        this.reviewCount = reviewCount;
        this.avatarColor = avatarColor;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

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

    public List<String> getSkills() {
        return skills;
    }

    public float getRating() {
        return rating;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    // --- Setters ---

    public void setId(String id) {
        this.id = id;
    }

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

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }

    /**
     * Returns initials for the avatar circle (first letter of first and last name).
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
