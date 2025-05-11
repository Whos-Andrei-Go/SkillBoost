package ph.edu.usc.skillboost.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.view.adapters.FilterAdapter;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;

public class CoursesActivity extends BaseActivity {

    RecyclerView filterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("1", "Math Basics", "Introduction to Math", new ArrayList<>(), new ArrayList<>(),R.drawable.course1));
        courseList.add(new Course("2", "Advanced Java", "Deep dive into OOP", new ArrayList<>(), new ArrayList<>(), R.drawable.course2));
        courseList.add(new Course("3", "UI/UX Design", "Design modern interfaces", new ArrayList<>(), new ArrayList<>(), R.drawable.course1));

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