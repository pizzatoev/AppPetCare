package com.example.petcare.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.petcare.data.entities.Mascota;
import com.example.petcare.data.entities.MascotaVacuna;

import java.util.List;

@Dao
public interface MascotaDao {

    @Insert
    void insertar(Mascota mascota);

    @Update
    void actualizar(Mascota mascota);

    @Delete
    void eliminar(Mascota mascota );



    @Query("SELECT * FROM Mascota")
    List<Mascota> obtenerTodas();

    @Query("SELECT * FROM Mascota WHERE id_cliente = :ci")
    List<Mascota> obtenerPorCliente(String ci);

    @Query("SELECT * FROM Mascota WHERE id_mascota = :id LIMIT 1")
    Mascota obtenerPorId(int id);

    @Query("SELECT COUNT(*) FROM Mascota WHERE id_cliente = :ci")
    int contarPorCliente(String ci);


}
