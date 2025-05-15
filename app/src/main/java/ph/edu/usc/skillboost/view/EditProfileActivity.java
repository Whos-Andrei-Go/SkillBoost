package ph.edu.usc.skillboost.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ph.edu.usc.skillboost.R;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PROFILE_PREFS = "ProfilePrefs";
    private static final String PROFILE_IMAGE_KEY = "profile_image";

    private ImageView backArrow;
    private TextView saveButton;
    private ImageView profileImage;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText genderEditText;
    private EditText dobEditText;
    private EditText bioEditText;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private Calendar calendar;
    private Uri imageUri;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();
        sharedPreferences = getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);

        // Initialize views
        backArrow = findViewById(R.id.backArrow);
        saveButton = findViewById(R.id.saveButton);
        profileImage = findViewById(R.id.profileimg);
        nameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        genderEditText = findViewById(R.id.gender);
        dobEditText = findViewById(R.id.dob);
        bioEditText = findViewById(R.id.bio);

        // Set click listeners
        backArrow.setOnClickListener(v -> finish());
        saveButton.setOnClickListener(v -> saveProfile());
        profileImage.setOnClickListener(v -> openImageChooser());

        // Set up date picker for DOB
        dobEditText.setOnClickListener(v -> showDatePickerDialog());

        // Load current user data
        loadUserData();
        loadLocalProfileImage();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
                saveProfileImageLocally(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfileImageLocally(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_IMAGE_KEY, encodedImage);
        editor.apply();
    }

    private void loadLocalProfileImage() {
        String encodedImage = sharedPreferences.getString(PROFILE_IMAGE_KEY, null);
        if (encodedImage != null) {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profileImage.setImageBitmap(bitmap);
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDobLabel();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDobLabel() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        dobEditText.setText(sdf.format(calendar.getTime()));
    }

    private void loadUserData() {
        if (currentUser != null) {
            // Load email from Auth
            emailEditText.setText(currentUser.getEmail());

            // Load user data from Firestore
            DocumentReference userRef = db.collection("users").document(currentUser.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // Handle name (your main display name)
                    nameEditText.setText(documentSnapshot.getString("name"));

                    // Handle bio
                    bioEditText.setText(documentSnapshot.getString("bio"));

                    // Handle dob (stored as long in your DB)
                    Long dob = documentSnapshot.getLong("dob"); // Read the dob as long
                    if (dob != null) {
                        // Convert the long timestamp to a formatted string (Optional)
                        // For example, you could format it as "dd/MM/yyyy"
                        Date date = new Date(dob);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        dobEditText.setText(sdf.format(date));
                    } else {
                        dobEditText.setText(""); // Handle null or missing value
                    }

                    // Handle gender
                    genderEditText.setText(documentSnapshot.getString("gender"));

                    // Handle phone (if you add this field later)
                    if (documentSnapshot.contains("phone")) {
                        phoneEditText.setText(documentSnapshot.getString("phone"));
                    }
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
                Log.e("EditProfile", "Error loading profile", e);
            });
        }
    }

    private void saveProfile() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String dobString = dobEditText.getText().toString().trim(); // Read the string from EditText
        String bio = bioEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        // Convert dobString to a Unix timestamp if it's not empty
        Long dob = null;
        if (!dobString.isEmpty()) {
            try {
                // Example: Parsing the dob string to a Unix timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = sdf.parse(dobString);
                if (date != null) {
                    dob = date.getTime(); // Convert to Unix timestamp (milliseconds)
                }
            } catch (ParseException e) {
                dobEditText.setError("Invalid date format");
                return;
            }
        }

        // Create the map for Firestore update
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("bio", bio);
        updates.put("gender", gender);
        updates.put("dob", dob != null ? dob : 0L); // If dob is null, use 0 as default

        // Only update phone if field exists and is not empty
        if (!TextUtils.isEmpty(phone)) {
            updates.put("phone", phone);
        }

        // Update Firestore
        db.collection("users").document(currentUser.getUid())
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Update email in Auth if changed
                    // Update display name in FirebaseAuth too
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("EditProfile", "FirebaseAuth displayName updated.");
                                }
                            });

                    // Update email if needed
                    if (!email.equals(currentUser.getEmail())) {
                        currentUser.updateEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        showSuccess();
                                    } else {
                                        showPartialSuccessEmail();
                                    }
                                });
                    } else {
                        showSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    Log.e("EditProfile", "Error updating profile", e);
                });
    }


    private void showSuccess() {
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showPartialSuccessEmail() {
        Toast.makeText(this,
                "Profile updated but email change failed. Please verify your current email",
                Toast.LENGTH_LONG).show();
        finish();
    }

    private void updateFirestoreProfile(String username, String email, String phone, String gender, String dob, String bio) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("gender", gender);

        // Handle date conversion if needed
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date = sdf.parse(dob);
            userData.put("dob", new com.google.firebase.Timestamp(date));
        } catch (Exception e) {
            // If date parsing fails, store as string
            userData.put("dob", dob);
        }

        userData.put("bio", bio);

        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .update(userData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("EditProfile", "Error updating profile", e);
                    });
        }
    }
}