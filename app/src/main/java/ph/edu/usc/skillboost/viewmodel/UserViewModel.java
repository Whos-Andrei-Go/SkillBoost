package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ph.edu.usc.skillboost.model.User;
import ph.edu.usc.skillboost.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private final UserRepository repository = new UserRepository();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void fetchUser(String uid) {
        repository.getUser(uid, task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                User user = task.getResult().toObject(User.class);
                userLiveData.setValue(user);
            }
        });
    }

    public void registerUser(User user) {
        repository.createUser(user, task -> {
            if (task.isSuccessful()) {
                userLiveData.setValue(user);
            }
        });
    }
}
