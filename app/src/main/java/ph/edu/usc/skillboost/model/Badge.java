package ph.edu.usc.skillboost.model;

import java.util.List;

public class Badge {
    private String badgeId;       // Unique ID for each badge
    private String title;
    private String subtitle;
    private List<String> categories;
    private String imageRes;
    private String courseId;

    public Badge() {} // Needed for Firebase

    // Constructor with badgeID
    public Badge(String badgeId, String title, String subtitle, String imageRes, List<String> categories, String courseId) {
        this.badgeId = badgeId;
        this.title = title;
        this.subtitle = subtitle;
        this.imageRes = imageRes;
        this.categories = categories;
        this.courseId = courseId;
    }

    // Badge ID
    public String getBadgeId() { // Ensure the method is named correctly
        return badgeId;
    }
    public void setBadgeId(String badgeID) {
        this.badgeId = badgeID;
    }

    // Categories
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    // Title
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) { this.title = title; }

    // Description
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    // Image Resource
    public String getImageRes() {
        return imageRes;
    }
    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }

    // Course ID
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
