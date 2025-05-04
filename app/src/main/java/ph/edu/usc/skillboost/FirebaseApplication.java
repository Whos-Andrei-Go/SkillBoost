package ph.edu.usc.skillboost;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FirebaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase here globally
        FirebaseApp.initializeApp(this);
    }
}
