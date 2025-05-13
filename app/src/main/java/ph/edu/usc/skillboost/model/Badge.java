package ph.edu.usc.skillboost.model;

public class Badge {
    private String badgeId;
    private String name;
    private String description;
    private String imageUrl;

    public Badge() {}

    public Badge(String badgeId, String name, String description, String imageUrl) {
        this.badgeId = badgeId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getBadgeId() { return badgeId; }
    public void setBadgeId(String badgeId) { this.badgeId = badgeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}