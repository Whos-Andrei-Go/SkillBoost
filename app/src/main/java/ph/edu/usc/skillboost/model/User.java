package ph.edu.usc.skillboost.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String name;
    private String email;
    private String role;
    private String gender;
    private String bio;
    private long dob;
    private List<String> enrolledCourses;
    private List<String> completedCourses;
    private List<String> badges;
    private List<Topic> preferences;
    private long createdAt;
    private long lastLogin;
    private String photoUrl;

    public User() {} // Needed for Firebase

    public User(String uid, String name, String email, String role, String gender, String bio, long dob,
                List<String> enrolledCourses, List<String> completedCoursesCourses, List<String> badges,
                List<Topic> preferences, long createdAt, long lastLogin, String photoUrl) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.role = role;
        this.gender = gender;
        this.bio = bio;
        this.dob = dob;
        this.enrolledCourses = enrolledCourses;
        this.completedCourses = completedCourses;
        this.badges = badges;
        this.preferences = preferences;
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

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public long getDob() { return dob; }
    public void setDob(long dob) { this.dob = dob; }

    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<String> enrolledCourses) { this.enrolledCourses = enrolledCourses; }

    public List<String> getCompletedCourses() { return completedCourses; }
    public void setCompletedCourses(List<String> completedCourses) { this.completedCourses = completedCourses; }

    public List<String> getBadges() { return badges; }
    public void setBadges(List<String> badges) { this.badges = badges; }

    public List<Topic> getPreferences() { return preferences; }
    public void setPreferences() { this.preferences = preferences; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getLastLogin() { return lastLogin; }
    public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}

//