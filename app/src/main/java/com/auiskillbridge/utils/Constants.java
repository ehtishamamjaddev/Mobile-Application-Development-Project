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
 *  Constants.java — App-wide constant values and SharedPreferences keys
 */

package com.auiskillbridge.utils;

/**
 * Central repository for all constants used across the AUSkillBridge app.
 * SharedPreferences keys, intent extras, and category definitions.
 */
public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    // ── SharedPreferences File Name ──
    public static final String PREFS_NAME = "auskillbridge_prefs";

    // ── SharedPreferences Keys (Core Concept #3) ──
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_ROLL = "user_roll_number";
    public static final String KEY_USER_DEPARTMENT = "user_department";
    public static final String KEY_USER_SEMESTER = "user_semester";
    public static final String KEY_USER_BIO = "user_bio";
    public static final String KEY_USER_AVATAR_COLOR = "user_avatar_color";
    public static final String KEY_USER_SKILLS = "user_offered_skills";
    public static final String KEY_FAVORITES = "favorite_mentor_ids";
    public static final String KEY_LAST_SEARCH = "last_search_query";
    public static final String KEY_DARK_MODE = "dark_mode_enabled";
    public static final String KEY_ONBOARDING_DONE = "onboarding_completed";
    public static final String KEY_LOGGED_IN = "user_logged_in";

    // ── Intent Extras ──
    public static final String EXTRA_MENTOR = "extra_mentor";
    public static final String EXTRA_MENTOR_ID = "extra_mentor_id";
    public static final String EXTRA_SEARCH_QUERY = "extra_search_query";
    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_SKILL_PREFILL = "extra_skill_prefill";

    // ── AU Departments ──
    public static final String[] AU_DEPARTMENTS = {
            "Computer Science",
            "Software Engineering",
            "Electrical Engineering",
            "Mechanical Engineering",
            "Computer Engineering",
            "Artificial Intelligence",
            "Data Science",
            "Cyber Security",
            "Aerospace Engineering",
            "Mechatronics Engineering",
            "Biomedical Engineering",
            "BBA / Management Sciences",
            "Accounting & Finance",
            "Physics",
            "Mathematics",
            "English Linguistics & Literature",
            "Psychology",
            "Defence & Strategic Studies",
            "Avionics & Aeronautics",
            "Creative Technologies",
            "Computer Games Development"
    };

    // ── AU Department Short Codes ──
    public static final String[] AU_DEPT_CODES = {
            "CS", "SE", "EE", "ME", "CE", "AI", "DS",
            "Cyber", "Aero", "Mech-E", "Biomed",
            "BBA", "AF", "Phys", "Math",
            "ELL", "Psych", "DSS", "IAA", "CT", "CGD"
    };

    // ── AU Semesters ──
    public static final String[] AU_SEMESTERS = {
            "1st Semester", "2nd Semester", "3rd Semester", "4th Semester",
            "5th Semester", "6th Semester", "7th Semester", "8th Semester"
    };

    // ── Skill Categories ──
    public static final String[] SKILL_CATEGORIES = {
            "Programming & Dev",
            "AI & Data Science",
            "Electrical & EE",
            "CAD & Engineering",
            "Mathematics",
            "Design & Media",
            "Database & Systems",
            "Business & Soft Skills"
    };

    // ── Category Subtitles ──
    public static final String[] CATEGORY_SUBTITLES = {
            "Python, Java, C++, Flutter, Web Dev",
            "ML, TensorFlow, Power BI, NLP",
            "MATLAB, Proteus, Multisim, PLC",
            "AutoCAD, SolidWorks, Engineering Drawing",
            "Calculus, Linear Algebra, Signals, Laplace",
            "Figma, Canva, Photoshop, UI/UX",
            "SQL, OS, DSA, System Design",
            "MS Office, Presentations, Business Writing"
    };

    // ── Filter Chip Labels (Requests Tab) ──
    public static final String[] FILTER_CHIPS = {
            "All", "Programming", "AI", "EE", "CAD", "Math", "Design", "DB", "Business"
    };

    // ── Urgency Levels ──
    public static final String[] URGENCY_LEVELS = {
            "High", "Medium", "Low"
    };

    // ── Animation Constants ──
    public static final int STAGGER_DELAY_MS = 60;
    public static final int ITEM_ANIM_DURATION_MS = 350;
    public static final int SPLASH_DURATION_MS = 1800;
    public static final int FAB_PULSE_INTERVAL_MS = 3000;
}
