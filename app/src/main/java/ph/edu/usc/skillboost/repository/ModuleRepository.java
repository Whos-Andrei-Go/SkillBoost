package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ph.edu.usc.skillboost.model.Module;

// ModuleRepository.java
public class ModuleRepository {
    private final FirebaseFirestore db;

    public ModuleRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Module>> getModulesByCourse(String courseId) {
        MutableLiveData<List<Module>> modulesLiveData = new MutableLiveData<>();
        db.collection("modules")
                .whereEqualTo("courseId", courseId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Module> moduleList = new ArrayList<>();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Module module = snapshot.toObject(Module.class);
                        if (module != null) {
                            moduleList.add(module);
                        }
                    }
                    modulesLiveData.setValue(moduleList);
                })
                .addOnFailureListener(e -> modulesLiveData.setValue(Collections.emptyList()));
        return modulesLiveData;
    }

    public LiveData<Boolean> addModule(Module module) {
        MutableLiveData<Boolean> status = new MutableLiveData<>();
        db.collection("modules")
                .document(module.getModuleId())
                .set(module)
                .addOnSuccessListener(unused -> status.setValue(true))
                .addOnFailureListener(e -> status.setValue(false));
        return status;
    }

    public LiveData<Boolean> updateModule(Module module) {
        MutableLiveData<Boolean> status = new MutableLiveData<>();
        db.collection("modules")
                .document(module.getModuleId())
                .set(module)
                .addOnSuccessListener(unused -> status.setValue(true))
                .addOnFailureListener(e -> status.setValue(false));
        return status;
    }

    public LiveData<Boolean> deleteModule(String moduleId) {
        MutableLiveData<Boolean> status = new MutableLiveData<>();
        db.collection("modules")
                .document(moduleId)
                .delete()
                .addOnSuccessListener(unused -> status.setValue(true))
                .addOnFailureListener(e -> status.setValue(false));
        return status;
    }
}

