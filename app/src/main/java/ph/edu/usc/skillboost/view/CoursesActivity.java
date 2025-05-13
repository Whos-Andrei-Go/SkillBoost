package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView back, bookmark;
    EditText searchBar;
    List<Course> courseList;
    CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        searchBar = findViewById(R.id.search_bar);

        courseList = new ArrayList<>();
        courseList.add(new Course("1", "Math Basics", "Introduction to Math", new ArrayList<>(), new ArrayList<>(),R.drawable.course1));
        courseList.add(new Course("2", "Advanced Java", "Deep dive into OOP", new ArrayList<>(), new ArrayList<>(), R.drawable.course2));
        courseList.add(new Course("3", "UI/UX Design", "Design modern interfaces", new ArrayList<>(), new ArrayList<>(), R.drawable.course1));

        adapter = new CourseAdapter(this, courseList, CourseAdapter.CardSize.LARGE, "courses");
        recyclerView.setLayoutManager(new LinearLayoutManager(CoursesActivity.this));
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

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            // Consume the "Enter" key press event
            return true;
        });


        filterRecycler = findViewById(R.id.filterRecycler);
        List<String> filters = Arrays.asList("All", "Top Courses", "Recommended", "Recently Added", "Other");

        FilterAdapter filterAdapter = new FilterAdapter(filters, filter -> {
            // TODO: Handle filter click - update RecyclerView contents, etc.
            Toast.makeText(this, "Selected: " + filter, Toast.LENGTH_SHORT).show();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        filterRecycler.setLayoutManager(layoutManager);
        filterRecycler.setAdapter(filterAdapter);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        bookmark = findViewById(R.id.savedCourses);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change this into SavedCourses page
                Intent intent = new Intent(CoursesActivity.this, HomepageActivity.class);
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