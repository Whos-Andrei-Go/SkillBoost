package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.utils.Utilities;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText nameEditText, passwordEditText, emailEditText, bioEditText;
    private Button signUpButton;
    private ImageView backArrow;
    private TextView logInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View curView = findViewById(R.id.register);
        Utilities.setViewPadding(curView);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        passwordEditText = findViewById(R.id.editTextPassword);
        emailEditText = findViewById(R.id.editTextEmail);
        bioEditText = findViewById(R.id.editTextBio);
        signUpButton = findViewById(R.id.buttonSignUp);
        backArrow = findViewById(R.id.backArrow);
        logInText = findViewById(R.id.textLogIn);

        signUpButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String bio = bioEditText.getText().toString().trim();

            // Check if email is empty
            if (email.isEmpty()) {
                emailEditText.setError("Enter email");
                emailEditText.requestFocus();
                return;
            }

            // Check if the email format is valid
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Enter a valid email address");
                emailEditText.requestFocus();
                return;
            }

            // Check if password is empty or too short
            if (password.isEmpty() || password.length() < 6) {
                passwordEditText.setError("Password must be 6+ chars");
                passwordEditText.requestFocus();
                return;
            }

            // Check if name is empty
            if (name.isEmpty()) {
                nameEditText.setError("Enter name");
                nameEditText.requestFocus();
                return;
            }

            // Handle bio (allow empty)
            if (bio.isEmpty()) {
                bio = "";
            }

            Intent intent = new Intent(RegisterActivity.this, PreferencesActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("bio", bio);
            startActivity(intent);
        });


        backArrow.setOnClickListener(v -> finish());

        logInText.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        // Observe error messages and success (email sent)
        authViewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                // Show verification message OR general error
                if (error.toLowerCase().contains("verification email")) {
                    new AlertDialog.Builder(this)
                            .setTitle("Verify Your Email")
                            .setMessage(error)
                            .setCancelable(false)
                            .setPositiveButton("Go to Login", (dialog, which) -> {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            })
                            .show();
                } else {
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Prevent navigating to homepage unless user is already verified (which shouldn't happen here)
        authViewModel.getUserLiveData().observe(this, user -> {
            // Optional: Only navigate if user is verified (for safety)

            if (user != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(this, HomepageActivity.class));
                finish();
            }

        });
    }
}
