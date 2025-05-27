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
import com.example.petcare.data.entities.MascotaVacuna;
import com.example.petcare.data.entities.Vacuna;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FormularioVacunaAplicadaActivity extends AppCompatActivity {

    private Spinner spinnerMascota, spinnerVacuna;
    private EditText etFecha, etObservaciones;
    private Button btnGuardar;

    private PetCareDatabase db;
    private List<Mascota> listaMascotas = new ArrayList<>();
    private List<Vacuna> listaVacunas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_vacuna_aplicada);

        spinnerMascota = findViewById(R.id.spinnerMascota);
        spinnerVacuna = findViewById(R.id.spinnerVacuna);
        etFecha = findViewById(R.id.etFechaAplicacion);
        etObservaciones = findViewById(R.id.etObservaciones);
        btnGuardar = findViewById(R.id.btnGuardarVacuna);

        db = PetCareDatabase.getInstance(getApplicationContext());

        cargarSpinners();

        btnGuardar.setOnClickListener(view -> guardarVacunaAplicada());
    }

    private void cargarSpinners() {
        Executors.newSingleThreadExecutor().execute(() -> {
            listaMascotas = db.mascotaDao().obtenerTodas();
            listaVacunas = db.vacunaDao().obtenerTodas();
            List<Cliente> clientes = db.clienteDao().obtenerTodos();

            if (listaMascotas.isEmpty() || listaVacunas.isEmpty()) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Debe haber al menos una mascota y una vacuna", Toast.LENGTH_LONG).show();
                    finish();
                });
                return;
            }

            String[] nombresMascotas = new String[listaMascotas.size()];
            for (int i = 0; i < listaMascotas.size(); i++) {
                Mascota mascota = listaMascotas.get(i);
                String nombreDueno = "Desconocido";
                for (Cliente c : clientes) {
                    if (c.id_cliente.equals(mascota.id_cliente)) {
                        nombreDueno = c.nombre;
                        break;
                    }
                }
                nombresMascotas[i] = mascota.nombre + " (" + nombreDueno + ")";
            }

            String[] nombresVacunas = new String[listaVacunas.size()];
            for (int i = 0; i < listaVacunas.size(); i++) {
                Vacuna v = listaVacunas.get(i);
                nombresVacunas[i] = v.nombre + " (" + v.tipo + ")";
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adaptadorMascotas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresMascotas);
                adaptadorMascotas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMascota.setAdapter(adaptadorMascotas);

                ArrayAdapter<String> adaptadorVacunas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresVacunas);
                adaptadorVacunas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerVacuna.setAdapter(adaptadorVacunas);
            });
        });
    }

    private void guardarVacunaAplicada() {
        String fecha = etFecha.getText().toString().trim();
        String obs = etObservaciones.getText().toString().trim();

        if (TextUtils.isEmpty(fecha)) {
            Toast.makeText(this, "La fecha es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        int posMascota = spinnerMascota.getSelectedItemPosition();
        int posVacuna = spinnerVacuna.getSelectedItemPosition();

        if (posMascota == -1 || posVacuna == -1) {
            Toast.makeText(this, "Debe seleccionar mascota y vacuna", Toast.LENGTH_SHORT).show();
            return;
        }

        Mascota mascota = listaMascotas.get(posMascota);
        Vacuna vacuna = listaVacunas.get(posVacuna);

        MascotaVacuna mv = new MascotaVacuna();
        mv.id_mascota = mascota.id_mascota;
        mv.id_vacuna = vacuna.id_vacuna;
        mv.fecha_aplicacion = fecha;
        mv.observaciones = obs;

        Executors.newSingleThreadExecutor().execute(() -> {
            db.mascotaVacunaDao().insertar(mv);
            runOnUiThread(() -> {
                Toast.makeText(this, "Vacuna registrada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
