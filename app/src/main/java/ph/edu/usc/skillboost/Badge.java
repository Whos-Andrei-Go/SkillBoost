package ph.edu.usc.skillboost;

public class Badge {
    private String title;
    private String description;
    private int imageResId;

    public Badge(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}
