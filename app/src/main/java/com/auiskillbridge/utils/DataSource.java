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
 *  DataSource.java — Hardcoded AU-realistic dummy data with runtime caching
 */

package com.auiskillbridge.utils;

import com.auiskillbridge.models.Mentor;
import com.auiskillbridge.models.Notification;
import com.auiskillbridge.models.SkillRequest;
import com.auiskillbridge.models.Urgency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides hardcoded but realistic AU-specific data for the AUSkillBridge app.
 * All mentor names, departments, roll numbers, and skills are realistic
 * for Air University Islamabad students.
 * Caches list instances in memory to enable runtime dynamic updates.
 */
public class DataSource {

    private static List<Mentor> mentorsCache;
    private static List<SkillRequest> requestsCache;
    private static List<Notification> notificationsCache;

    /**
     * Returns the complete list of 12 AU student mentors.
     * Each mentor has realistic skills relevant to their AU department.
     */
    public static synchronized List<Mentor> getMentors() {
        if (mentorsCache == null) {
            mentorsCache = new ArrayList<>();

            mentorsCache.add(new Mentor(
                    "m1", "Ehtisham Amjad", "231996", "SE", "6th Semester",
                    "Passionate about mobile and web development. Love building apps with Flutter and Firebase. Always happy to help fellow AU students with their projects!",
                    new ArrayList<>(Arrays.asList("Flutter", "Firebase", "Figma", "OOP in Java")),
                    4.8f, true, false, 24, "#7C6FFF"
            ));

            mentorsCache.add(new Mentor(
                    "m2", "M Abdullah", "232052", "CS", "5th Semester",
                    "Hey there! Passionate about machine learning, databases, and software design. Let's connect and share knowledge!",
                    new ArrayList<>(Arrays.asList("Python", "Machine Learning", "Data Analysis", "SQL")),
                    4.6f, true, false, 18, "#A994FF"
            ));

            mentorsCache.add(new Mentor(
                    "m3", "Aman Mir", "233002", "EE", "7th Semester",
                    "Senior electrical engineering student. Proficient in circuit design, MATLAB simulations, and digital logic design. Feel free to reach out!",
                    new ArrayList<>(Arrays.asList("Circuit Design", "MATLAB", "Proteus Simulation", "VHDL")),
                    4.9f, false, false, 31, "#FFB347"
            ));

            mentorsCache.add(new Mentor(
                    "m4", "Ayesha Siddiqui", "231101", "AI", "4th Semester",
                    "Deep learning researcher in training. Working with TensorFlow and computer vision. Let's explore AI together!",
                    new ArrayList<>(Arrays.asList("TensorFlow", "Deep Learning", "Computer Vision", "Python")),
                    4.5f, true, false, 15, "#FF5C7C"
            ));

            mentorsCache.add(new Mentor(
                    "m5", "Bilal Chaudhry", "211445", "CE", "6th Semester",
                    "Embedded systems enthusiast. From Arduino to FPGA, I love making hardware talk. AutoCAD for mechanical designs too!",
                    new ArrayList<>(Arrays.asList("AutoCAD", "Embedded Systems", "Arduino", "VHDL")),
                    4.7f, false, false, 22, "#00E5A0"
            ));

            mentorsCache.add(new Mentor(
                    "m6", "Mahnoor Iqbal", "221678", "DS", "5th Semester",
                    "Data storyteller. I turn raw numbers into beautiful dashboards. Power BI and Tableau are my tools of choice.",
                    new ArrayList<>(Arrays.asList("Power BI", "SQL", "Tableau", "Excel Dashboards")),
                    4.4f, true, false, 12, "#7FD460"
            ));

            mentorsCache.add(new Mentor(
                    "m7", "Zain ul Abideen", "201923", "ME", "7th Semester",
                    "Mechanical engineering senior. SolidWorks expert and thermodynamics tutor. Happy to help with engineering drawing assignments!",
                    new ArrayList<>(Arrays.asList("SolidWorks", "AutoCAD", "Thermodynamics", "Engineering Drawing")),
                    4.8f, false, false, 27, "#9090A8"
            ));

            mentorsCache.add(new Mentor(
                    "m8", "Sana Mirza", "241056", "CS", "3rd Semester",
                    "Web dev enthusiast! Building cool websites with React and modern CSS. Also freelancing in WordPress and UI Design.",
                    new ArrayList<>(Arrays.asList("Web Dev (HTML/CSS/JS)", "React", "WordPress", "UI Design")),
                    4.3f, true, false, 9, "#A994FF"
            ));

            mentorsCache.add(new Mentor(
                    "m9", "Ahmed Raza", "191234", "SE", "8th Semester",
                    "Final year SE student. Competitive programmer and DSA expert. Let me help you crack those coding interviews and exams!",
                    new ArrayList<>(Arrays.asList("DSA", "System Design", "OS Concepts", "C++", "Competitive Programming")),
                    4.9f, true, false, 35, "#4DA6FF"
            ));

            mentorsCache.add(new Mentor(
                    "m10", "Hira Baig", "231789", "BBA", "4th Semester",
                    "Business student with strong design and digital marketing skills. Need a poster or presentation? I've got you covered!",
                    new ArrayList<>(Arrays.asList("MS Office", "Canva", "Digital Marketing", "Business Writing")),
                    4.2f, false, false, 8, "#E080C0"
            ));

            mentorsCache.add(new Mentor(
                    "m11", "Talha Mehmood", "211302", "EE", "6th Semester",
                    "Power systems and PLC programming specialist. Multisim simulations are my bread and butter. EE students, hit me up!",
                    new ArrayList<>(Arrays.asList("PLC Programming", "Power Systems", "Multisim", "MATLAB")),
                    4.6f, true, false, 19, "#FFB347"
            ));

            mentorsCache.add(new Mentor(
                    "m12", "Rimsha Zahid", "221834", "AI", "5th Semester",
                    "NLP researcher and chatbot builder. Passionate about prompt engineering and the future of AI. Python is life!",
                    new ArrayList<>(Arrays.asList("NLP", "Chatbot Dev", "Prompt Engineering", "Python")),
                    4.7f, false, false, 21, "#FF5C7C"
            ));
        }
        return mentorsCache;
    }

