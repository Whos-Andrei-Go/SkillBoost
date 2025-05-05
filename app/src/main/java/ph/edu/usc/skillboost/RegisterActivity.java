package ph.edu.usc.skillboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ph.edu.usc.skillboost.view.LoginActivity;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText nameEditText, passwordEditText, emailEditText;
    private Button signUpButton;
    private ImageView backArrow;
    private TextView logInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Make sure this XML is saved as activity_login.xml
        View curView = findViewById(R.id.register);
        Utilities.setViewPadding(curView);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        passwordEditText = findViewById(R.id.editTextPassword);
        emailEditText = findViewById(R.id.editTextEmail);
        signUpButton = findViewById(R.id.buttonSignUp);
        backArrow = findViewById(R.id.backArrow);
        logInText = findViewById(R.id.textLogIn);

        signUpButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty()) {
                emailEditText.setError("Enter email");
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty() || password.length() < 6) {
                passwordEditText.setError("Password must be 6+ chars");
                passwordEditText.requestFocus();
                return;
            }

            if (name.isEmpty()) {
                nameEditText.setError("Enter name");
                nameEditText.requestFocus();
                return;
            }

            authViewModel.register(email, password, name);
        });

        backArrow.setOnClickListener(v -> finish());

        logInText.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        authViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomepageActivity.class));
                finish();
            }
        });

        authViewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
