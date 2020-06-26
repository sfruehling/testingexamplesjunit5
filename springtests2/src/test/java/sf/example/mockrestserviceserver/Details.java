package sf.example.mockrestserviceserver;

public class Details {
    private String name = "";
    private String login = "";

    public Details(String username, String login) {
        this.name = username;
        this.login = login;
    }

    public Details() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }
}
