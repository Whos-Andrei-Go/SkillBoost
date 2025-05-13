package ph.edu.usc.skillboost.model;

import java.util.List;

public class Progress {
    private String progressId;
    private String uid; // User ID
    private String courseId;
    private List<String> completedModules;
    private int percentageCompleted;
    private long lastUpdated;

    public Progress() {}

    public Progress(String progressId, String uid, String courseId, List<String> completedModules, int percentageCompleted, long lastUpdated) {
        this.progressId = progressId;
        this.uid = uid;
        this.courseId = courseId;
        this.completedModules = completedModules;
        this.percentageCompleted = percentageCompleted;
        this.lastUpdated = lastUpdated;
    }

    // Getters and setters omitted for brevity
    public String getProgressId() { return this.progressId; }
    public void setProgressId(String progressId) { this.progressId = progressId; }

    public String getUid() { return this.uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getCourseId() { return this.courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public List<String> getCompletedModules() { return this.completedModules; }
    public void setCompletedModules(List<String> completedModules) { this.completedModules = completedModules; }

    public int getPercentageCompleted() { return this.percentageCompleted; }
    public void setPercentageCompleted(int percentageCompleted) { this.percentageCompleted = percentageCompleted; }

    public long getLastUpdated() { return this.lastUpdated; }
    public void setLastUpdated(long lastUpdated) {this.lastUpdated = lastUpdated; }
}
