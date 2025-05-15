package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;
import ph.edu.usc.skillboost.viewmodel.CourseViewModel;

public class HomepageActivity extends BaseActivity {
    TextView username;
    ImageView notifications;
    EditText searchBar;
    LinearLayout moreCourses;
    RecyclerView courseRecycler;
    CourseAdapter courseAdapter;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_homepage);

        initViews();
        setupListeners();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // User is logged in, get the display name
            String displayName = currentUser.getDisplayName();

            // Update the TextView
            if (displayName != null && !displayName.isEmpty()) {
                username.setText("Hello, " + displayName);
            } else {
                username.setText("Hello, User"); // Or some default text
            }
        } else {
            username.setText("Not logged in");
        }

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseAdapter = new CourseAdapter(this, new ArrayList<>(), CourseAdapter.CardSize.MEDIUM, "home");
        courseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        courseRecycler.setAdapter(courseAdapter);

        courseViewModel.getAllCourses().observe(this, this::updateTopCoursesList);
    }
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

    private void initViews(){
        moreCourses = findViewById(R.id.morecourses);
        notifications = findViewById(R.id.notifications);
        courseRecycler = findViewById(R.id.recycler_view_courses);
        searchBar = findViewById(R.id.search_bar);
        username = findViewById(R.id.username);
    }

    private void setupListeners() {
        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
                intent.putExtra("selectedCategory", "Top Courses");
                startActivity(intent);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                courseAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            // Consume the "Enter" key press event
            return true;
        });
    }

    private void updateTopCoursesList(List<Course> courses) {
        List<Course> topCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCategories() != null && course.getCategories().contains("Top Courses")) {
                topCourses.add(course);
            }
        }

        courseAdapter.updateCourseList(topCourses);
    }

}