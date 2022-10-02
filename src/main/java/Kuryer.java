public class Kuryer {
    private String login;
    private String password;
    private String firstName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Kuryer(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
}
    public Kuryer(String login, String password){
        this.login = login;
        this.password = password;
    }
    public Kuryer(String login){
        this.login = login;
    }

    public static Kuryer from(Kuryer kuryer) {
        return new Kuryer(kuryer.getLogin(), kuryer.getPassword());
    }

    public static Kuryer fromTwo(Kuryer kuryer) {
        return new Kuryer(kuryer.getLogin());
    }
}
