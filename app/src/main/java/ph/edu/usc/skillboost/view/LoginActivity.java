package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.utils.Utilities;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class LoginActivity extends ComponentActivity {

    private EditText emailEditText, passwordEditText;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (authViewModel.isUserLoggedIn()) {
            //Already logged in -> Skip log in
            Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_login); // Make sure this XML is saved as activity_login.xml
        View curView = findViewById(R.id.login);
        Utilities.setViewPadding(curView);

        // Initialize views
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        Button signInButton = findViewById(R.id.buttonSignIn);
        TextView registerNowText = findViewById(R.id.textRegisterNow);

        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty()) {
                emailEditText.setError("Email is required");
                emailEditText.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Please enter a valid email address");
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
                return;
            }


            authViewModel.login(email, password);
        });

        authViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Welcome back, " + user.getEmail(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        authViewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        registerNowText.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}