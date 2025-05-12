package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ph.edu.usc.skillboost.model.User;
import ph.edu.usc.skillboost.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private final UserRepository repository;
    private final MutableLiveData<User> userLiveData;
    private final MutableLiveData<Boolean> operationStatus;

    public UserViewModel() {
        repository = new UserRepository();
        userLiveData = new MutableLiveData<>();
        operationStatus = new MutableLiveData<>();
    }

    public LiveData<User> getUser(String uid) {
        repository.getUserById(uid).observeForever(userLiveData::setValue);
        return userLiveData;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public void createUser(User user) {
        repository.createUser(user).observeForever(operationStatus::setValue);
    }

    public void updateUser(User user) {
        repository.updateUser(user).observeForever(operationStatus::setValue);
    }

    public void deleteUser(String uid) {
        repository.deleteUser(uid).observeForever(operationStatus::setValue);
    }
}

