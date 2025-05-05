package ph.edu.usc.skillboost.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import ph.edu.usc.skillboost.model.User;

public class AuthRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> errorLiveData;
    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            userLiveData.setValue(new User(firebaseUser.getUid(), firebaseUser.getEmail()));
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            userLiveData.setValue(new User(user.getUid(), user.getEmail()));
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

                        Log.e("LOGIN_FAILED", "Error: ", e);
                        userLiveData.setValue(null); // Clear the user on error
                    }
                });
    }

    public void logout(Context context) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if ( user != null){
            context.getSharedPreferences("user_prefs",Context.MODE_PRIVATE)
                    .edit()
                    .putString("last_email",user.getEmail())
                    .apply();
        }
        firebaseAuth.signOut();
        userLiveData.setValue(null); // clear user LiveData
    }

    public void register(String email, String password, String displayName) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        if (firebaseUser != null) {
                            // Update display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName)
                                    .build();

                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                            userLiveData.setValue(new User(firebaseUser.getUid(), firebaseUser.getEmail()));
                                        } else {
                                            errorLiveData.setValue("Profile update failed.");
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
                        Log.e("REGISTER_FAILED", "Error: ", e);
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
}
