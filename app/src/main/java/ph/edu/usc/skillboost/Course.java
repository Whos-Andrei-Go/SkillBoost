package ph.edu.usc.skillboost;

public class Course {
    int imageResId;
    String title;
    String description;
    private boolean isCompleted; // completed in user profile

    public Course(int imageResId, String title, String description, boolean isCompleted) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}

