package com.example.petcare.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Cliente.class,
        parentColumns = "id_cliente",
        childColumns = "id_cliente",
        onDelete = CASCADE
))
public class Mascota {

    @PrimaryKey(autoGenerate = true)
    public int id_mascota;

    public String nombre;
    public String especie;
    public String fecha_nac;

    @NonNull
    public String id_cliente;
}
