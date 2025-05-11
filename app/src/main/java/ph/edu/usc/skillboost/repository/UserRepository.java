package ph.edu.usc.skillboost.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ph.edu.usc.skillboost.model.User;

public class UserRepository {
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void createUser(User user, OnCompleteListener<Void> onCompleteListener) {
        db.collection("users")
                .document(user.getUid())
                .set(user)
                .addOnCompleteListener(onCompleteListener);
    }

    public void getUser(String uid, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
}
