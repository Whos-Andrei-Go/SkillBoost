package ph.edu.usc.skillboost.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String name;
    private String email;
    private String role;
    private List<String> enrolledCourses;
    private List<String> badges;
    private long createdAt;
    private long lastLogin;
    private String photoUrl;

    public User() {} // Needed for Firebase

    public User(String uid, String name, String email, String role, List<String> enrolledCourses, List<String> badges, long createdAt, long lastLogin, String photoUrl) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.role = role;
        this.enrolledCourses = enrolledCourses;
        this.badges = badges;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.photoUrl = photoUrl;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<String> enrolledCourses) { this.enrolledCourses = enrolledCourses; }

    public List<String> getBadges() { return badges; }
    public void setBadges(List<String> badges) { this.badges = badges; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getLastLogin() { return lastLogin; }
    public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}
