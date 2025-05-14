package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.view.adapters.BadgeAdapter;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class ProfileActivity extends BaseActivity {
    LinearLayout moreCourses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_profile);

        RecyclerView coursesView = findViewById(R.id.recycler_courses_completed);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("1", "Math Basics", "Introduction to Math", "SOME MATH AHH", new ArrayList<>(), new ArrayList<>(),"course1"));
        courseList.add(new Course("2", "Advanced Java", "Deep dive into OOP", "SOME JAVA AHH", new ArrayList<>(), new ArrayList<>(), "course2"));
        courseList.add(new Course("3", "UI/UX Design", "Design modern interfaces", "SOME DESIGN AHH", new ArrayList<>(), new ArrayList<>(), "course1"));

        CourseAdapter adapter = new CourseAdapter(this, courseList, CourseAdapter.CardSize.SMALL, "profile");
        coursesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        coursesView.setAdapter(adapter);

        moreCourses = findViewById(R.id.more_courses_completed);

        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });

        List<Badge> badgeList = new ArrayList<>();
        badgeList.add(new Badge("1", "Java Master", "Completed the Advanced Java Course", "sample_certificate", Collections.emptyList()));
        badgeList.add(new Badge("2", "UI/UX Expert", "Completed the UI/UX Design Course", "sample_certificate2", Collections.emptyList()));

        // Set up BadgeAdapter for displaying badges
        RecyclerView badgesView = findViewById(R.id.recycler_certificates_achieved);
        BadgeAdapter badgeAdapter = new BadgeAdapter(this, badgeList);
        badgesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        badgesView.setAdapter(badgeAdapter);
    }
}