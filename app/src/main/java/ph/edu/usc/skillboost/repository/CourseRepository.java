package ph.edu.usc.skillboost.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    public LiveData<Course> getCourseById(String courseId) {
        MutableLiveData<Course> courseLiveData = new MutableLiveData<>();

        coursesRef.whereEqualTo("courseId", courseId).get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        Course course = documentSnapshot.toObject(Course.class);
                        courseLiveData.setValue(course);
                    } else {
                        courseLiveData.setValue(null); // No course found
                    }
                })
                .addOnFailureListener(e -> {
                    courseLiveData.setValue(null); // Handle failure
                    Log.e("Firestore Error", e.getMessage());
                });

        return courseLiveData;
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
