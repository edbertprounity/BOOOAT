import javafx.beans.property.SimpleStringProperty;

public class LoginModel {
    private UserManager userManager;
    private final SimpleStringProperty username = new SimpleStringProperty("");
    private final SimpleStringProperty password = new SimpleStringProperty("");

    public LoginModel() {
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }
}
