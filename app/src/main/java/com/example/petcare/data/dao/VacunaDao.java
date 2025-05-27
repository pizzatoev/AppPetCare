package com.example.petcare.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.petcare.data.entities.Vacuna;

import java.util.List;

@Dao
public interface VacunaDao {

    @Insert
    void insertar(Vacuna vacuna); // Para precargar vacunas si se desea

    @Query("SELECT * FROM Vacuna")
    List<Vacuna> obtenerTodas();

    @Query("SELECT * FROM Vacuna WHERE id_vacuna = :id LIMIT 1")
    Vacuna obtenerPorId(int id);

}
