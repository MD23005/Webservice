package ues.edu.dam235.guiaPractica.data.repository;

import android.content.Context;

import retrofit2.Call;
import ues.edu.dam235.guiaPractica.data.api.ApiClient;
import ues.edu.dam235.guiaPractica.data.api.ApiService;
import ues.edu.dam235.guiaPractica.data.model.request.LoginRequest;
import ues.edu.dam235.guiaPractica.data.model.request.RegisterRequest;
import ues.edu.dam235.guiaPractica.data.model.response.LoginRespose;
import ues.edu.dam235.guiaPractica.data.model.response.LogoutResponse;
import ues.edu.dam235.guiaPractica.data.model.response.RegisterResponse;

public class AuthRepository {

    private final ApiService api;

    public AuthRepository(Context context){
        api = ApiClient.getApi(context);
    }

    public Call<LoginRespose> login(String user, String pass){
        return api.login(new LoginRequest(user,pass));
    }

    public Call<RegisterResponse> register(String name, String lastName, String email, String password){
        return api.register(new RegisterRequest(name, lastName, email, password));
    }

    public Call<LogoutResponse> logout(){
        return api.logout();
    }
}
