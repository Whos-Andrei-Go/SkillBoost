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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;

public class HomepageActivity extends BaseActivity {

    LinearLayout moreCourses;
    ImageView notifications;
    EditText searchBar;
    List<Course> courseList;
    CourseAdapter courseAdapter;
    TextView username;
    RecyclerView recyclerCourses;

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

        courseList = new ArrayList<>();
        courseList.add(new Course("1", "Math Basics", "Introduction to Math", "Lorem Ipsum", new ArrayList<>(), new ArrayList<>(),"course1"));
        courseList.add(new Course("2", "Advanced Java", "Deep dive into OOP", "Lorem Ipsum", new ArrayList<>(), new ArrayList<>(), "course2"));
        courseList.add(new Course("3", "UI/UX Design", "Design modern interfaces", "Lorem Ipsum", new ArrayList<>(), new ArrayList<>(), "course1"));

        courseAdapter = new CourseAdapter(this, courseList, CourseAdapter.CardSize.MEDIUM, "home");
        recyclerCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCourses.setAdapter(courseAdapter);
    }

    private void initViews(){
        moreCourses = findViewById(R.id.morecourses);
        notifications = findViewById(R.id.notifications);
        recyclerCourses = findViewById(R.id.recycler_view_courses);
        searchBar = findViewById(R.id.search_bar);
        username = findViewById(R.id.username);
    }

    private void setupListeners() {
        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
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
}