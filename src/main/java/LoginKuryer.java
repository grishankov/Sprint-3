public class LoginKuryer {
    private String login;
    private String password;

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

    public LoginKuryer(String login, String password){
        this.login = login;
        this.password = password;
    }

    public LoginKuryer(String login) {
        this.login = login;
    }

    public static LoginKuryer from(Kuryer kuryer) {
        return new LoginKuryer(kuryer.getLogin(), kuryer.getPassword());
    }
}
