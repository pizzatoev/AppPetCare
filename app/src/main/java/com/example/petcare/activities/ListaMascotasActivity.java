package com.example.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.R;
import com.example.petcare.adapters.MascotaAdapter;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.Mascota;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaMascotasActivity extends AppCompatActivity {

    private RecyclerView recyclerMascotas;
    private FloatingActionButton fabAgregarMascota;
    private PetCareDatabase db;
    private MascotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        recyclerMascotas = findViewById(R.id.recyclerMascotas);
        fabAgregarMascota = findViewById(R.id.fabAgregarMascota);
        db = PetCareDatabase.getInstance(getApplicationContext());

        fabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaMascotasActivity.this, FormularioMascotaActivity.class));
            }
        });

        cargarMascotas();
    }

    private void cargarMascotas() {
        List<Mascota> mascotas = db.mascotaDao().obtenerTodas();

        adapter = new MascotaAdapter(mascotas, db, new MascotaAdapter.OnItemClickListener() {
            @Override
            public void onEditar(Mascota mascota) {
                Intent intent = new Intent(ListaMascotasActivity.this, FormularioMascotaActivity.class);
                intent.putExtra("id_mascota", mascota.id_mascota);
                startActivity(intent);
            }

            @Override
            public void onEliminar(Mascota mascota) {
                db.mascotaDao().eliminar(mascota);
                cargarMascotas();
                Toast.makeText(ListaMascotasActivity.this, "Mascota eliminada", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerMascotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerMascotas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarMascotas();
    }
}
