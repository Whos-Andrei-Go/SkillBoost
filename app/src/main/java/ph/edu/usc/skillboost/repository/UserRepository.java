package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import ph.edu.usc.skillboost.model.User;

public class UserRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");

    public LiveData<User> getUserById(String uid) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                userLiveData.setValue(documentSnapshot.toObject(User.class));
            }
        });
        return userLiveData;
    }

    public LiveData<Boolean> createUser(User user) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        usersRef.document(user.getUid()).set(user)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public LiveData<Boolean> updateUser(User user) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        usersRef.document(user.getUid()).set(user, SetOptions.merge())
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public LiveData<Boolean> deleteUser(String uid) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        usersRef.document(uid).delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}