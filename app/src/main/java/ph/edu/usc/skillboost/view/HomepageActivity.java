package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Topic;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;
import ph.edu.usc.skillboost.viewmodel.CourseViewModel;

public class HomepageActivity extends BaseActivity {
    TextView username;
    ImageView notifications;
    EditText searchBar;
    LinearLayout moreCourses, moreRecommended;
    RecyclerView courseRecycler, recommendedCourseRecycler;
    CourseAdapter topCoursesAdapter;
    CourseAdapter recommendedCoursesAdapter;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_homepage);

        initViews();
        setupListeners();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                username.setText("Hello, " + displayName);
            } else {
                username.setText("Hello, User");
            }
        } else {
            username.setText("Not logged in");
        }

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // Setup Top Courses RecyclerView & Adapter
        topCoursesAdapter = new CourseAdapter(this, new ArrayList<>(), CourseAdapter.CardSize.MEDIUM, "home");
        courseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        courseRecycler.setAdapter(topCoursesAdapter);

        // Setup Recommended Courses RecyclerView & Adapter
        recommendedCoursesAdapter = new CourseAdapter(this, new ArrayList<>(), CourseAdapter.CardSize.MEDIUM, "home");
        recommendedCourseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recommendedCourseRecycler.setAdapter(recommendedCoursesAdapter);

        // Observe courses and update both lists
        courseViewModel.getAllCourses().observe(this, courses -> {
            updateTopCoursesList(courses);
            updateRecommendedCoursesList(courses);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                username.setText("Hello, " + displayName);
            } else {
                username.setText("Hello, User");
            }
        }
    }

    private void initViews() {
        moreCourses = findViewById(R.id.morecourses);
        moreRecommended = findViewById(R.id.morerecommended);
        notifications = findViewById(R.id.notifications);
        courseRecycler = findViewById(R.id.recycler_view_courses);
        recommendedCourseRecycler = findViewById(R.id.recycler_view_recommended);
        searchBar = findViewById(R.id.search_bar);
        username = findViewById(R.id.username);
    }

    private void setupListeners() {
        moreCourses.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
            intent.putExtra("selectedCategory", "Top Courses");
            startActivity(intent);
        });

        moreRecommended.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
            intent.putExtra("selectedCategory", "Recommended");
            startActivity(intent);
        });

        notifications.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter both adapters based on search query
                topCoursesAdapter.filter(s.toString());
                recommendedCoursesAdapter.filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        searchBar.setOnEditorActionListener((v, actionId, event) -> true);
    }

    private void updateTopCoursesList(List<Course> courses) {
        List<Course> topCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCategories() != null && course.getCategories().contains("Top Courses")) {
                topCourses.add(course);
            }
        }
        topCoursesAdapter.updateCourseList(topCourses);
    }

    private void updateRecommendedCoursesList(List<Course> courses) {
        getUserPreferences(userPreferences -> {
            List<Course> recommendedCourses = new ArrayList<>();
            List<String> preferredTopicNames = new ArrayList<>();

            for (Topic topic : userPreferences) {
                preferredTopicNames.add(topic.getName());
            }

            for (Course course : courses) {
                if (course.getTags() != null) {
                    for (String tag : course.getTags()) {
                        if (preferredTopicNames.contains(tag)) {
                            recommendedCourses.add(course);
                            break;
                        }
                    }
                }
            }

            // Update adapter on the main/UI thread if needed
            runOnUiThread(() -> recommendedCoursesAdapter.updateCourseList(recommendedCourses));
        });
    }

    public interface PreferencesCallback {
        void onCallback(List<Topic> userPreferences);
    }

    private void getUserPreferences(PreferencesCallback callback) {
        List<Topic> userPreferences = new ArrayList<>();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            List<Map<String, Object>> preferenceMaps = (List<Map<String, Object>>) documentSnapshot.get("preferences");

                            if (preferenceMaps != null) {
                                for (Map<String, Object> map : preferenceMaps) {
                                    String name = (String) map.get("name");
                                    Topic topic = new Topic(name);
                                    userPreferences.add(topic);
                                }
                            }
                        }
                        callback.onCallback(userPreferences);
                    })
                    .addOnFailureListener(e -> {
                        // In case of failure, just send an empty list or handle error
                        callback.onCallback(userPreferences);
                    });
        } else {
            // User not logged in, return empty list immediately
            callback.onCallback(userPreferences);
        }
    }

}
