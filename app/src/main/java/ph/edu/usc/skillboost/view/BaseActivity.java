package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.widget.TextView;
import android.graphics.PorterDuff;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ph.edu.usc.skillboost.R;

public class BaseActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    protected void setContentLayout(int layoutResID) {
        setContentView(R.layout.activity_base);
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        int[] navIds = {R.id.nav_home, R.id.nav_courses, R.id.nav_badges, R.id.nav_profile};
        int[] iconIds = {R.id.icon_nav_home, R.id.icon_nav_courses, R.id.icon_nav_badges, R.id.icon_nav_profile};
        int[] textIds = {R.id.txt_nav_home, R.id.txt_nav_courses, R.id.txt_nav_badges, R.id.txt_nav_profile};
        Class<?>[] activityClasses = {HomepageActivity.class, CoursesActivity.class, BadgesActivity.class, ProfileActivity.class};

        for (int i = 0; i < navIds.length; i++) {
            LinearLayout navItem = findViewById(navIds[i]);
            TextView text = navItem.findViewById(textIds[i]);

            text.setTextColor(ContextCompat.getColor(this, R.color.silver));
            resetBackground(navItem);

            final int index = i;
            navItem.setOnClickListener(view -> {
                if (!(this.getClass().equals(activityClasses[index]))) {
                    startActivity(new Intent(this, activityClasses[index]));
                    overridePendingTransition(0, 0);
                }
            });
        }

        Class<?> currentActivityClass = this.getClass();
        for (int i = 0; i < activityClasses.length; i++) {
            if (currentActivityClass.equals(activityClasses[i])) {
                LinearLayout navItem = findViewById(navIds[i]);
                ImageView icon = navItem.findViewById(iconIds[i]);
                TextView text = navItem.findViewById(textIds[i]);

                icon.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_IN); // Set tint for selected state
                text.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                setSelectedBackground(navItem);
                break;
            }
        }
    }

    private void setSelectedBackground(LinearLayout navItem) {
        navItem.setBackgroundResource(R.drawable.bg_selected_nav_item);
    }

    private void resetBackground(LinearLayout navItem) {
        navItem.setBackgroundResource(0);
    }
}