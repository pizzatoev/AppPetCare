package com.example.petcare.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.petcare.data.dao.ClienteDao;
import com.example.petcare.data.dao.MascotaDao;
import com.example.petcare.data.dao.VacunaDao;
import com.example.petcare.data.dao.MascotaVacunaDao;
import com.example.petcare.data.entities.Cliente;
import com.example.petcare.data.entities.Mascota;
import com.example.petcare.data.entities.Vacuna;
import com.example.petcare.data.entities.MascotaVacuna;

@Database(entities = {
        Cliente.class,
        Mascota.class,
        Vacuna.class,
        MascotaVacuna.class
}, version = 1)
public abstract class PetCareDatabase extends RoomDatabase {

    private static PetCareDatabase INSTANCE;

    public abstract ClienteDao clienteDao();
    public abstract MascotaDao mascotaDao();
    public abstract VacunaDao vacunaDao();
    public abstract MascotaVacunaDao mascotaVacunaDao();

    public static synchronized PetCareDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PetCareDatabase.class, "petcare_db")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new VacunaPreCargada(getInstance(context).vacunaDao()).execute();
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
