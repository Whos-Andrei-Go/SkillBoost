package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class SettingsActivity extends BaseActivity {
    private Switch switchPush, switchPromotions, switchUpdates;
    private TextView editProfile, changePassword, securityPrivacy,logout;
    private ImageView backArrow;
    private LinearLayout passwordChangeForm;
    private AuthViewModel authViewModel;
    private boolean isPasswordFormVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        initViews();
        setupPasswordChangeForm();
        setupClickListeners();
        setupSwitchListeners();
    }

    private void initViews() {
        switchPush = findViewById(R.id.switchPush);
        switchPromotions = findViewById(R.id.switchPromotions);
        switchUpdates = findViewById(R.id.switchUpdates);

        editProfile = findViewById(R.id.editProfile);
        changePassword = findViewById(R.id.changePassword);
        securityPrivacy = findViewById(R.id.securityPrivacy);
        logout = findViewById(R.id.logout);
        backArrow = findViewById(R.id.backArrow);
        passwordChangeForm = findViewById(R.id.passwordChangeForm);

        TextView securityPrivacyHeader = findViewById(R.id.securityPrivacy);
        final LinearLayout securityPrivacyContent = findViewById(R.id.securityPrivacyContent);
        TextView permissionsManagement = findViewById(R.id.permissionsManagement);
        TextView dataCollectionPrefs = findViewById(R.id.dataCollectionPrefs);
        TextView deleteAccount = findViewById(R.id.deleteAccount);

        permissionsManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Permissions Management click
            }
        });

        dataCollectionPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Data Collection Preferences click
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Delete Account click
            }
        });


        securityPrivacyHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSecurityPrivacySection(securityPrivacyContent);
            }
        });

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void setupPasswordChangeForm() {
        // Initially hide the password change form
        passwordChangeForm.setVisibility(View.GONE);

        Button btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        btnUpdatePassword.setOnClickListener(v -> validateAndChangePassword());
    }

    private void toggleSecurityPrivacySection(final LinearLayout content) {
        if (content.getVisibility() == View.VISIBLE) {
            // Collapse with slide-up animation
            Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    content.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            content.startAnimation(slideUp);
        } else {
            // Expand with slide-down animation
            content.setVisibility(View.VISIBLE);
            Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            content.startAnimation(slideDown);
        }
    }


    private void validateAndChangePassword() {
        EditText currentPassword = findViewById(R.id.currentPassword);
        EditText newPassword = findViewById(R.id.newPassword);
        EditText confirmNewPassword = findViewById(R.id.confirmNewPassword);

        String currentPass = currentPassword.getText().toString().trim();
        String newPass = newPassword.getText().toString().trim();
        String confirmPass = confirmNewPassword.getText().toString().trim();

        if (currentPass.isEmpty()) {
            currentPassword.setError("Current password is required");
            currentPassword.requestFocus();
            return;
        }

        if (newPass.isEmpty()) {
            newPassword.setError("New password is required");
            newPassword.requestFocus();
            return;
        }

        if (newPass.length() < 6) {
            newPassword.setError("Password must be at least 6 characters");
            newPassword.requestFocus();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            confirmNewPassword.setError("Passwords don't match");
            confirmNewPassword.requestFocus();
            return;
        }

        // Call ViewModel to change password
        authViewModel.changePassword(currentPass, newPass).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                togglePasswordForm(); // Hide the form after success
                clearPasswordFields();
            } else {
                Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearPasswordFields() {
        ((EditText) findViewById(R.id.currentPassword)).setText("");
        ((EditText) findViewById(R.id.newPassword)).setText("");
        ((EditText) findViewById(R.id.confirmNewPassword)).setText("");
    }

    private void togglePasswordForm() {
        if (isPasswordFormVisible) {
            // Hide the form with animation
            Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            passwordChangeForm.startAnimation(slideUp);
            passwordChangeForm.setVisibility(View.GONE);
        } else {
            // Show the form with animation
            passwordChangeForm.setVisibility(View.VISIBLE);
            Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            passwordChangeForm.startAnimation(slideDown);
        }
        isPasswordFormVisible = !isPasswordFormVisible;
    }

    private void setupClickListeners() {
        editProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, EditProfileActivity.class));
        });

        changePassword.setOnClickListener(v -> togglePasswordForm());


        logout.setOnClickListener(v -> {
            authViewModel.logout(this);
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        backArrow.setOnClickListener(v -> onBackPressed());
    }

    private void setupSwitchListeners() {
        switchPush.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "Push Notifications " + (isChecked ? "Enabled" : "Disabled"),
                    Toast.LENGTH_SHORT).show();
            // Save preference
        });

        switchPromotions.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "Promotions " + (isChecked ? "Enabled" : "Disabled"),
                    Toast.LENGTH_SHORT).show();
            // Save preference
        });

        switchUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "App Updates " + (isChecked ? "Enabled" : "Disabled"),
                    Toast.LENGTH_SHORT).show();
            // Save preference
        });
    }
}