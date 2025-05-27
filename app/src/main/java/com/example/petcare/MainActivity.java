package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.activities.ListaClientesActivity;
import com.example.petcare.activities.ListaMascotasActivity;
import com.example.petcare.activities.ListaVacunasAplicadasActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnClientes, btnMascotas, btnVacunas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClientes = findViewById(R.id.btnClientes);
        btnMascotas = findViewById(R.id.btnMascotas);
        btnVacunas = findViewById(R.id.btnVacunas);

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListaClientesActivity.class));
            }
        });

        btnMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListaMascotasActivity.class));
            }
        });

        btnVacunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListaVacunasAplicadasActivity.class));
            }
        });
    }
}
