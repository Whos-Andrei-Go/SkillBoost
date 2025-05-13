package ph.edu.usc.skillboost.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.model.User;
import ph.edu.usc.skillboost.repository.CourseRepository;
import ph.edu.usc.skillboost.repository.UserRepository;

public class CourseViewModel extends ViewModel {
    private final CourseRepository repository;
    private final MutableLiveData<List<Course>> allCourses;
    private final MutableLiveData<Boolean> operationStatus;

    public CourseViewModel() {
        repository = new CourseRepository();
        allCourses = new MutableLiveData<>();
        operationStatus = new MutableLiveData<>();
        fetchCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public void fetchCourses() {
        repository.getAllCourses().observeForever(courses -> allCourses.setValue(courses));
    }

    public void addCourse(Course course) {
        repository.addCourse(course).observeForever(operationStatus::setValue);
    }

    public void updateCourse(Course course) {
        repository.updateCourse(course).observeForever(operationStatus::setValue);
    }

    public void deleteCourse(String courseId) {
        repository.deleteCourse(courseId).observeForever(operationStatus::setValue);
    }
}


