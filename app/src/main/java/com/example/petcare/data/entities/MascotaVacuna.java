package com.example.petcare.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"id_mascota", "id_vacuna"},
        foreignKeys = {
                @ForeignKey(entity = Mascota.class,
                        parentColumns = "id_mascota",
                        childColumns = "id_mascota",
                        onDelete = CASCADE),
                @ForeignKey(entity = Vacuna.class,
                        parentColumns = "id_vacuna",
                        childColumns = "id_vacuna",
                        onDelete = CASCADE)
        }
)
public class MascotaVacuna {

    @NonNull
    public int id_mascota;

    @NonNull
    public int id_vacuna;

    public String fecha_aplicacion;
    public String observaciones;
}
