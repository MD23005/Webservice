package ues.edu.dam235.guiaPractica.data.model.request;

public class LoginRequest {

    private String user;
    private String pass;

    public LoginRequest(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
