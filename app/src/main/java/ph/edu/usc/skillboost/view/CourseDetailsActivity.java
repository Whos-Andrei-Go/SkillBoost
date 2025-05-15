package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Module;
import ph.edu.usc.skillboost.utils.Utilities;
import ph.edu.usc.skillboost.view.adapters.ModuleAdapter;
import ph.edu.usc.skillboost.viewmodel.CourseViewModel;

public class CourseDetailsActivity extends AppCompatActivity {

    RecyclerView moduleRecyler;
    List<Module> moduleList;
    ModuleAdapter moduleAdapter;
    Button startCourse;
    ImageView back, courseImage;
    TextView courseTitleText, courseDescriptionText;
    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        setupViews();
    }

    private void initViews() {
        back = findViewById(R.id.back);
        courseDescriptionText = findViewById(R.id.txt_course_description);
        courseTitleText = findViewById(R.id.txt_course_title);
        moduleRecyler = findViewById(R.id.recycler_view_modules);
        startCourse = findViewById(R.id.start_course);
        courseImage = findViewById(R.id.img_course);
    }

    private void setupListeners(){
        String source = getIntent().getStringExtra("source");
        back.setOnClickListener(v -> {
            if (source.equals("home")) {
                startActivity(new Intent(CourseDetailsActivity.this, HomepageActivity.class));
            } else if (source.equals("profile")) {
                startActivity(new Intent(CourseDetailsActivity.this, ProfileActivity.class));
            } else {
                startActivity(new Intent(CourseDetailsActivity.this, CoursesActivity.class));
            }
        });

        startCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailsActivity.this, CourseContentActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("source", source);
                startActivity(intent);
            }
        });
    }

    private void setupViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use android:justificationMode for API level 29 and higher
            courseDescriptionText.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        courseId = getIntent().getStringExtra("courseId");
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.fetchCourse(courseId);

        courseViewModel.getCourse().observe(this, course -> {
            if (course != null) {
                courseTitleText.setText(course.getTitle());
                courseDescriptionText.setText(course.getDescription());
                courseImage.setImageResource(Utilities.getDrawableFromRes(this, course.getImageRes()));
            } else {
                Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
            }
        });

        setupRecyclerViews();
    }

    private void setupRecyclerViews() {
        // Initialize module list
        moduleList = new ArrayList<>();
        moduleList.add(new Module("mod1", "course1", "Welcome & Overview", "Get an overview of what this course covers and how to get started.", "course1.jpg"));
        moduleList.add(new Module("mod2", "course1", "Setting Up Your Tools", "Learn how to install and configure the software needed for this course.", "course1.jpg"));
        moduleList.add(new Module("mod3", "course1", "Core Concepts", "Dive into the key concepts you need to understand before moving forward.", "course1.jpg"));
        moduleList.add(new Module("mod4", "course1", "Hands-On Practice", "Apply what you've learned through guided exercises.", "course2.jpg"));
        moduleList.add(new Module("mod5", "course1", "Course Summary & Next Steps", "Wrap up the course and explore paths for further learning.", "course2.jpg"));

        // Set up RecyclerView
        moduleAdapter = new ModuleAdapter(this, moduleList);
        moduleRecyler.setLayoutManager(new LinearLayoutManager(this));
        moduleRecyler.setAdapter(moduleAdapter);
    }
}