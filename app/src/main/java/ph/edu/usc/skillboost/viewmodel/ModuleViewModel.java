package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ph.edu.usc.skillboost.model.Module;
import ph.edu.usc.skillboost.repository.ModuleRepository;

// ModuleViewModel.java
public class ModuleViewModel extends ViewModel {
    private final ModuleRepository repository;
    private final MutableLiveData<List<Module>> moduleList;
    private final MutableLiveData<Boolean> operationStatus;

    public ModuleViewModel() {
        repository = new ModuleRepository();
        moduleList = new MutableLiveData<>();
        operationStatus = new MutableLiveData<>();
    }

    public LiveData<List<Module>> getModulesByCourse(String courseId) {
        repository.getModulesByCourse(courseId).observeForever(moduleList::setValue);
        return moduleList;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public void addModule(Module module) {
        repository.addModule(module).observeForever(operationStatus::setValue);
    }

    public void updateModule(Module module) {
        repository.updateModule(module).observeForever(operationStatus::setValue);
    }

    public void deleteModule(String moduleId) {
        repository.deleteModule(moduleId).observeForever(operationStatus::setValue);
    }
}

