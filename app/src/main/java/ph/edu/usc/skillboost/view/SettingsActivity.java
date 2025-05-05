package ph.edu.usc.skillboost.view;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class SettingsActivity extends BaseActivity{
    Switch switchPush, switchPromotions, switchUpdates;
    TextView editProfile, changePassword, securityPrivacy, logout;
    ImageView backArrow;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find Views
        switchPush = findViewById(R.id.switchPush);
        switchPromotions = findViewById(R.id.switchPromotions);
        switchUpdates = findViewById(R.id.switchUpdates);

        editProfile = findViewById(R.id.editProfile);
        changePassword = findViewById(R.id.changePassword);
        securityPrivacy = findViewById(R.id.securityPrivacy);
        logout = findViewById(R.id.logout);
        backArrow = findViewById(R.id.backArrow);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        editProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, EditProfileActivity.class));
        });

        changePassword.setOnClickListener(v -> {
            Toast.makeText(this, "Change Password Clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, ChangePasswordActivity.class));
        });

        securityPrivacy.setOnClickListener(v -> {
            Toast.makeText(this, "Security & Privacy Clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, SecurityPrivacyActivity.class));
        });

        logout.setOnClickListener(v -> {
            authViewModel.logout(this);

            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


        // Switch Change Listeners
        switchPush.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "Push Notifications " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        switchPromotions.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "Promotions " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        switchUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "App Updates " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });
    }
}
