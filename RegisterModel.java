import javafx.beans.property.SimpleStringProperty;

public class RegisterModel {
    private UserManager userManager;
    private final SimpleStringProperty fullName = new SimpleStringProperty("");
    private final SimpleStringProperty username = new SimpleStringProperty("");
    private final SimpleStringProperty password = new SimpleStringProperty("");

    public RegisterModel() {
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public void clear() {
        fullName.set("");
        username.set("");
        password.set("");
    }
}
