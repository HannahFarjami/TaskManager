package se.kth.id1212.taskmanagerandroidclient.model;

import java.io.Serializable;

/**
 * Represent a user from firebase
 */
public class User implements Serializable {

    private String email;
    private String uId;

    public User(String email, String uId) {
        this.email = email;
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
