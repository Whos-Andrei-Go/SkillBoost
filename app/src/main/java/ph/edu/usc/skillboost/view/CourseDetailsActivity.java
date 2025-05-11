package ph.edu.usc.skillboost.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Module;
import ph.edu.usc.skillboost.view.adapters.ModuleAdapter;

public class CourseDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerViewModules;
    List<Module> moduleList;
    ModuleAdapter moduleAdapter;


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

        recyclerViewModules = findViewById(R.id.recycler_view_modules);

        // Initialize module list
        moduleList = new ArrayList<>();
        moduleList.add(new Module("mod1", "course1", "Welcome & Overview", "Get an overview of what this course covers and how to get started.", "course1.jpg"));
        moduleList.add(new Module("mod2", "course1", "Setting Up Your Tools", "Learn how to install and configure the software needed for this course.", "course1.jpg"));
        moduleList.add(new Module("mod3", "course1", "Core Concepts", "Dive into the key concepts you need to understand before moving forward.", "course1.jpg"));
        moduleList.add(new Module("mod4", "course1", "Hands-On Practice", "Apply what you've learned through guided exercises.", "course2.jpg"));
        moduleList.add(new Module("mod5", "course1", "Course Summary & Next Steps", "Wrap up the course and explore paths for further learning.", "course2.jpg"));

        // Set up RecyclerView
        moduleAdapter = new ModuleAdapter(this, moduleList);
        recyclerViewModules.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewModules.setAdapter(moduleAdapter);
    }
}