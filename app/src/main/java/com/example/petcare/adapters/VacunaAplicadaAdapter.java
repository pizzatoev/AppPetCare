package com.example.petcare.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.R;
import com.example.petcare.activities.FormularioVacunaAplicadaActivity;
import com.example.petcare.activities.ListaVacunasAplicadasActivity;
import com.example.petcare.data.PetCareDatabase;
import com.example.petcare.data.entities.Mascota;
import com.example.petcare.data.entities.MascotaVacuna;
import com.example.petcare.data.entities.Vacuna;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class VacunaAplicadaAdapter extends RecyclerView.Adapter<VacunaAplicadaAdapter.VacunaViewHolder> {

    public static void cargarVacunasAplicadas(
            Context context,
            PetCareDatabase db,
            RecyclerView recycler,
            OnItemClickListener listener
    ) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<MascotaVacuna> lista = db.mascotaVacunaDao().obtenerTodas();
            List<Mascota> mascotas = db.mascotaDao().obtenerTodas();
            List<Vacuna> vacunas = db.vacunaDao().obtenerTodas();

            List<VacunaAplicadaConDetalles> listaDetalles = new ArrayList<>();
            for (MascotaVacuna mv : lista) {
                String nombreMascota = "Desconocida";
                String nombreVacuna = "Desconocida";

                for (Mascota m : mascotas) {
                    if (m.id_mascota == mv.id_mascota) {
                        nombreMascota = m.nombre;
                        break;
                    }
                }

                for (Vacuna v : vacunas) {
                    if (v.id_vacuna == mv.id_vacuna) {
                        nombreVacuna = v.nombre;
                        break;
                    }
                }

                VacunaAplicadaConDetalles detalle = new VacunaAplicadaConDetalles();
                detalle.mv = mv;
                detalle.nombreMascota = nombreMascota;
                detalle.nombreVacuna = nombreVacuna;
                listaDetalles.add(detalle);
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                VacunaAplicadaAdapter adapter = new VacunaAplicadaAdapter(context, listaDetalles, listener);
                recycler.setLayoutManager(new LinearLayoutManager(context));
                recycler.setAdapter(adapter);
            });
        });
    }


    public interface OnItemClickListener {
        void onEliminar(MascotaVacuna mv);
    }

    private Context context;
    private List<VacunaAplicadaConDetalles> lista;
    private OnItemClickListener listener;

    public VacunaAplicadaAdapter(Context context, List<VacunaAplicadaConDetalles> lista, OnItemClickListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VacunaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacuna_aplicada, parent, false);
        return new VacunaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VacunaViewHolder holder, int position) {
        VacunaAplicadaConDetalles detalle = lista.get(position);

        holder.txtMascota.setText("Mascota: " + detalle.nombreMascota);
        holder.txtVacuna.setText("Vacuna: " + detalle.nombreVacuna);
        holder.txtFecha.setText("Fecha: " + detalle.mv.fecha_aplicacion);
        holder.txtObservaciones.setText("Observaciones: " + detalle.mv.observaciones);
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(detalle.mv));


        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormularioVacunaAplicadaActivity.class);
            intent.putExtra("id_mascota", detalle.mv.id_mascota);
            intent.putExtra("id_vacuna", detalle.mv.id_vacuna);
            intent.putExtra("fecha_aplicacion", detalle.mv.fecha_aplicacion);
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(detalle.mv));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class VacunaViewHolder extends RecyclerView.ViewHolder {
        TextView txtMascota, txtVacuna, txtFecha, txtObservaciones;
        ImageButton btnEditar, btnEliminar;

        public VacunaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMascota = itemView.findViewById(R.id.txtMascota);
            txtVacuna = itemView.findViewById(R.id.txtVacuna);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtObservaciones = itemView.findViewById(R.id.txtObservaciones);
            btnEditar = itemView.findViewById(R.id.btnEditarVacuna);
            btnEliminar = itemView.findViewById(R.id.btnEliminarVacuna);
        }
    }

    public void actualizarLista(List<VacunaAplicadaConDetalles> nuevas) {
        lista = nuevas;
        notifyDataSetChanged();
    }
}