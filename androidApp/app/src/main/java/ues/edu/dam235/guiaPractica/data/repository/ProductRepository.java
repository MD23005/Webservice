package ues.edu.dam235.guiaPractica.data.repository;

import android.content.Context;
import java.util.List;
import retrofit2.Call;
import ues.edu.dam235.guiaPractica.data.api.ApiClient;
import ues.edu.dam235.guiaPractica.data.api.ApiService;
import ues.edu.dam235.guiaPractica.data.model.request.ProductRequest;
import ues.edu.dam235.guiaPractica.data.model.response.ProductOperationResponse;
import ues.edu.dam235.guiaPractica.data.model.response.ProductResponse;

public class ProductRepository {
    private final ApiService api;

    public ProductRepository(Context context) {
        api = ApiClient.getApi(context);
    }

    public Call<List<ProductResponse>> getProducts() {
        return api.getProducts();
    }

    public Call<ProductOperationResponse> createProduct(ProductRequest request) {
        return api.createProduct(request);
    }

    public Call<ProductOperationResponse> updateProduct(int code, ProductResponse product) {
        return api.updateProduct(code, product);
    }

    public Call<ProductOperationResponse> deleteProduct(int code) {
        return api.deleteProduct(code);
    }
}
