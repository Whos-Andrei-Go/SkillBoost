package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_homepage);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        searchBar = findViewById(R.id.search_bar);

        courseList = new ArrayList<>();
        courseList.add(new Course(R.drawable.course1, "Math Basics", "Introduction to Math"));
        courseList.add(new Course(R.drawable.course2, "Advanced Java", "Deep dive into OOP"));
        courseList.add(new Course(R.drawable.course1, "UI/UX Design", "Design modern interfaces"));

        adapter = new CourseAdapter(this, courseList, CourseAdapter.CardSize.MEDIUM);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCourses(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        moreCourses = findViewById(R.id.morecourses);

        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });

        notifications = findViewById(R.id.notifications);

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void filterCourses(String query) {
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : courseList) {
            if (course.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    course.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredCourses.add(course);
            }
        }
        adapter.updateCourseList(filteredCourses);
    }
}