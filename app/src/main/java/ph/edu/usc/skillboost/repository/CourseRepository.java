package ph.edu.usc.skillboost.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Course;

public class CourseRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursesRef = db.collection("courses");

    public LiveData<List<Course>> getAllCourses() {
        MutableLiveData<List<Course>> coursesLiveData = new MutableLiveData<>();
        coursesRef.get().addOnSuccessListener(querySnapshot -> {
            List<Course> courses = new ArrayList<>();
            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                courses.add(doc.toObject(Course.class));
            }
            coursesLiveData.setValue(courses);
        });
        return coursesLiveData;
    }

    public LiveData<Boolean> addCourse(Course course) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        coursesRef.document(course.getCourseId()).set(course)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public LiveData<Boolean> updateCourse(Course course) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        coursesRef.document(course.getCourseId()).set(course, SetOptions.merge())
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public LiveData<Boolean> deleteCourse(String courseId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        coursesRef.document(courseId).delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}
