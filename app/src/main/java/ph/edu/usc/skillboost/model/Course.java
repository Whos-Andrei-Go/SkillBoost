package ph.edu.usc.skillboost.model;

import java.util.List;

public class Course {

    private String courseId;
    private int imageResId;
    private String title;
    private String description;

    private List<String> tags;
    private List<String> categories;

    public Course() {}

    public Course(String courseId, String title, String description, List<String> tags, List<String> categories, int imageUrl) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.imageResId = imageUrl;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public int getImageResId() { return imageResId; }
    public void setImageUrl(int imageUrl) { this.imageResId = imageUrl; }
}



