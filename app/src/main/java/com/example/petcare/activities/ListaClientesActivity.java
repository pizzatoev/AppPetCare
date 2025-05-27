package com.example.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.R;
import com.example.petcare.adapters.ClienteAdapter;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerClientes;
    private ClienteAdapter adapter;
    private PetCareDatabase db;
    private FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        recyclerClientes = findViewById(R.id.recyclerClientes);
        fabAgregar = findViewById(R.id.fabAgregarCliente);
        db = PetCareDatabase.getInstance(getApplicationContext());

        cargarClientes();

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaClientesActivity.this, FormularioClienteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cargarClientes() {
        List<Cliente> clientes = db.clienteDao().obtenerTodos();
        adapter = new ClienteAdapter(this, clientes, new ClienteAdapter.OnItemClickListener() {
            @Override
            public void onEditar(Cliente cliente) {
                Intent intent = new Intent(ListaClientesActivity.this, FormularioClienteActivity.class);
                intent.putExtra("ci_cliente", cliente.id_cliente);
                startActivity(intent);
            }

            @Override
            public void onEliminar(Cliente cliente) {
                db.clienteDao().eliminar(cliente);
                cargarClientes();
                Toast.makeText(ListaClientesActivity.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
            }
        }, db);



        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerClientes.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarClientes(); // Por si vuelves desde otro Activity
    }
}
