package ph.edu.usc.skillboost.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import ph.edu.usc.skillboost.R;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView back = findViewById(R.id.back);
        TextView editProfile = findViewById(R.id.edit_profile);
        TextView changePassword = findViewById(R.id.change_password);
        TextView securityPrivacy = findViewById(R.id.security_privacy);
        Switch pushNotifications = findViewById(R.id.push_notifications);
        Switch promotions = findViewById(R.id.promotions);
        Switch appUpdates = findViewById(R.id.app_updates);
        TextView logout = findViewById(R.id.logout);

        back.setOnClickListener(v -> onBackPressed());

        editProfile.setOnClickListener(v -> {
            // Navigate to Edit Profile
        });

        changePassword.setOnClickListener(v -> {
            // Navigate to Change Password
        });

        securityPrivacy.setOnClickListener(v -> {
            // Navigate to Security & Privacy
        });

        pushNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle push notification toggle
        });

        promotions.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle promotions toggle
        });

        appUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle app updates toggle
        });

        logout.setOnClickListener(v -> {
            // Handle logout
        });
    }
}
