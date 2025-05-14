package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ph.edu.usc.skillboost.R;

public class ModuleBaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_base);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Set up drawer header buttons
        View header = navigationView.getHeaderView(0);

        // Back button
        ImageView backButton = header.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseDetailsActivity.class);
            intent.putExtra("courseId", getIntent().getStringExtra("courseId"));
            intent.putExtra("source", getIntent().getStringExtra("source"));
            startActivity(intent);
        });

        // Sidebar close button
        ImageView sidebarButton = header.findViewById(R.id.sidebar);
        sidebarButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // Handle navigation menu item clicks here
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    protected void setActivityContent(int layoutId) {
        FrameLayout content = findViewById(R.id.activity_content);
        if (content != null) {
            getLayoutInflater().inflate(layoutId, content, true);
        }
    }
}