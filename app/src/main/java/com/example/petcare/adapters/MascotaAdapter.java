package com.example.petcare.adapters;

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
import com.example.petcare.data.entities.Mascota;

import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    public interface OnItemClickListener {
        void onEditar(Mascota mascota);
        void onEliminar(Mascota mascota);
    }

    private List<Mascota> listaMascotas;
    private PetCareDatabase db;
    private OnItemClickListener listener;

    public MascotaAdapter(List<Mascota> listaMascotas, PetCareDatabase db, OnItemClickListener listener) {
        this.listaMascotas = listaMascotas;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota, parent, false);
        return new MascotaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Mascota mascota = listaMascotas.get(position);

        holder.txtNombre.setText("Nombre: " + mascota.nombre);
        holder.txtEspecie.setText("Especie: " + mascota.especie);

        // Obtener nombre del dueño
        Cliente dueno = db.clienteDao().obtenerTodos().stream()
                .filter(c -> c.id_cliente.equals(mascota.id_cliente))
                .findFirst()
                .orElse(null);

        holder.txtDueno.setText("Dueño: " + (dueno != null ? dueno.nombre : "Desconocido"));

        int vacunas = db.mascotaVacunaDao().contarVacunasPorMascota(mascota.id_mascota);
        holder.txtVacunas.setText("Nro Vacunas: " + vacunas);

        holder.btnEditar.setOnClickListener(v -> listener.onEditar(mascota));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(mascota));
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtEspecie, txtDueno, txtVacunas;
        ImageButton btnEditar, btnEliminar;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreMascota);
            txtEspecie = itemView.findViewById(R.id.txtEspecieMascota);
            txtDueno = itemView.findViewById(R.id.txtDuenoMascota);
            txtVacunas = itemView.findViewById(R.id.txtCantidadVacunas);
            btnEditar = itemView.findViewById(R.id.btnEditarMascota);
            btnEliminar = itemView.findViewById(R.id.btnEliminarMascota);
        }
    }

    public void actualizarLista(List<Mascota> nuevasMascotas) {
        listaMascotas = nuevasMascotas;
        notifyDataSetChanged();
    }
}
