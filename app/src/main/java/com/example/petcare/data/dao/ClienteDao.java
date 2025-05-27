package com.example.petcare.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.petcare.data.entities.Cliente;

import java.util.List;

@Dao
public interface ClienteDao {

    @Insert
    void insertar(Cliente cliente);

    @Update
    void actualizar(Cliente cliente);

    @Query("SELECT * FROM Cliente WHERE id_cliente = :ci LIMIT 1")
    Cliente obtenerPorId(String ci);

    @Delete
    void eliminar(Cliente cliente);

    @Query("SELECT * FROM Cliente")
    List<Cliente> obtenerTodos();

    @Query("SELECT COUNT(*) FROM Cliente WHERE id_cliente = :ci")
    int existeCliente(String ci);
}
