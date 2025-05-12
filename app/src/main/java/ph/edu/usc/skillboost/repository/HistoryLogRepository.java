package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.HistoryLog;

public class HistoryLogRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference logsRef = db.collection("historyLogs");

    public LiveData<Boolean> addLog(HistoryLog log) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        logsRef.document(log.getLogId()).set(log)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public LiveData<List<HistoryLog>> getUserLogs(String uid) {
        MutableLiveData<List<HistoryLog>> logsLiveData = new MutableLiveData<>();
        logsRef.whereEqualTo("uid", uid).get().addOnSuccessListener(snapshot -> {
            List<HistoryLog> logs = new ArrayList<>();
            for (DocumentSnapshot doc : snapshot.getDocuments()) {
                logs.add(doc.toObject(HistoryLog.class));
            }
            logsLiveData.setValue(logs);
        });
        return logsLiveData;
    }
}
