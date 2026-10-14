package ues.edu.dam235.guiaPractica.data.model.response;

public class LoginRespose {
    private String token;
    private String  expireIn;
    private String msj;

    public LoginRespose(String token, String expireIn, String msj) {
        this.token = token;
        this.expireIn = expireIn;
        this.msj = msj;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }
}
