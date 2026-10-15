package ues.edu.dam235.guiaPractica.ui.products;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ues.edu.dam235.guiaPractica.R;
import ues.edu.dam235.guiaPractica.data.model.request.ProductRequest;
import ues.edu.dam235.guiaPractica.data.model.response.LogoutResponse;
import ues.edu.dam235.guiaPractica.data.model.response.ProductOperationResponse;
import ues.edu.dam235.guiaPractica.data.model.response.ProductResponse;
import ues.edu.dam235.guiaPractica.data.repository.AuthRepository;
import ues.edu.dam235.guiaPractica.data.repository.ProductRepository;
import ues.edu.dam235.guiaPractica.ui.login.LoginActivity;
import ues.edu.dam235.guiaPractica.utils.SessionManager;

public class ProductsActivity extends AppCompatActivity {

    private AuthRepository authRepository;
    private ProductRepository productRepository;
    private SessionManager session;
    private LinearLayout productsContainer;
    private TextView txtEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        ImageButton btnLogout = findViewById(R.id.btnLogout);
        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        productsContainer = findViewById(R.id.productsContainer);
        txtEstado = findViewById(R.id.txtEstado);

        authRepository = new AuthRepository(this);
        productRepository = new ProductRepository(this);
        session = new SessionManager(this);

