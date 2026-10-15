package ues.edu.dam235.guiaPractica.data.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ues.edu.dam235.guiaPractica.data.model.request.LoginRequest;
import ues.edu.dam235.guiaPractica.data.model.request.RegisterRequest;
import ues.edu.dam235.guiaPractica.data.model.request.ProductRequest;
import ues.edu.dam235.guiaPractica.data.model.response.LoginRespose;
import ues.edu.dam235.guiaPractica.data.model.response.LogoutResponse;
import ues.edu.dam235.guiaPractica.data.model.response.RegisterResponse;
import ues.edu.dam235.guiaPractica.data.model.response.ProductResponse;
import ues.edu.dam235.guiaPractica.data.model.response.ProductOperationResponse;

public interface ApiService {

    // Autenticacion: metodos existentes del companero
    @POST(ApiConstant.LOGIN)
    Call<LoginRespose> login(@Body LoginRequest request);

    @POST(ApiConstant.REGISTER)
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST(ApiConstant.LOGOUT)
    Call<LogoutResponse> logout();

    // CRUD de productos
    @GET(ApiConstant.PRODUCTS)
    Call<List<ProductResponse>> getProducts();

    @POST(ApiConstant.PRODUCTS)
    Call<ProductOperationResponse> createProduct(@Body ProductRequest request);

    @PUT(ApiConstant.PRODUCTS + "/{code}")
    Call<ProductOperationResponse> updateProduct(@Path("code") int code,
                                                  @Body ProductResponse product);

    @DELETE(ApiConstant.PRODUCTS + "/{code}")
    Call<ProductOperationResponse> deleteProduct(@Path("code") int code);
}
