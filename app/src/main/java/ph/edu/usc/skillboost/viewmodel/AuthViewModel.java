package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ph.edu.usc.skillboost.model.User;
import ph.edu.usc.skillboost.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    public void login(String email, String password) {
        authRepository.login(email, password);
    }

    public void logout() {
        authRepository.logout();
    }

    public void register(String email, String password, String displayName) {
        authRepository.register(email, password, displayName);
    }

    public boolean isUserLoggedIn() {
        return authRepository.isUserLoggedIn();
    }

    public LiveData<User> getUserLiveData() {
        return authRepository.getUserLiveData();
    }

    public LiveData<String> getErrorLiveData() {
        return authRepository.getErrorLiveData();
    }
}
