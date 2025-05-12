package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ph.edu.usc.skillboost.model.Notification;
import ph.edu.usc.skillboost.repository.NotificationRepository;

public class NotificationViewModel extends ViewModel {
    private final NotificationRepository repository;
    private final MutableLiveData<List<Notification>> notifications;
    private final MutableLiveData<Boolean> operationStatus;

    public NotificationViewModel() {
        repository = new NotificationRepository();
        notifications = new MutableLiveData<>();
        operationStatus = new MutableLiveData<>();
    }

    public LiveData<List<Notification>> getUserNotifications(String uid) {
        repository.getUserNotifications(uid).observeForever(notifications::setValue);
        return notifications;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public void addNotification(Notification notification) {
        repository.addNotification(notification).observeForever(operationStatus::setValue);
    }
}

