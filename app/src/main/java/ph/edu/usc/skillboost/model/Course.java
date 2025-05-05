package ph.edu.usc.skillboost.model;

public class Course {
    int imageResId;
    String title;
    String description;
    private boolean isCompleted; // completed in user profile

    public Course(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }

    public Course(int imageResId, String title, String description, boolean isCompleted) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}

