public class RegisterController {
    private RegisterModel model;

    public RegisterController(RegisterModel model) {
        this.model = model;
    }

    public void updateFullName(String name) {
        model.fullNameProperty().set(name);
    }

    public void updateUsername(String username) {
        model.usernameProperty().set(username);
    }

    public void updatePassword(String password) {
        model.passwordProperty().set(password);
    }

    public boolean processRegistration() {
        String name = model.getFullName();
        String username = model.getUsername();
        String password = model.getPassword();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (model.getUserManager().findByUsername(username) != null) {
            return false;
        }

        model.getUserManager().addUser(new Member(name, username, password));
        model.clear();
        return true;
    }
}
