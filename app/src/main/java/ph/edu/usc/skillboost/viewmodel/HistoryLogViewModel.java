package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ph.edu.usc.skillboost.model.HistoryLog;
import ph.edu.usc.skillboost.repository.HistoryLogRepository;

public class HistoryLogViewModel extends ViewModel {
    private final HistoryLogRepository repository;
    private final MutableLiveData<List<HistoryLog>> logs;
    private final MutableLiveData<Boolean> operationStatus;

    public HistoryLogViewModel() {
        repository = new HistoryLogRepository();
        logs = new MutableLiveData<>();
        operationStatus = new MutableLiveData<>();
    }

    public LiveData<List<HistoryLog>> getUserLogs(String uid) {
        repository.getUserLogs(uid).observeForever(logs::setValue);
        return logs;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public void addLog(HistoryLog log) {
        repository.addLog(log).observeForever(operationStatus::setValue);
    }
}