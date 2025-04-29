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

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, passwordEditText;
    private Button signUpButton;
    private ImageView backArrow;
    private TextView logInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Make sure this XML is saved as activity_login.xml
        View curView = findViewById(R.id.register);
        Utilities.setViewPadding(curView);

        // Initialize views
        nameEditText = findViewById(R.id.editTextName);
        passwordEditText = findViewById(R.id.editTextPassword);
        signUpButton = findViewById(R.id.buttonSignUp);
        backArrow = findViewById(R.id.backArrow);
        logInText = findViewById(R.id.textLogIn);

        signUpButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backArrow.setOnClickListener(v -> finish());

        logInText.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
