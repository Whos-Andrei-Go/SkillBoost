package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ph.edu.usc.skillboost.model.Progress;
import ph.edu.usc.skillboost.repository.ProgressRepository;

public class ProgressViewModel extends ViewModel {
    private final ProgressRepository repository = new ProgressRepository();

    public LiveData<List<Progress>> getProgressByUserId(String uid) {
        return repository.getProgressByUserId(uid);
    }

    public LiveData<Progress> getProgressByUserAndCourse(String uid, String courseId) {
        return repository.getProgressByUserAndCourse(uid, courseId);
    }

    public LiveData<Boolean> addOrUpdateProgress(Progress progress) {
        return repository.addOrUpdateProgress(progress);
    }

    public LiveData<Boolean> deleteProgress(String progressId) {
        return repository.deleteProgress(progressId);
    }
}

