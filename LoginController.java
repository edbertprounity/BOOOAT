public class LoginController {
    private UserManager userManager;
    private LoginModel model;

    public LoginController(UserManager userManager, LoginModel model) {
        this.userManager = userManager;
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

        User user = userManager.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
