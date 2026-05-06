public class LoginController {
    private LoginModel model;

    public LoginController(LoginModel model) {
        this.model = model;
    }

    public void updateUsername(String username) {
        model.usernameProperty().set(username);
    }

    public void updatePassword(String password) {
        model.passwordProperty().set(password);
    }

    public User processLogin() {
        String username = model.getUsername();
        String password = model.getPassword();

        User user = model.getUserManager().findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
