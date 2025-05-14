package ph.edu.usc.skillboost.model;

public class Badge {
    private int badgeID;       // Unique ID for each badge
    private String title;
    private String description;
    private String category;
    private int imageRes;

    // Constructor with badgeID
    public Badge(int badgeID, String title, String description, int imageRes, String category) {
        this.badgeID = badgeID;
        this.title = title;
        this.description = description;
        this.imageRes = imageRes;
        this.category = category;
    }

    // Getter for the category
    public String getCategory() {
        return category;
    }

    // Getter and Setter for badgeID
    public int getBadgeID() { // Ensure the method is named correctly
        return badgeID;
    }

    public void setBadgeID(int badgeID) {
        this.badgeID = badgeID;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for image resource
    public int getImageRes() {
        return imageRes;
    }

    // Optionally, you can also set the image resource if needed
    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
