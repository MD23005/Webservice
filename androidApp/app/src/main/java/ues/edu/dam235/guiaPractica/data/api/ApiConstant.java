package ues.edu.dam235.guiaPractica.data.api;

public class ApiConstant {
    // Dirección del servidor donde están expuestas las api
    public static final String BASE_URL = "http://192.168.1.10:8085/";

    //Endpoints para gestión de usuarios
    public static final String LOGIN = "api/v1/demoapirestdam235/auth/login";
    public static final String REGISTER = "api/v1/demoapirestdam235/auth/register";
    public static final String LOGOUT = "api/v1/demoapirestdam235/auth/logout";
    public static final String PRODUCTS = "api/v1/demoapirestdam235/products";

}
