package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;

public class BadgeRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference badgeRef = db.collection("badges");

    public LiveData<List<Badge>> getAllBadges() {
        MutableLiveData<List<Badge>> badgeLiveData = new MutableLiveData<>();
        badgeRef.get().addOnSuccessListener(snapshot -> {
            List<Badge> badgeList = new ArrayList<>();
            for (DocumentSnapshot doc : snapshot.getDocuments()) {
                Badge badge = doc.toObject(Badge.class);
                badgeList.add(badge);
            }
            badgeLiveData.setValue(badgeList);
        });
        return badgeLiveData;
    }

    public LiveData<Boolean> addBadge(Badge badge) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        // Convert the badgeID (int) to a String before passing it to document()
        badgeRef.document(String.valueOf(badge.getBadgeId()))
                .set(badge)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}
