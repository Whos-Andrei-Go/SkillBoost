package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.view.adapters.CourseAdapter;

public class HomepageActivity extends BaseActivity {

    LinearLayout moreCourses;
    ImageView notifications;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_homepage);

        username = findViewById(R.id.username);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // User is logged in, get the display name
            String displayName = currentUser.getDisplayName();

            // Update the TextView
            if (displayName != null && !displayName.isEmpty()) {
                username.setText("Hello, " + displayName);
            } else {
                username.setText("Hello, User"); // Or some default text
            }
        } else {
            username.setText("Not logged in");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("1", "Math Basics", "Introduction to Math", new ArrayList<>(), new ArrayList<>(),R.drawable.course1));
        courseList.add(new Course("2", "Advanced Java", "Deep dive into OOP", new ArrayList<>(), new ArrayList<>(), R.drawable.course2));
        courseList.add(new Course("3", "UI/UX Design", "Design modern interfaces", new ArrayList<>(), new ArrayList<>(), R.drawable.course1));

        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        moreCourses = findViewById(R.id.morecourses);

        moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });

        notifications = findViewById(R.id.notifications);

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });



    }
}