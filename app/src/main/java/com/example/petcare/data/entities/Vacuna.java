package com.example.petcare.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vacuna {

    @PrimaryKey(autoGenerate = true)
    public int id_vacuna;

    public String nombre;
    public String fabricante;
    public String dosis;
    public String tipo;

    public Vacuna(int id_vacuna, String nombre, String fabricante, String dosis, String tipo) {
        this.id_vacuna = id_vacuna;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.dosis = dosis;
        this.tipo = tipo;
    }
}
