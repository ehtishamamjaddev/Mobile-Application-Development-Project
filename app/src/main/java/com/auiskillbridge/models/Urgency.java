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
 *  Urgency.java — Enum for skill request urgency levels
 */

package com.auiskillbridge.models;

/**
 * Urgency levels for skill requests posted by AU students.
 * Each level maps to a specific color in the AU Dark Pro palette.
 */
public enum Urgency {
    HIGH,    // Coral #FF5C7C — exam tomorrow, deadline critical
    MEDIUM,  // Amber #FFB347 — within a few days
    LOW      // Mint  #00E5A0 — no rush, general learning
}
