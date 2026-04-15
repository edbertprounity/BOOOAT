import java.util.List;

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

    public String getUsername() {
        return username;
    }

    protected String getListItem(List<?> list) {
        String items = "";
        for (int i = 1; i <= list.size(); i++) {
            items = items + "\n" + i + ". " + list.get(i - 1);
        }
        return items;
    }
}