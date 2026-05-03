import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterModel {
    private final StringProperty fullName = new SimpleStringProperty("");
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
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
