package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class ProfileActivity extends BaseActivity {
    private AuthViewModel authViewModel;
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_profile);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        logoutButton = findViewById(R.id.btnlogout);

        logoutButton.setOnClickListener(v -> {
            authViewModel.logout();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}