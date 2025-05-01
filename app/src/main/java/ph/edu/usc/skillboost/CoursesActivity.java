package ph.edu.usc.skillboost;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoursesActivity extends BaseActivity {

    RecyclerView filterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(R.drawable.course1, "Math Basics", "Introduction to Math"));
        courseList.add(new Course(R.drawable.course2, "Advanced Java", "Deep dive into OOP"));
        courseList.add(new Course(R.drawable.course1, "UI/UX Design", "Design modern interfaces"));

        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        filterRecycler = findViewById(R.id.filterRecycler);
        List<String> filters = Arrays.asList("All", "Top Courses", "Recommended", "Recently Added", "Other");

        FilterAdapter filterAdapter = new FilterAdapter(filters, filter -> {
            // TODO: Handle filter click - update RecyclerView contents, etc.
            Toast.makeText(this, "Selected: " + filter, Toast.LENGTH_SHORT).show();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        filterRecycler.setLayoutManager(layoutManager);
        filterRecycler.setAdapter(filterAdapter);

    }
}