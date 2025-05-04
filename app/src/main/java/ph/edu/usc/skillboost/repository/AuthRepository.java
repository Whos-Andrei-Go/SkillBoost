package ph.edu.usc.skillboost.repository;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import ph.edu.usc.skillboost.HomepageActivity;
import ph.edu.usc.skillboost.model.User;
import ph.edu.usc.skillboost.view.LoginActivity;

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

    public void logout() {
        firebaseAuth.signOut();
        userLiveData.setValue(null); // clear user LiveData
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
