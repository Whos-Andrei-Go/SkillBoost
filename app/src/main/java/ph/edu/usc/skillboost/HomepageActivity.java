package ph.edu.usc.skillboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends BaseActivity {

    LinearLayout moreCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_homepage);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(R.drawable.course1, "Math Basics", "Introduction to Math"));
        courseList.add(new Course(R.drawable.course2, "Advanced Java", "Deep dive into OOP"));
        courseList.add(new Course(R.drawable.course1, "UI/UX Design", "Design modern interfaces"));

        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        moreCourses = findViewById(R.id.morecourses);

        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });

    }
}