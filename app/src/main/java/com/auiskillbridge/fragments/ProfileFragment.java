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
 *  ProfileFragment.java — Manages current user's profile, custom skills list, saved mentors, dark mode, and credits dialog
 */

package com.auiskillbridge.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.auiskillbridge.R;
import com.auiskillbridge.adapters.SkillListAdapter;
import com.auiskillbridge.models.Mentor;
import com.auiskillbridge.models.UserProfile;
import com.auiskillbridge.utils.AnimationUtils;
import com.auiskillbridge.utils.DataSource;
import com.auiskillbridge.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Fragment managing the student's User Profile.
 * Persists data via SharedPreferences (Core Concept #3).
 * Features:
 * - SharedPreferences lookup and update for user profile, dark mode, favorites (Core Concept #3)
 * - Custom ListView and adapter for user skills (Core Concept #2)
 * - Dynamic list height adjustment to resolve ScrollView nesting conflicts
 * - Inflates About Dialog with MAD project credits
 */
public class ProfileFragment extends Fragment {

    private TextView avatarText;
    private TextView nameText;
    private TextView rollText;
    private TextView deptSemText;
    private EditText bioInput;
    private TextView btnAddSkill;
    private ListView skillsListView;
    private TextView savedMentorsText;
    private Switch switchDarkMode;
    private TextView btnAbout;
    private TextView btnSaveProfile;

    private UserProfile userProfile;
    private SkillListAdapter skillsAdapter;
    private List<String> userSkillsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarText = view.findViewById(R.id.profile_avatar);
        nameText = view.findViewById(R.id.profile_name);
        rollText = view.findViewById(R.id.profile_roll);
        deptSemText = view.findViewById(R.id.profile_dept_sem);
        bioInput = view.findViewById(R.id.profile_bio);
        btnAddSkill = view.findViewById(R.id.btn_add_skill);
        skillsListView = view.findViewById(R.id.my_skills_listview);
        savedMentorsText = view.findViewById(R.id.saved_mentors_text);
        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        btnAbout = view.findViewById(R.id.btn_about);
        btnSaveProfile = view.findViewById(R.id.btn_edit_profile);

        // Transition fade in
        AnimationUtils.fadeIn(view);

        // Load profile data from SharedPreferences
        loadProfileData();

        // Save Profile Button
        btnSaveProfile.setOnClickListener(v -> saveProfileData());

        // Add Skill Button
        btnAddSkill.setOnClickListener(v -> showAddSkillDialog());

        // Skills ListView item clicks -> Delete skill
        skillsListView.setOnItemClickListener((parent, v1, position, id) -> {
            String skill = userSkillsList.get(position);
            showDeleteSkillDialog(skill, position);
        });

        // About Button
        btnAbout.setOnClickListener(v -> showAboutDialog());

        // Dark Mode switch handler
        switchDarkMode.setChecked(PreferencesManager.getInstance(getContext()).isDarkMode());
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PreferencesManager.getInstance(getContext()).setDarkMode(isChecked);
            userProfile.setDarkMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload saved mentors in case favorite set changed in search / detail screens
        displaySavedMentors();
    }

    private void loadProfileData() {
        PreferencesManager prefs = PreferencesManager.getInstance(getContext());
        userProfile = prefs.getUserProfile();

        // Set Text Fields
        nameText.setText(userProfile.getName());
        rollText.setText("Roll: " + userProfile.getRollNumber());
        deptSemText.setText(userProfile.getDepartment() + " • " + userProfile.getSemester());
        bioInput.setText(userProfile.getBio());

        // Set Avatar initials & color
        avatarText.setText(userProfile.getInitials());
        GradientDrawable avatarBg = (GradientDrawable) avatarText.getBackground().mutate();
        try {
            avatarBg.setColor(Color.parseColor(userProfile.getAvatarColor()));
        } catch (Exception e) {
            avatarBg.setColor(Color.parseColor("#7C6FFF"));
        }

        // Load offered skills list
        userSkillsList = new ArrayList<>(userProfile.getOfferedSkills());
        skillsAdapter = new SkillListAdapter(getContext(), userSkillsList);
        skillsListView.setAdapter(skillsAdapter);
        adjustListViewHeight();

        // Load favorites list
        displaySavedMentors();
    }

    private void saveProfileData() {
        String newBio = bioInput.getText().toString().trim();
        userProfile.setBio(newBio);
        userProfile.setOfferedSkills(userSkillsList);

        // SharedPreferences: saving profile values (Core Concept #3)
        PreferencesManager.getInstance(getContext()).saveUserProfile(userProfile);
        Toast.makeText(getContext(), "Profile saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void displaySavedMentors() {
        PreferencesManager prefs = PreferencesManager.getInstance(getContext());
        Set<String> favIds = prefs.getFavorites();

        if (favIds.isEmpty()) {
            savedMentorsText.setText("No saved mentors yet. Start connecting!");
            return;
        }

        List<Mentor> mentors = DataSource.getMentors();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Mentor m : mentors) {
            if (favIds.contains(m.getId())) {
                if (count > 0) sb.append(", ");
                sb.append(m.getName()).append(" (").append(m.getDepartment()).append(")");
                count++;
            }
        }

        if (count == 0) {
            savedMentorsText.setText("No saved mentors yet. Start connecting!");
        } else {
            savedMentorsText.setText(sb.toString());
        }
    }

    private void showAddSkillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AUDialog);
        builder.setTitle("Add Skill 💡");

        // Set up the input field in dialog
        final EditText input = new EditText(getContext());
        input.setHint("e.g. Flutter, AutoCAD, MATLAB");
        input.setPadding(32, 24, 32, 24);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String skill = input.getText().toString().trim();
            if (!skill.isEmpty()) {
                if (!userSkillsList.contains(skill)) {
                    userSkillsList.add(skill);
                    skillsAdapter.notifyDataSetChanged();
                    adjustListViewHeight();
                    // Save changes to SharedPreferences automatically
                    saveProfileData();
                } else {
                    Toast.makeText(getContext(), "Skill already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteSkillDialog(String skill, int position) {
        new AlertDialog.Builder(getContext(), R.style.AUDialog)
                .setTitle("Delete Skill 🗑️")
                .setMessage("Are you sure you want to remove \"" + skill + "\" from your profile?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    userSkillsList.remove(position);
                    skillsAdapter.notifyDataSetChanged();
                    adjustListViewHeight();
                    saveProfileData();
                    Toast.makeText(getContext(), "Skill removed", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AUDialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_about, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Close Button handler
        TextView btnClose = dialogView.findViewById(R.id.btn_close_about);
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Programmatic adjustment of ListView height inside ScrollView (MAD expert layout trick).
     */
    private void adjustListViewHeight() {
        if (skillsAdapter == null) return;
        int totalHeight = 0;
        int count = skillsAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = skillsAdapter.getView(i, null, skillsListView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = skillsListView.getLayoutParams();
        params.height = totalHeight + (skillsListView.getDividerHeight() * (count - 1));
        skillsListView.setLayoutParams(params);
        skillsListView.requestLayout();
    }
}
