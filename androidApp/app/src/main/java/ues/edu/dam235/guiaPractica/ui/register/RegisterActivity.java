package ues.edu.dam235.guiaPractica.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ues.edu.dam235.guiaPractica.R;
import ues.edu.dam235.guiaPractica.data.model.response.RegisterResponse;
import ues.edu.dam235.guiaPractica.data.repository.AuthRepository;
import ues.edu.dam235.guiaPractica.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edtName, edtLastName, edtEmail, edtPass, edtPassConfirm;
    private Button btnSignin;
    private ImageButton btnRegresar;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        edtName = findViewById(R.id.edtName);
        edtLastName = findViewById(R.id.edtLastname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtPassConfirm = findViewById(R.id.edtPassConfirm);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnSignin = findViewById(R.id.btnSignin);

        btnRegresar.setOnClickListener(v -> regresar());
        btnSignin.setOnClickListener(v -> tryRegister());

        authRepository = new AuthRepository(this);
    }

    private void regresar(){
        finish();
    }

    private void tryRegister(){
        String name = edtName.getText() != null ? edtName.getText().toString().trim() : "";
        String lastName = edtLastName.getText() != null ? edtLastName.getText().toString().trim() : "";
        String email = edtEmail.getText() != null ? edtEmail.getText().toString().trim() : "";
        String password = edtPass.getText() != null ? edtPass.getText().toString().trim() : "";
        String confirmPass = edtPassConfirm.getText() != null ? edtPassConfirm.getText().toString().trim() : "";

        if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Error: debe llenar todos los campos", Toast.LENGTH_SHORT);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Debe ingresar una dirección de correo electrónico válida");
            return;
        }

        if(!password.equals(confirmPass)){
            edtPassConfirm.setError("Las contraseñas debe coincidir");
            return;
        }

        btnSignin.setEnabled(false);

        authRepository.register(name, lastName, email, password).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                btnSignin.setEnabled(true);

                if(response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Algo falló...Pruebe con otras credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                btnSignin.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "Error al establecer conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
