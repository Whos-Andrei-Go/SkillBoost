package ph.edu.usc.skillboost.model;

public class Module {
    private String moduleId;
    private String courseId;
    private String title;
    private String content;
    private String mediaUrl;

    public Module() {}

    public Module(String moduleId, String courseId, String title, String content, String mediaUrl) {
        this.moduleId = moduleId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.mediaUrl = mediaUrl;
    }

    public String getModuleId() { return moduleId; }
    public void setModuleId(String moduleId) { this.moduleId = moduleId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
}
