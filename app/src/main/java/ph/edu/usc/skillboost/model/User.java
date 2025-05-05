package ph.edu.usc.skillboost.model;

public class User {
    private String uid;
    private String email;

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public String getUid(){
        return this.uid;
    }

    public String getEmail(){
        return this.email;
    }
}
