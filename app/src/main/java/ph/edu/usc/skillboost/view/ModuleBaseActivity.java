package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import ph.edu.usc.skillboost.R;

public class ModuleBaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    private OnModuleSelectListener moduleSelectListener;
    View headerView;
    ImageView backButton, sidebarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_base);

        initViews();
        setupListeners();
        setupViews();
    }

    private void initViews(){
        // Here is the important part:
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        backButton = headerView.findViewById(R.id.back);
        sidebarButton = headerView.findViewById(R.id.sidebar);
    }
    private void setupListeners(){
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseDetailsActivity.class);
            intent.putExtra("courseId", getIntent().getStringExtra("courseId"));
            intent.putExtra("source", getIntent().getStringExtra("source"));
            startActivity(intent);
        });

        sidebarButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        Map<Integer, String> moduleMap = new HashMap<Integer, String>() {{
            put(R.id.module_1, "Module 1");
            put(R.id.module_2, "Module 2");
            put(R.id.module_3, "Module 3");
            put(R.id.module_4, "Module 4");
            put(R.id.module_5, "Module 5");
            put(R.id.module_exam, "Module Exam");
            put(R.id.certificate, "Certificate");
            put(R.id.other_info, "Other Info");
            put(R.id.about_module, "About This Module");
        }};

        navigationView.setNavigationItemSelectedListener(item -> {
            String moduleName = moduleMap.get(item.getItemId());
            if (moduleName != null && moduleSelectListener != null) {
                moduleSelectListener.onModuleSelected(moduleName);
            } else {
                Log.e("ModuleBaseActivity", "Module not found for ID: " + item.getItemId());
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
    private void setupViews() {
    }

    protected void setOnModuleSelectListener(OnModuleSelectListener listener) {
        this.moduleSelectListener = listener;
    }

    protected interface OnModuleSelectListener {
        void onModuleSelected(String moduleName);
    }

    protected void setActivityContent(int layoutId) {
        FrameLayout content = findViewById(R.id.activity_content);
        if (content != null) {
            getLayoutInflater().inflate(layoutId, content, true);
        }
    }
}
