package com.example.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.R;
import com.example.petcare.adapters.VacunaAplicadaAdapter;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.MascotaVacuna;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.petcare.adapters.VacunaAplicadaAdapter;


import java.util.List;

public class ListaVacunasAplicadasActivity extends AppCompatActivity {

    private RecyclerView recyclerVacunas;
    private FloatingActionButton fabAgregar;
    private PetCareDatabase db;
    private VacunaAplicadaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacunas_aplicadas);

        recyclerVacunas = findViewById(R.id.recyclerVacunasAplicadas);
        fabAgregar = findViewById(R.id.fabAgregarVacuna);
        db = PetCareDatabase.getInstance(getApplicationContext());

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaVacunasAplicadasActivity.this, FormularioVacunaAplicadaActivity.class));
            }
        });

        cargarVacunasAplicadas();
    }

    private void cargarVacunasAplicadas() {
        VacunaAplicadaAdapter.cargarVacunasAplicadas(
                this,
                db,
                recyclerVacunas,
                mv -> {
                    db.mascotaVacunaDao().eliminar(mv);
                    cargarVacunasAplicadas();
                    Toast.makeText(this, "Vacuna eliminada", Toast.LENGTH_SHORT).show();
                }
        );

    }


    @Override
    protected void onResume() {
        super.onResume();
        cargarVacunasAplicadas();
    }
}
