import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class User {
    protected String password;
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();
    private final SimpleStringProperty usernameProperty = new SimpleStringProperty();

    protected User(String name, String username, String password) {
        this.password = password;
        this.nameProperty.set(name);
        this.usernameProperty.set(username);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getName() {
        return nameProperty.get();
    }

    public String getUsername() {
        return usernameProperty.get();
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
        return "User[name=" + getName() + ", username=" + getUsername() + "]";
    }
}