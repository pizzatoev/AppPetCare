package com.example.petcare.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.R;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.Cliente;
import com.example.petcare.data.entities.Mascota;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FormularioMascotaActivity extends AppCompatActivity {

    private EditText etNombre, etEspecie, etFechaNac;
    private Spinner spinnerCliente;
    private Button btnGuardar;

    private PetCareDatabase db;
    private List<Cliente> listaClientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_mascota);

        etNombre = findViewById(R.id.etNombreMascota);
        etEspecie = findViewById(R.id.etEspecieMascota);
        etFechaNac = findViewById(R.id.etFechaNacMascota);
        spinnerCliente = findViewById(R.id.spinnerDueno);
        btnGuardar = findViewById(R.id.btnGuardarMascota);

        db = PetCareDatabase.getInstance(getApplicationContext());

        cargarClientesEnSpinner();

        btnGuardar.setOnClickListener(view -> guardarMascota());
    }

    private void cargarClientesEnSpinner() {
        Executors.newSingleThreadExecutor().execute(() -> {
            listaClientes = db.clienteDao().obtenerTodos();

            if (listaClientes.isEmpty()) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Debe registrar al menos un cliente", Toast.LENGTH_LONG).show();
                    finish();
                });
                return;
            }

            String[] nombresClientes = new String[listaClientes.size()];
            for (int i = 0; i < listaClientes.size(); i++) {
                Cliente c = listaClientes.get(i);
                nombresClientes[i] = c.nombre + " (" + c.id_cliente + ")";
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresClientes);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCliente.setAdapter(adaptador);
            });
        });
    }

    private void guardarMascota() {
        String nombre = etNombre.getText().toString().trim();
        String especie = etEspecie.getText().toString().trim();
        String fecha = etFechaNac.getText().toString().trim();
        int posCliente = spinnerCliente.getSelectedItemPosition();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(especie) || TextUtils.isEmpty(fecha) || posCliente == -1) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente cliente = listaClientes.get(posCliente);

        Mascota mascota = new Mascota();
        mascota.nombre = nombre;
        mascota.especie = especie;
        mascota.fecha_nac = fecha;
        mascota.id_cliente = cliente.id_cliente;

        Executors.newSingleThreadExecutor().execute(() -> {
            db.mascotaDao().insertar(mascota);
            runOnUiThread(() -> {
                Toast.makeText(this, "Mascota registrada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}