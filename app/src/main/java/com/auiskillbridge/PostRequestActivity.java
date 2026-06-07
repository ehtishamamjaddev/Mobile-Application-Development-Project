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
 *  PostRequestActivity.java — Form to post a new skill request with validations and pre-fills
 */

package com.auiskillbridge;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auiskillbridge.models.Notification;
import com.auiskillbridge.models.SkillRequest;
import com.auiskillbridge.models.Urgency;
import com.auiskillbridge.models.UserProfile;
import com.auiskillbridge.utils.Constants;
import com.auiskillbridge.utils.DataSource;
import com.auiskillbridge.utils.PreferencesManager;

/**
 * Activity for posting new skill help requests.
 * Features:
 * - Spinners populated programmatically fromConstants (Core Concept #5)
 * - Auto pre-fill spinner selections using saved UserProfile SharedPreferences (Core Concept #3)
 * - Non-empty form validation logic
 * - Inserts request to dynamic data store and logs notifications (Core Concept #3)
 */
public class PostRequestActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etSkillNeeded;
    private EditText etDescription;
    private Spinner spinnerUrgency;
    private Spinner spinnerDepartment;
    private Spinner spinnerSemester;
    private TextView btnSubmit;

    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_request);

        btnBack = findViewById(R.id.btn_back);
        etSkillNeeded = findViewById(R.id.et_skill_needed);
        etDescription = findViewById(R.id.et_description);
        spinnerUrgency = findViewById(R.id.spinner_urgency);
        spinnerDepartment = findViewById(R.id.spinner_department);
        spinnerSemester = findViewById(R.id.spinner_semester);
        btnSubmit = findViewById(R.id.btn_submit);

        // Load profile for pre-fills
        userProfile = PreferencesManager.getInstance(this).getUserProfile();

        // Populate Urgency Spinner
        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Constants.URGENCY_LEVELS
        );
        urgencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrgency.setAdapter(urgencyAdapter);
        spinnerUrgency.setSelection(1); // Default to Medium

        // Populate Department Spinner
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Constants.AU_DEPARTMENTS
        );
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(deptAdapter);

        // Pre-select user's department
        if (userProfile.getDepartment() != null) {
            for (int i = 0; i < Constants.AU_DEPARTMENTS.length; i++) {
                if (Constants.AU_DEPARTMENTS[i].equalsIgnoreCase(userProfile.getDepartment())) {
                    spinnerDepartment.setSelection(i);
                    break;
                }
            }
        }

        // Populate Semester Spinner
        ArrayAdapter<String> semAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Constants.AU_SEMESTERS
        );
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(semAdapter);

        // Pre-select user's semester
        if (userProfile.getSemester() != null) {
            for (int i = 0; i < Constants.AU_SEMESTERS.length; i++) {
                if (Constants.AU_SEMESTERS[i].equalsIgnoreCase(userProfile.getSemester())) {
                    spinnerSemester.setSelection(i);
                    break;
                }
            }
        }

        // Listeners
        btnBack.setOnClickListener(v -> finish());
        btnSubmit.setOnClickListener(v -> submitRequestForm());
    }

    private void submitRequestForm() {
        String skill = etSkillNeeded.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Form validation
        if (skill.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Selected spinner values
        int deptPos = spinnerDepartment.getSelectedItemPosition();
        String deptShortCode = Constants.AU_DEPT_CODES[deptPos];
        String semester = spinnerSemester.getSelectedItem().toString();

        String urgencyStr = spinnerUrgency.getSelectedItem().toString().toUpperCase();
        Urgency urgency = Urgency.valueOf(urgencyStr);

        // Construct new request
        SkillRequest newRequest = new SkillRequest();
        newRequest.setId("r_custom_" + System.currentTimeMillis());
        newRequest.setRequesterName(userProfile.getName());
        newRequest.setRequesterRoll(userProfile.getRollNumber());
        newRequest.setDepartment(deptShortCode);
        newRequest.setSemester(semester);
        newRequest.setSkillWanted(skill);
        newRequest.setDescription(description);
        newRequest.setUrgency(urgency);
        newRequest.setTimestamp(System.currentTimeMillis());
        newRequest.setResolved(false);

        // Save request to cache (in-memory data store)
        DataSource.addSkillRequest(newRequest);

        // Log notification of activity
        Notification notif = new Notification(
                "n_custom_" + System.currentTimeMillis(),
                "You posted a skill help request for \"" + skill + "\".",
                "Just now",
                false
        );
        DataSource.addNotification(notif);

        Toast.makeText(this, "Skill request posted successfully!", Toast.LENGTH_LONG).show();
        finish();
    }
}
