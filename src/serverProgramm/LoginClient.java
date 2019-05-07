package serverProgramm;

import java.io.Serializable;

public class LoginClient implements Serializable {

    private String username;
    private String password;

    public LoginClient(String _username, String _password){
        this.username = _username;
        this.password = _password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
