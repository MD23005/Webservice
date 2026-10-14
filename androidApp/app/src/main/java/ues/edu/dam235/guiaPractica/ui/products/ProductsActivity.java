package ues.edu.dam235.guiaPractica.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ues.edu.dam235.guiaPractica.R;
import ues.edu.dam235.guiaPractica.data.model.response.LogoutResponse;
import ues.edu.dam235.guiaPractica.data.repository.AuthRepository;
import ues.edu.dam235.guiaPractica.ui.login.LoginActivity;
import ues.edu.dam235.guiaPractica.utils.SessionManager;

public class ProductsActivity extends AppCompatActivity {

    private ImageButton btnLogout;
    private AuthRepository authRepository;
    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        btnLogout = findViewById(R.id.btnLogout);
        authRepository = new AuthRepository(this);
        session = new SessionManager(this);

        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout(){
        authRepository.logout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductsActivity.this, "Sesión cerrada exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductsActivity.this, LoginActivity.class));
                    session.logout();
                    finish();
                }else{
                    Toast.makeText(ProductsActivity.this, "Algo falló...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Error al establecer conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
