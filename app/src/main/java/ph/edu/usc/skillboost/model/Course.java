package ph.edu.usc.skillboost.model;

import java.util.List;

public class Course {

    private String courseId;
    private String imageUrl;
    private String title;

    private String subtitle;
    private String description;

    private List<String> tags;
    private List<String> categories;

    public Course() {}

    public Course(String courseId, String title, String subtitle, String description, List<String> tags, List<String> categories, String imageUrl) {
        this.courseId = courseId;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
}



