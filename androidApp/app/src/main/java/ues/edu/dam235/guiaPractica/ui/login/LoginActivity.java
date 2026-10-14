package ues.edu.dam235.guiaPractica.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import android.util.Patterns;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ues.edu.dam235.guiaPractica.R;
import ues.edu.dam235.guiaPractica.data.model.response.LoginRespose;
import ues.edu.dam235.guiaPractica.data.repository.AuthRepository;
import ues.edu.dam235.guiaPractica.ui.products.ProductsActivity;
import ues.edu.dam235.guiaPractica.ui.register.RegisterActivity;
import ues.edu.dam235.guiaPractica.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edtEmail, edtPass;
    private Button btnLogin, btnSignin;
    private AuthRepository authRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignin = findViewById(R.id.btnSignin);

        authRepository = new AuthRepository(this);
        sessionManager = new SessionManager(this);

        if(sessionManager.isLoggedIn()){
            startActivity(new Intent(this, ProductsActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(v -> tryLogin());

        btnSignin.setOnClickListener(v -> redirectRegister());
    }

    private void tryLogin(){
        String user = edtEmail.getText() != null ? edtEmail.getText().toString().trim() : "";
        String pass = edtPass.getText() != null ? edtPass.getText().toString().trim() : "";

        if(user.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            edtEmail.setError("El correo es inválido");
            return;
        }

        if(pass.isEmpty()){
            edtPass.setError("Llena el campo de la contraseña");
            return;
        }

        btnLogin.setEnabled(false);

        authRepository.login(user,pass).enqueue(new Callback<LoginRespose>() {
            @Override
            public void onResponse(Call<LoginRespose> call, Response<LoginRespose> response) {
                btnLogin.setEnabled(true);

                if(response.isSuccessful() && response.body() != null){
                    LoginRespose body = response.body();
                    sessionManager.saveSession(body.getToken(), user);
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, ProductsActivity.class));

                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas (" + response.code() + ")",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRespose> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Error al establecer la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void redirectRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
