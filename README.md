# 📱 SkillBridge — Peer-to-Peer Mobile Learning Platform

**SkillBridge** is a high-performance, native Android application designed to facilitate peer-to-peer knowledge sharing, skill exchange, and interactive mentoring. 

Created and developed by **M Ehtisham Amjad**, the application provides a clean, responsive, and highly polished platform where users can act as both learners seeking support and mentors sharing their expertise in fields ranging from Software Engineering to Digital Design.

---

## 🎨 Design Identity: "Clarity UI"

Inspired by modern, minimal visual systems like *Notion*, *Linear*, and *iOS 17*, SkillBridge uses a custom design language:
*   **Minimal & Clean:** Pure white background surfaces with crisp grey borders and high-contrast typography.
*   **Curated Palettes:** HSL-tailored accents (deep brand purples, soft mint greens, and warm gold accents) that create a premium look.
*   **Vector Iconography:** Custom vector assets mapped dynamically to category topics to ensure scalable rendering across high-density devices.
*   **Premium Micro-interactions:** Smooth animations, elevated card selections, and responsive touch ripples.

---

## 🚀 Key Features

*   **Dynamic Dashboard:** Home interface featuring a horizontal featured mentors carousel showing top-rated tutors, followed by a categorized grid of academic and professional domains.
*   **Categorized Search & Discovery:** Instantly filter mentors by 8 distinct categories including Programming, AI & Data Science, Electrical & EE, CAD & Engineering, Database Systems, and Mathematics.
*   **Advanced Search Engine:** Query the entire mentor directory by name, department, or individual skill keywords.
*   **Circular Crop Profile Rendering:** A custom graphics component (`AvatarTextView`) that composites user portraits or fallbacks on initials dynamically inside a circular mask at 60 FPS without memory leaks.
*   **Peer Request Desk:** A public bulletin board where users can post help requests with varying urgency tags (High, Medium, Low) to crowdsource peer tutoring.
*   **Interactive Onboarding Flow:** A premium multi-step onboarding carousel explaining how to utilize the app to request help or start mentoring.
*   **Dynamic Notification Center:** A live list of profile views, request responses, and mentor connection alerts.

---

## 🛠️ Architecture & Core Components

The app is written in **Java** and uses XML Layouts with custom Android Views:

### 1. Presentation Layer (Activities & Fragments)
*   **`SplashActivity`:** Renders a clean branding page with a custom logo.
*   **`OnboardingActivity`:** Manages onboarding state via a customized slider adapter.
*   **`MainActivity`:** Integrates the Bottom Navigation Bar linking the 5 core fragments:
    *   `HomeFragment` — Featured items and categories grid.
    *   `ExploreFragment` — Complete list of peer mentors.
    *   `RequestsFragment` — Public board of active help requests.
    *   `NotificationsFragment` — Connection and view logs.
    *   `ProfileFragment` — User account details.
*   **`MentorDetailActivity`:** Full-screen detail card for mentors containing their history, bio, rating, and contact actions.

### 2. Custom Graphics & Utilities
*   **`AvatarTextView`:** Extends `AppCompatTextView` to enable full compatibility with standard adapter viewholders. Uses `PorterDuff.Mode.SRC_IN` composition to dynamically crop portrait images into circular avatars on-the-fly.
*   **`CleanTextView`:** Filters system emojis and converts rating stars into customized amber vector stars (`★`) inheriting current text styles.
*   **`CategoryIconView`:** Maps textual categories to vector assets dynamically.

---

## 📦 Technical Specifications

*   **Min SDK:** 26 (Android 8.0)
*   **Target SDK:** 34 (Android 14)
*   **Language:** Java (JDK 17/21)
*   **Build System:** Gradle (v8.13)
*   **UI Libraries:** AndroidX AppCompat, Google Material Components, CardView, ConstraintLayout

---

## 👨‍💻 Developer & Creator

*   **M Ehtisham Amjad** — Lead Android Developer / UI/UX Designer
