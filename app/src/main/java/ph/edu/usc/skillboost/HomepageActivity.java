package ph.edu.usc.skillboost;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {

    LinearLayout navHome = findViewById(R.id.nav_home);
    LinearLayout navCourses = findViewById(R.id.nav_courses);
    LinearLayout navBadges = findViewById(R.id.nav_badges);
    LinearLayout navProfile = findViewById(R.id.nav_profile);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(R.drawable.course1, "Math Basics", "Introduction to Math"));
        courseList.add(new Course(R.drawable.course2, "Advanced Java", "Deep dive into OOP"));
        courseList.add(new Course(R.drawable.course1, "UI/UX Design", "Design modern interfaces"));

        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        List<LinearLayout> navItems = Arrays.asList(navHome, navCourses, navBadges, navProfile);

        for (LinearLayout item : navItems) {
            item.setOnClickListener(v -> {
                for (LinearLayout nav : navItems) {
                    nav.setBackgroundColor(Color.TRANSPARENT); // Unselect
                    TextView text = (TextView) nav.getChildAt(1);
                    text.setTextColor(Color.parseColor("#7B6F8E")); // Unselected color
                }

                v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_selected_nav_item));
                TextView selectedText = (TextView) ((LinearLayout) v).getChildAt(1);
                selectedText.setTextColor(Color.WHITE);
            });
        }


    }
}