package databaseObjects;

import java.io.Serializable;

public class User implements Serializable {

    private String uniqueName;
    private String password;

    public User(String uniqueName, String password) {
        this.uniqueName = uniqueName;
        this.password = password;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
