package ph.edu.usc.skillboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {

    LinearLayout moreCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_profile);

        RecyclerView recyclerView = findViewById(R.id.recycler_courses_completed);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(R.drawable.course1, "Math Basics", "Introduction to Math", true));
        courseList.add(new Course(R.drawable.course2, "Advanced Java", "Deep dive into OOP", true));
        courseList.add(new Course(R.drawable.course1, "UI/UX Design", "Design modern interfaces", true));

        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        moreCourses = findViewById(R.id.more_courses_completed);

        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });
    }
}