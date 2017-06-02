package MutualJsonObjects;

/**
 * Created by user on 12/05/2017.
 */
public class ClientUserLoginDetails {
    String username;
    String password;

    public ClientUserLoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ClientUserLoginDetails() {
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
