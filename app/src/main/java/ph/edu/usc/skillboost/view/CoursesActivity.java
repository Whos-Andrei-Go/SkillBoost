package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.view.adapters.FilterAdapter;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;
import ph.edu.usc.skillboost.viewmodel.CourseViewModel;

public class CoursesActivity extends BaseActivity {

    RecyclerView filterRecycler, courseRecycler;
    ImageView back, bookmark;
    EditText searchBar;
    private CourseViewModel courseViewModel;
    CourseAdapter courseAdapter;
    FilterAdapter filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_courses);

        initViews();
        setupListeners();
        setupRecyclerViews();
    }

    private void setupListeners() {
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change this into SavedCourses page
                Intent intent = new Intent(CoursesActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        back = findViewById(R.id.back);
        bookmark = findViewById(R.id.savedCourses);
        filterRecycler = findViewById(R.id.filterRecycler);
        courseRecycler = findViewById(R.id.courseRecycler);
        searchBar = findViewById(R.id.search_bar);
    }

    private void setupRecyclerViews() {
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseAdapter = new CourseAdapter(this, new ArrayList<>(), CourseAdapter.CardSize.LARGE, "courses");
        courseRecycler.setLayoutManager(new LinearLayoutManager(CoursesActivity.this));
        courseRecycler.setAdapter(courseAdapter);

        courseViewModel.getAllCourses().observe(this, this::updateCourseList);
        List<String> filters = Arrays.asList("All", "Top Courses", "Recommended", "Recently Added", "Completed Courses");

        filterAdapter = new FilterAdapter(filters, filter -> {
            courseAdapter.filterByCategory(filter);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        filterRecycler.setLayoutManager(layoutManager);
        filterRecycler.setAdapter(filterAdapter);
    }

    private void updateCourseList(List<Course> courses) {
        courseAdapter.updateCourseList(courses);

        String selectedCategory = getIntent().getStringExtra("selectedCategory");

        if (selectedCategory != null) {
            filterAdapter.setSelectedFilter(selectedCategory);
            courseAdapter.filterByCategory(selectedCategory);
        }
    }
}