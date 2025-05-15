package ph.edu.usc.skillboost.view;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ph.edu.usc.skillboost.R;

public class CourseContentActivity extends ModuleBaseActivity {

    ImageView sidebarToggle;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setActivityContent(R.layout.activity_course_content);

        initViews();
        setupListeners();
        setupViews();
    }

    private void initViews(){
        sidebarToggle = findViewById(R.id.sidebar);
        headerText = findViewById(R.id.txt_header);
    }

    private void setupListeners(){
        sidebarToggle.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setOnModuleSelectListener(moduleName -> {
            // Update the header text when a module is selected
            headerText.setText(moduleName);
        });
    }

    private void setupViews(){

    }
}