import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class User {
    protected String username;
    protected String password;
    protected String name;
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();
    private final SimpleStringProperty usernameProperty = new SimpleStringProperty();

    protected User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.nameProperty.set(name);
        this.usernameProperty.set(username);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public StringProperty usernameProperty() {
        return usernameProperty;
    }

    @Override
    public String toString() {
        return "User[name=" + name + ", username=" + username + "]";
    }
}