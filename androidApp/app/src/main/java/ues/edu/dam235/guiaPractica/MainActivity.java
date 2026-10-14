package ues.edu.dam235.guiaPractica;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ues.edu.dam235.guiaPractica.ui.login.LoginActivity;
import ues.edu.dam235.guiaPractica.ui.products.ProductsActivity;
import ues.edu.dam235.guiaPractica.ui.register.RegisterActivity;
import ues.edu.dam235.guiaPractica.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager session = new SessionManager(this);

        Intent intent;
        if(session.isLoggedIn()){
            intent = new Intent(this, ProductsActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}