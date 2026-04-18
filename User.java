public abstract class User {
    protected String username;
    protected String password;
    protected String name;

    protected User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "User[name=" + name + ", username=" + username + "]";
    }
}