package ph.edu.usc.skillboost.model;

public class Notification {
    private String notificationId;
    private String uid;
    private String title;
    private String message;
    private long timestamp;

    public Notification() {}

    public Notification(String id, String uid, String title, String message, long timestamp) {
        this.notificationId = id;
        this.uid = uid;
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getNotificationId() { return notificationId; }
    public void setId(String notificationId) { this.notificationId = notificationId; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}

