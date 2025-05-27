package com.example.petcare.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cliente {

    @PrimaryKey
    @NonNull
    public String id_cliente;

    public String nombre;
    public String telefono;
    public String correo;
}
