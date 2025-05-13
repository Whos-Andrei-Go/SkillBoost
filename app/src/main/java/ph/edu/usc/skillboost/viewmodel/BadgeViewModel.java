package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.repository.BadgeRepository;

public class BadgeViewModel extends ViewModel {
    private final BadgeRepository repository;
    private final MutableLiveData<List<Badge>> badgeList;
    private final MutableLiveData<Boolean> operationStatus;

    public BadgeViewModel() {
        repository = new BadgeRepository();
        badgeList = new MutableLiveData<>();
        operationStatus = new MutableLiveData<>();
        fetchBadges();
    }

    public LiveData<List<Badge>> getAllBadges() {
        return badgeList;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public void fetchBadges() {
        repository.getAllBadges().observeForever(badgeList::setValue);
    }

    public void addBadge(Badge badge) {
        repository.addBadge(badge).observeForever(operationStatus::setValue);
    }
}
