package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.view.adapters.BadgeAdapter;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;

public class ProfileActivity extends BaseActivity {
    LinearLayout moreCourses;
    ImageView settingsIcon;
    RecyclerView completedCourseRecycler;
    RecyclerView achievedBadgeRecycler;
    TextView nameText, roleText, bioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_profile);

        initViews();
        setupListeners();
        setupViews();
    }

    private void initViews() {
        settingsIcon = findViewById(R.id.settingsIcon);
        completedCourseRecycler = findViewById(R.id.recycler_courses_completed);
        moreCourses = findViewById(R.id.more_courses_completed);
        achievedBadgeRecycler = findViewById(R.id.recycler_certificates_achieved);
        nameText = findViewById(R.id.txt_name);
        roleText = findViewById(R.id.txt_role);
        bioText = findViewById(R.id.txt_bio);
    }
    private void setupListeners() {
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CoursesActivity.class);
                intent.putExtra("selectedCategory", "Completed Courses");
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        refreshProfileInfo();
    }

    private void setupViews() {
        refreshProfileInfo();
        setupRecyclerViews();
    }
    private void refreshProfileInfo() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            String userId = currentUser.getUid();

            // Update name
            if (displayName != null && !displayName.isEmpty()) {
                nameText.setText(displayName);
            } else {
                nameText.setText("User");
            }

            // Fetch the role from Firestore
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String displayRole = documentSnapshot.getString("role");
                            String displayBio = documentSnapshot.getString("bio");

                            roleText.setText(displayRole != null ? displayRole : "Role not found");
                            bioText.setText(displayBio != null ? displayBio : "No bio");
                        }
                    })
                    .addOnFailureListener(e -> {
                        roleText.setText("Error fetching role");
                        bioText.setText("Error fetching bio");
                    });
        } else {
            nameText.setText("Not logged in");
            roleText.setText("Unknown Role");
            bioText.setText("No bio");
        }
    }


    private void setupRecyclerViews() {
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("completed_courses")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Course> courseList = new ArrayList<>();

                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Course course = document.toObject(Course.class);
                        courseList.add(course);
                    }

                    // Initialize the adapter with the fetched courses
                    CourseAdapter adapter = new CourseAdapter(this, courseList, CourseAdapter.CardSize.SMALL, "profile");
                    completedCourseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    completedCourseRecycler.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(this, "Failed to load completed courses.", Toast.LENGTH_SHORT).show();
                });

        }

        List<Badge> badgeList = new ArrayList<>();
        badgeList.add(new Badge("1", "Java Master", "Completed the Advanced Java Course", "sample_certificate", Collections.emptyList()));
        badgeList.add(new Badge("2", "UI/UX Expert", "Completed the UI/UX Design Course", "sample_certificate2", Collections.emptyList()));

        // Set up BadgeAdapter for displaying badges
        BadgeAdapter badgeAdapter = new BadgeAdapter(this, badgeList);
        achievedBadgeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        achievedBadgeRecycler.setAdapter(badgeAdapter);
    }
}