    /**
     * Returns featured mentors (top-rated, rating >= 4.7).
     * Displayed in the horizontal RecyclerView strip on the Home tab.
     */
    public static List<Mentor> getFeaturedMentors() {
        List<Mentor> featured = new ArrayList<>();
        for (Mentor m : getMentors()) {
            if (m.getRating() >= 4.7f) {
                featured.add(m);
            }
        }
        return featured;
    }

    /**
     * Returns the complete list of 8 realistic AU student skill requests.
     * Timestamps are set relative to current time for realistic "X hours ago" display.
     */
    public static synchronized List<SkillRequest> getSkillRequests() {
        if (requestsCache == null) {
            requestsCache = new ArrayList<>();
            long now = System.currentTimeMillis();

            requestsCache.add(new SkillRequest(
                    "r1", "Ahmed", "230145", "CS", "4th Semester",
                    "DSA",
                    "Need help understanding Dijkstra's Algorithm before DSA exam",
                    Urgency.HIGH, now - (2 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r2", "Omar", "220987", "EE", "5th Semester",
                    "Laplace Transforms",
                    "Can someone explain Laplace Transforms? My signals exam is in 3 days",
                    Urgency.HIGH, now - (5 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r3", "Sara", "210678", "SE", "7th Semester",
                    "Flutter",
                    "Looking for Flutter mentor for FYP — need help with state management",
                    Urgency.MEDIUM, now - (8 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r4", "Kamran", "240321", "ME", "3rd Semester",
                    "SolidWorks",
                    "Anyone good at SolidWorks? Need to finish ME lab project",
                    Urgency.MEDIUM, now - (12 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r5", "Nadia", "230789", "AI", "3rd Semester",
                    "TensorFlow",
                    "Need help setting up TensorFlow for AI assignment",
                    Urgency.MEDIUM, now - (18 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r6", "Ali", "220456", "CS", "5th Semester",
                    "Normalization",
                    "Looking for someone who can explain Normalization in DB — exam tomorrow!",
                    Urgency.HIGH, now - (1 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r7", "Zara", "240890", "SE", "2nd Semester",
                    "Canva/Photoshop",
                    "Need Canva/Photoshop help for our CS dept event poster",
                    Urgency.LOW, now - (24 * 60 * 60 * 1000), false
            ));

            requestsCache.add(new SkillRequest(
                    "r8", "Hassan", "230234", "CE", "4th Semester",
                    "Arduino",
                    "Can anyone help me debug my Arduino code? The sensor isn't reading values",
                    Urgency.HIGH, now - (3 * 60 * 60 * 1000), false
            ));
        }
        return requestsCache;
    }

    /**
     * Returns dummy AU-flavoured notifications.
     */
    public static synchronized List<Notification> getNotifications() {
        if (notificationsCache == null) {
            notificationsCache = new ArrayList<>();

            notificationsCache.add(new Notification(
                    "n1", "M Ehtisham Amjad viewed your profile", "2m ago", false
            ));
            notificationsCache.add(new Notification(
                    "n2", "New Flutter mentor joined from SE department", "15m ago", false
            ));
            notificationsCache.add(new Notification(
                    "n3", "3 new skill requests posted tonight", "1h ago", false
            ));
            notificationsCache.add(new Notification(
                    "n4", "Fatima Noor accepted your help request", "2h ago", true
            ));
            notificationsCache.add(new Notification(
                    "n5", "Exam season alert: 12 new Math requests posted", "3h ago", true
            ));
            notificationsCache.add(new Notification(
                    "n6", "Ahmed Raza is now offering Competitive Programming", "5h ago", true
            ));
            notificationsCache.add(new Notification(
                    "n7", "Your profile was viewed 8 times this week", "8h ago", true
            ));
            notificationsCache.add(new Notification(
                    "n8", "Bilal Chaudhry wants to connect with you", "12h ago", true
            ));
            notificationsCache.add(new Notification(
                    "n9", "New AutoCAD mentor from ME department!", "1d ago", true
            ));
            notificationsCache.add(new Notification(
                    "n10", "Welcome to AUSkillBridge! Complete your profile to get started.", "2d ago", true
            ));
        }
        return notificationsCache;
    }

    /**
     * Adds a new skill request to the top of the cache.
     */
    public static synchronized void addSkillRequest(SkillRequest request) {
        getSkillRequests().add(0, request);
    }

    /**
     * Adds a new notification to the top of the cache.
     */
    public static synchronized void addNotification(Notification notification) {
        getNotifications().add(0, notification);
    }

    /**
     * Returns mentors filtered by a search query matching skill names.
     */
    public static List<Mentor> searchMentors(String query) {
        List<Mentor> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();

        for (Mentor mentor : getMentors()) {
            // Check name
            if (mentor.getName().toLowerCase().contains(lowerQuery)) {
                results.add(mentor);
                continue;
            }
            // Check department
            if (mentor.getDepartment().toLowerCase().contains(lowerQuery)) {
                results.add(mentor);
                continue;
            }
            // Check skills
            for (String skill : mentor.getSkills()) {
                if (skill.toLowerCase().contains(lowerQuery)) {
                    results.add(mentor);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * Returns mentors filtered by skill category.
     */
    public static List<Mentor> getMentorsByCategory(String category) {
        List<Mentor> results = new ArrayList<>();
        List<String> keywords = getCategoryKeywords(category);

        for (Mentor mentor : getMentors()) {
            for (String skill : mentor.getSkills()) {
                String lowerSkill = skill.toLowerCase();
                for (String keyword : keywords) {
                    if (lowerSkill.contains(keyword.toLowerCase())) {
                        if (!results.contains(mentor)) {
                            results.add(mentor);
                        }
                        break;
                    }
                }
            }
        }
        return results;
    }

    /**
     * Returns keywords associated with each skill category for filtering.
     */
    private static List<String> getCategoryKeywords(String category) {
        switch (category) {
            case "Programming & Dev":
                return Arrays.asList("python", "java", "c++", "flutter", "web", "react",
                        "firebase", "oop", "wordpress", "html", "css", "js");
            case "AI & Data Science":
                return Arrays.asList("ml", "machine learning", "tensorflow", "deep learning",
                        "power bi", "nlp", "data", "numpy", "computer vision",
                        "chatbot", "prompt", "tableau");
            case "Electrical & EE":
                return Arrays.asList("matlab", "proteus", "multisim", "plc", "circuit",
                        "power systems", "signals");
            case "CAD & Engineering":
                return Arrays.asList("autocad", "solidworks", "engineering drawing",
                        "thermodynamics", "cad");
            case "Mathematics":
                return Arrays.asList("calculus", "algebra", "signals", "laplace",
                        "math", "dijkstra");
            case "Design & Media":
                return Arrays.asList("figma", "canva", "photoshop", "ui", "ux", "design");
            case "Database & Systems":
                return Arrays.asList("sql", "os", "dsa", "system design", "normalization",
                        "database", "competitive");
            case "Business & Soft Skills":
                return Arrays.asList("office", "presentation", "business", "writing",
                        "marketing", "excel");
            default:
                return new ArrayList<>();
        }
    }

    /**
     * Returns count of mentors in a given category.
     */
    public static int getMentorCountByCategory(String category) {
        return getMentorsByCategory(category).size();
    }
}