        btnLogout.setOnClickListener(v -> logout());
        btnAdd.setOnClickListener(v -> abrirFormulario(null));
        cargarProductos();
    }

    private int dp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void mensaje(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void cargarProductos() {
        // txtEstado es el primer hijo del contenedor: conservarlo y borrar solo la lista.
        int adicionales = productsContainer.getChildCount() - 1;
        if (adicionales > 0) productsContainer.removeViews(1, adicionales);
        txtEstado.setVisibility(View.VISIBLE);
        txtEstado.setText("Cargando productos...");

        productRepository.getProducts().enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call,
                                   Response<List<ProductResponse>> response) {
                if (!response.isSuccessful()) {
                    txtEstado.setText("Error HTTP " + response.code() + " al consultar productos");
                    return;
                }

                List<ProductResponse> productos = response.body();
                if (productos == null || productos.isEmpty()) {
                    txtEstado.setText("No hay productos registrados");
                    return;
                }

                txtEstado.setVisibility(View.GONE);
                for (ProductResponse product : productos) agregarFila(product);
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                txtEstado.setText("Error de conexion: " + t.getClass().getSimpleName());
            }
        });
    }

    private void agregarFila(ProductResponse product) {
        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setPadding(dp(14), dp(14), dp(14), dp(12));

        GradientDrawable fondo = new GradientDrawable();
        fondo.setColor(Color.rgb(241, 241, 250));
        fondo.setCornerRadius(dp(12));
        tarjeta.setBackground(fondo);

        TextView datos = new TextView(this);
        datos.setText("Codigo: " + product.getCode() + "\nProducto: " + product.getName()
                + "\nEstado: " + (product.isStatus() ? "Activo" : "Inactivo"));
        datos.setTextSize(16);
        datos.setTextColor(Color.BLACK);
        tarjeta.addView(datos);

        LinearLayout botones = new LinearLayout(this);
        botones.setOrientation(LinearLayout.HORIZONTAL);

        Button btnEditar = new Button(this);
        btnEditar.setText("Editar");
        btnEditar.setOnClickListener(v -> abrirFormulario(product));
        botones.addView(btnEditar);

        Button btnEliminar = new Button(this);
        btnEliminar.setText("Eliminar");
        btnEliminar.setOnClickListener(v -> confirmarEliminar(product));
        botones.addView(btnEliminar);

        tarjeta.addView(botones);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, dp(12));
        productsContainer.addView(tarjeta, params);
    }

    private void abrirFormulario(ProductResponse productoExistente) {
        View form = getLayoutInflater().inflate(R.layout.add_product, null);
        TextInputEditText inputNombre = form.findViewById(R.id.edtProductName);
        Switch swEstado = form.findViewById(R.id.swProductStatus);
        TextView tvEstado = form.findViewById(R.id.tvProductStatus);
        TextView titulo = form.findViewById(R.id.tvDialogTitle);
        Button btnCancelar = form.findViewById(R.id.btnCancelar);
        Button btnGuardar = form.findViewById(R.id.btnGuardar);
        boolean editando = productoExistente != null;

        titulo.setText(editando ? "Editar producto" : "Agregar producto");
        btnGuardar.setText(editando ? "Guardar" : "Agregar");
        if (editando) {
            inputNombre.setText(productoExistente.getName());
            swEstado.setChecked(productoExistente.isStatus());
        }
        tvEstado.setText(swEstado.isChecked() ? "Activo" : "Inactivo");
        swEstado.setOnCheckedChangeListener((buttonView, checked) ->
                tvEstado.setText(checked ? "Activo" : "Inactivo"));

        AlertDialog dialog = new AlertDialog.Builder(this).setView(form).create();
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        btnGuardar.setOnClickListener(v -> {
            String nombre = inputNombre.getText() == null ? "" : inputNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                inputNombre.setError("Ingresa un nombre");
                return;
            }
            btnGuardar.setEnabled(false);
            Call<ProductOperationResponse> solicitud;
            if (editando) {
                ProductResponse actualizado = new ProductResponse();
                actualizado.setCode(productoExistente.getCode());
                actualizado.setName(nombre);
                actualizado.setStatus(swEstado.isChecked());
                solicitud = productRepository.updateProduct(productoExistente.getCode(), actualizado);
            } else {
                solicitud = productRepository.createProduct(new ProductRequest(nombre, swEstado.isChecked()));
            }

            solicitud.enqueue(new Callback<ProductOperationResponse>() {
                @Override
                public void onResponse(Call<ProductOperationResponse> call,
                                       Response<ProductOperationResponse> response) {
                    btnGuardar.setEnabled(true);
                    ProductOperationResponse body = response.body();
                    if (response.isSuccessful() && body != null && body.isSuccess()) {
                        mensaje(body.getMessage() != null ? body.getMessage() : "Operacion exitosa");
                        dialog.dismiss();
                        cargarProductos();
                    } else {
                        mensaje("No se pudo guardar (HTTP " + response.code() + ")");
                    }
                }
                @Override
                public void onFailure(Call<ProductOperationResponse> call, Throwable t) {
                    btnGuardar.setEnabled(true);
                    mensaje("Error de conexion: " + t.getClass().getSimpleName());
                }
            });
        });
        dialog.show();
    }

    private void confirmarEliminar(ProductResponse product) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar producto")
                .setMessage("¿Eliminar '" + product.getName() + "'?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    productRepository.deleteProduct(product.getCode())
                            .enqueue(new Callback<ProductOperationResponse>() {
                        @Override
                        public void onResponse(Call<ProductOperationResponse> call,
                                               Response<ProductOperationResponse> response) {
                            ProductOperationResponse body = response.body();
                            if (response.isSuccessful() && body != null && body.isSuccess()) {
                                mensaje("Producto eliminado");
                                cargarProductos();
                            } else {
                                mensaje("No se pudo eliminar (HTTP " + response.code() + ")");
                            }
                        }
                        @Override
                        public void onFailure(Call<ProductOperationResponse> call, Throwable t) {
                            mensaje("Error de conexion: " + t.getClass().getSimpleName());
                        }
                    });
                }).show();
    }

    private void logout() {
        authRepository.logout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    mensaje("Sesion cerrada exitosamente");
                    session.logout();
                    startActivity(new Intent(ProductsActivity.this, LoginActivity.class));
                    finish();
                } else {
                    mensaje("No se pudo cerrar sesion (HTTP " + response.code() + ")");
                }
            }
            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                mensaje("Error al establecer conexion con el servidor");
            }
        });
    }
}
