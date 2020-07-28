package sf.example.mockrestserviceserver;


public class Details {
    private String name;
    private String login;

    public Details(String username, String login) {
        this.name = username;
        this.login = login;
    }


    @SuppressWarnings("unused")
    public Details() {}

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }
}
