package com.example.petcare.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.R;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.Cliente;

import java.util.concurrent.Executors;

public class FormularioClienteActivity extends AppCompatActivity {

    private EditText etCi, etNombre, etTelefono, etCorreo;
    private Button btnGuardar;
    private PetCareDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);

        etCi = findViewById(R.id.etCI);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = PetCareDatabase.getInstance(getApplicationContext());

        btnGuardar.setOnClickListener(view -> guardarCliente());
    }

    private void guardarCliente() {
        String ci = etCi.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        if (TextUtils.isEmpty(ci) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            Cliente existente = db.clienteDao().obtenerPorId(ci);
            if (existente != null) {
                runOnUiThread(() -> Toast.makeText(this, "El CI ya está registrado", Toast.LENGTH_SHORT).show());
                return;
            }

            Cliente cliente = new Cliente();
            cliente.id_cliente = ci;
            cliente.nombre = nombre;
            cliente.telefono = telefono;
            cliente.correo = correo;

            db.clienteDao().insertar(cliente);

            runOnUiThread(() -> {
                Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
