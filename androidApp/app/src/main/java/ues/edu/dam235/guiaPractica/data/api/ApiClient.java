package ues.edu.dam235.guiaPractica.data.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ues.edu.dam235.guiaPractica.utils.SessionManager;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getInstance(Context context){
        if(retrofit == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        String token = new SessionManager(context).getToken();
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder()
                                .header("Content-Type", "application/json");

                        if(token != null && !token.isEmpty()){
                            builder.header("Authorization", "Bearer "+ token);
                        }
                        return chain.proceed(builder.build());
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstant.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public  static ApiService getApi(Context context){
        return getInstance(context).create(ApiService.class);
    }

}
