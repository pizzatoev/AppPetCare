package com.example.petcare.data;

import android.os.AsyncTask;

import com.example.petcare.data.entities.Vacuna;
import com.example.petcare.data.dao.VacunaDao;

public class VacunaPreCargada extends AsyncTask<Void, Void, Void> {

    private final VacunaDao vacunaDao;

    public VacunaPreCargada(VacunaDao dao) {
        this.vacunaDao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        vacunaDao.insertar(new Vacuna(0, "Rabia", "Zoetis", "1 dosis", "Obligatoria"));
        vacunaDao.insertar(new Vacuna(0, "Parvovirus", "Bayer", "2 dosis", "Obligatoria"));
        vacunaDao.insertar(new Vacuna(0, "Múltiple", "Virbac", "3 dosis", "Refuerzo"));
        return null;
    }
}
