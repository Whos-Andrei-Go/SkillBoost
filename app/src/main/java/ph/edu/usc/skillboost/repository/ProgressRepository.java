package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Progress;

public class ProgressRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference progressRef = db.collection("progress");

    public LiveData<List<Progress>> getProgressByUserId(String uid) {
        MutableLiveData<List<Progress>> progressLiveData = new MutableLiveData<>();
        progressRef.whereEqualTo("uid", uid).get().addOnSuccessListener(snapshot -> {
            List<Progress> list = new ArrayList<>();
            for (DocumentSnapshot doc : snapshot.getDocuments()) {
                list.add(doc.toObject(Progress.class));
            }
            progressLiveData.setValue(list);
        });
        return progressLiveData;
    }

    public LiveData<Progress> getProgressByUserAndCourse(String uid, String courseId) {
        MutableLiveData<Progress> progressLiveData = new MutableLiveData<>();
        progressRef.whereEqualTo("uid", uid).whereEqualTo("courseId", courseId)
                .limit(1).get().addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        progressLiveData.setValue(snapshot.getDocuments().get(0).toObject(Progress.class));
                    }
                });
        return progressLiveData;
    }

    public LiveData<Boolean> addOrUpdateProgress(Progress progress) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        progress.setLastUpdated(System.currentTimeMillis());
        progressRef.document(progress.getProgressId()).set(progress, SetOptions.merge())
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public LiveData<Boolean> deleteProgress(String progressId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        progressRef.document(progressId).delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}

