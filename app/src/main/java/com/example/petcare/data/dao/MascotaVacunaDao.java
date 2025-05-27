package com.example.petcare.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.petcare.data.entities.MascotaVacuna;

import java.util.List;

@Dao
public interface MascotaVacunaDao {

    @Insert
    void insertar(MascotaVacuna mv);

    @Update
    void actualizar(MascotaVacuna mv);

    @Delete
    void eliminar(MascotaVacuna mv);


    @Query("SELECT * FROM MascotaVacuna")
    List<MascotaVacuna> obtenerTodas();

    @Query("SELECT COUNT(*) FROM MascotaVacuna WHERE id_mascota = :mascotaId")
    int contarVacunasPorMascota(int mascotaId);

    @Query("SELECT * FROM MascotaVacuna WHERE id_mascota = :idMascota AND id_vacuna = :idVacuna AND fecha_aplicacion = :fecha LIMIT 1")
    MascotaVacuna obtenerPorClave(int idMascota, int idVacuna, String fecha);

    @Query("DELETE FROM MascotaVacuna WHERE id_mascota = :idMascota AND id_vacuna = :idVacuna AND fecha_aplicacion = :fecha")
    void eliminarPorClave(int idMascota, int idVacuna, String fecha);

    @Query("SELECT COUNT(*) FROM Mascota WHERE id_cliente = :ci")
    int contarPorCliente(String ci);




}
