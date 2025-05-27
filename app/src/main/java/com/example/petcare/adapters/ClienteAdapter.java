package com.example.petcare.adapters;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.R;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.Cliente;

import java.util.List;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    public interface OnItemClickListener {
        void onEditar(Cliente cliente);
        void onEliminar(Cliente cliente);
    }

    private Context context;
    private List<Cliente> listaClientes;
    private OnItemClickListener listener;
    private PetCareDatabase db;



    public ClienteAdapter(Context context, List<Cliente> listaClientes, OnItemClickListener listener, PetCareDatabase db) {
        this.context = context;
        this.listaClientes = listaClientes;
        this.listener = listener;
        this.db = db;
    }


    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);

        holder.txtNombre.setText("Nombre: " + cliente.nombre);
        holder.txtTelefono.setText("Teléfono: " + cliente.telefono);
        holder.txtCorreo.setText("Correo: " + cliente.correo);
        holder.txtCantidadMascotas.setText("Cant de mascotas: ..."); // temporal mientras carga

        Executors.newSingleThreadExecutor().execute(() -> {
            int cantidad = db.mascotaDao().contarPorCliente(cliente.id_cliente);
            new Handler(Looper.getMainLooper()).post(() -> {
                holder.txtCantidadMascotas.setText("Cant de mascotas: " + cantidad);
            });
        });


        holder.btnEditar.setOnClickListener(v -> listener.onEditar(cliente));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(cliente));
    }


    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtTelefono, txtCorreo, txtCantidadMascotas;
        ImageButton btnEditar, btnEliminar;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtCorreo = itemView.findViewById(R.id.txtCorreo);
            txtCantidadMascotas = itemView.findViewById(R.id.txtCantidadMascotas);

            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    public void actualizarLista(List<Cliente> nuevosClientes) {
        listaClientes = nuevosClientes;
        notifyDataSetChanged();
    }
}
