package ues.edu.dam235.guiaPractica.data.model.response;

public class RegisterResponse {
    private String message;
    private boolean success;
    private Integer userId;

    public RegisterResponse(String message, boolean success, Integer userId) {
        this.message = message;
        this.success = success;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
