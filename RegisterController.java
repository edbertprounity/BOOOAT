public class RegisterController {
    private UserManager userManager;
    private RegisterModel model;

    public RegisterController(UserManager userManager, RegisterModel model) {
        this.userManager = userManager;
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

        if (userManager.findByUsername(username) != null) {
            return false;
        }

        userManager.addUser(new Member(name, username, password));
        model.clear();
        return true;
    }
}
