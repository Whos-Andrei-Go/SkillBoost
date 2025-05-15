package ph.edu.usc.skillboost.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.utils.Utilities;
import ph.edu.usc.skillboost.view.adapters.BadgeAdapter;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;

public class ProfileActivity extends BaseActivity {
    LinearLayout moreCourses, moreCertificates;
    ImageView settingsIcon;
    RecyclerView completedCourseRecycler;
    RecyclerView achievedCertificateRecycler;
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
        completedCourseRecycler = findViewById(R.id.completedCourseRecycler);
        moreCourses = findViewById(R.id.more_courses_completed);
        moreCertificates = findViewById(R.id.more_certificates_achieved);
        achievedCertificateRecycler = findViewById(R.id.achievedCertificateRecycler);
        nameText = findViewById(R.id.txt_name);
        roleText = findViewById(R.id.txt_role);
        bioText = findViewById(R.id.txt_bio);
    }
    private void setupListeners() {
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                intent.putExtra("selectedCategory", "Your Awards");
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

        moreCertificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BadgesActivity.class);
                intent.putExtra("selectedCategory", "Your Awards");
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

            db.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            List<String> completedCourses = (List<String>) documentSnapshot.get("completedCourses");

                            if (completedCourses != null) {
                                // Load and filter courses
                                db.collection("courses").get().addOnSuccessListener(queryDocumentSnapshots -> {
                                    List<Course> courseList = new ArrayList<>();

                                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                                        Course course = doc.toObject(Course.class);
                                        if (completedCourses.contains(course.getCourseId())) {
                                            courseList.add(course);
                                        }
                                    }

                                    // Set course adapter
                                    CourseAdapter adapter = new CourseAdapter(this, courseList, CourseAdapter.CardSize.SMALL, "profile");
                                    completedCourseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                                    completedCourseRecycler.setAdapter(adapter);
                                });

                                // Load badges and filter by completed courses
                                db.collection("badges").get().addOnSuccessListener(querySnapshot -> {
                                    List<Badge> badgeList = new ArrayList<>();

                                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                                        Badge badge = doc.toObject(Badge.class);
                                        if (badge != null && completedCourses.contains(badge.getCourseId())) {
                                            badgeList.add(badge);
                                        }
                                    }

                                    BadgeAdapter badgeAdapter = new BadgeAdapter(this, badgeList);
                                    achievedCertificateRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                                    achievedCertificateRecycler.setAdapter(badgeAdapter);
                                    badgeAdapter.setOnBadgeClickListener(badge -> showBadgeDialog(badge));
                                });
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirestoreError", "Error fetching user data", e);
                    });
        }
    }

    private void showBadgeDialog(Badge badge) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_badge_details);

        ImageView imageView = dialog.findViewById(R.id.dialog_badge_image);
        TextView title = dialog.findViewById(R.id.dialog_badge_title);
        TextView subtitle = dialog.findViewById(R.id.dialog_badge_subtitle);
        TextView closeBtn = dialog.findViewById(R.id.dialog_close);

        imageView.setImageResource(Utilities.getDrawableFromRes(this, badge.getImageRes()));
        title.setText(badge.getTitle());
        subtitle.setText(badge.getSubtitle());

        closeBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}