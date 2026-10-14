package ues.edu.dam235.guiaPractica.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ues.edu.dam235.guiaPractica.data.model.request.LoginRequest;
import ues.edu.dam235.guiaPractica.data.model.request.RegisterRequest;
import ues.edu.dam235.guiaPractica.data.model.response.LoginRespose;
import ues.edu.dam235.guiaPractica.data.model.response.LogoutResponse;
import ues.edu.dam235.guiaPractica.data.model.response.RegisterResponse;

public interface ApiService {

    // Auth
    @POST(ApiConstant.LOGIN)
    Call<LoginRespose> login(@Body LoginRequest request);

    @POST(ApiConstant.REGISTER)
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST(ApiConstant.LOGOUT)
    Call<LogoutResponse> logout();
}
