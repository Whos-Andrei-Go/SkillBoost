package ph.edu.usc.skillboost.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ph.edu.usc.skillboost.model.User;

public class AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db;
    private final MutableLiveData<User> userLiveData;
    private final MutableLiveData<String> errorLiveData;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            db.collection("users").document(firebaseUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            userLiveData.setValue(user);
                            Log.d("AuthRepository", "User loaded on init: " + user.getName());
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("AuthRepository", "Failed to fetch user on init", e);
                        errorLiveData.setValue("Failed to load user.");
                    });
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            db.collection("users").document(firebaseUser.getUid())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            User user = documentSnapshot.toObject(User.class);
                                            userLiveData.setValue(user);
                                            Log.d("AuthRepository", "Login success for: " + email);
                                        } else {
                                            errorLiveData.setValue("User profile not found.");
                                            userLiveData.setValue(null);
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        errorLiveData.setValue("Failed to fetch user data: " + e.getMessage());
                                        userLiveData.setValue(null);
                                        Log.e("AuthRepository", "Firestore error: ", e);
                                    });
                        }
                    } else {
                        Exception e = task.getException();
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            errorLiveData.setValue("No account found with this email.");
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            errorLiveData.setValue("Incorrect password. Try again.");
                        } else {
                            errorLiveData.setValue("Login failed: " + (e != null ? e.getMessage() : "Unknown error"));
                        }
                        Log.e("LOGIN_FAILED", "Login error", e);
                        userLiveData.setValue(null);
                    }
                });
    }

    public void logout(Context context) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    .edit()
                    .putString("last_email", user.getEmail())
                    .apply();
        }
        firebaseAuth.signOut();
        userLiveData.setValue(null);
    }

    public void register(String email, String password, String displayName) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        if (firebaseUser != null) {
                            // Set display name in Firebase Auth
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName)
                                    .build();

                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                            String uid = firebaseUser.getUid();
                                            String photoUrl = firebaseUser.getPhotoUrl() != null
                                                    ? firebaseUser.getPhotoUrl().toString()
                                                    : null;

                                            User user = new User(
                                                    uid,
                                                    displayName,
                                                    email,
                                                    "user",
                                                    "unspecified",
                                                    "",
                                                    0L,
                                                    new ArrayList<>(),
                                                    new ArrayList<>(),
                                                    System.currentTimeMillis(),
                                                    System.currentTimeMillis(),
                                                    photoUrl
                                            );

                                            db.collection("users").document(uid)
                                                    .set(user)
                                                    .addOnSuccessListener(unused -> {
                                                        userLiveData.setValue(user);
                                                        Log.d("AuthRepository", "User registered successfully: " + displayName);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        errorLiveData.setValue("Failed to save user profile: " + e.getMessage());
                                                        Log.e("REGISTER_FIRESTORE", "Error saving user", e);
                                                    });

                                        } else {
                                            errorLiveData.setValue("Failed to update profile.");
                                        }
                                    });
                        }
                    } else {
                        Exception e = task.getException();
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            errorLiveData.setValue("Email already registered.");
                        } else if (e instanceof FirebaseAuthWeakPasswordException) {
                            errorLiveData.setValue("Password must be at least 6 characters.");
                        } else {
                            errorLiveData.setValue("Registration failed: " + e.getMessage());
                        }

                        userLiveData.setValue(null);
                        Log.e("REGISTER_FAILED", "Error registering user", e);
                    }
                });
    }

    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> changePassword(String currentPass, String newPass) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(updateTask -> {
                        resultLiveData.setValue(updateTask.isSuccessful());
                    });
                } else {
                    resultLiveData.setValue(false);
                }
            });
        } else {
            resultLiveData.setValue(false);
        }

        return resultLiveData;
    }
}
