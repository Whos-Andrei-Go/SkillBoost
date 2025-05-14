package ph.edu.usc.skillboost.model;

import java.util.List;

public class Badge {
    private String badgeId;       // Unique ID for each badge
    private String title;
    private String description;
    private List<String> categories;
    private String imageRes;

    // Constructor with badgeID
    public Badge(String badgeId, String title, String description, String imageRes, List<String> categories) {
        this.badgeId = badgeId;
        this.title = title;
        this.description = description;
        this.imageRes = imageRes;
        this.categories = categories;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description; }

    // Image Resource
    public String getImageRes() {
        return imageRes;
    }
    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }
}
