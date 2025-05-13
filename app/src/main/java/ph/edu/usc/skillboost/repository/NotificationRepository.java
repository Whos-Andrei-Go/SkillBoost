package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Notification;

public class NotificationRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notifRef = db.collection("notifications");

    public LiveData<List<Notification>> getUserNotifications(String uid) {
        MutableLiveData<List<Notification>> notifLiveData = new MutableLiveData<>();
        notifRef.whereEqualTo("uid", uid).get().addOnSuccessListener(snapshot -> {
            List<Notification> list = new ArrayList<>();
            for (DocumentSnapshot doc : snapshot.getDocuments()) {
                list.add(doc.toObject(Notification.class));
            }
            notifLiveData.setValue(list);
        });
        return notifLiveData;
    }

    public LiveData<Boolean> addNotification(Notification notification) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        notifRef.document(notification.getNotificationId()).set(notification)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}

