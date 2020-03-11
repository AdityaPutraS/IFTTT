package com.ksatukeltiga.ifttw;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class RutinRepository {
        private String DB_NAME = "db_rutin";

        private RutinDatabase rutinDatabase;
        public RutinRepository(Context context) {
            rutinDatabase = Room.databaseBuilder(context, RutinDatabase.class, DB_NAME).build();
        }

        public void insertRutin(String kondisi,
                               String aksi) {
            Rutin rutin = new Rutin();
            rutin.setKondisi(kondisi);
            rutin.setAksi(aksi);

            insertRutin(rutin);
        }

        public void insertRutin(final Rutin rutin) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    rutinDatabase.rutinDao().insertRutin(rutin);
                    return null;
                }
            }.execute();
        }

        public void updateRutin(final Rutin rutin) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    rutinDatabase.rutinDao().updateRutin(rutin);
                    return null;
                }
            }.execute();
        }

        public void deleteRutin(final int id) {
            final LiveData<Rutin> rutin = getRutin(id);
            if(rutin != null) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        rutinDatabase.rutinDao().deleteRutin(rutin.getValue());
                        return null;
                    }
                }.execute();
            }
        }

        public void deleteRutin(final Rutin rutin) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    rutinDatabase.rutinDao().deleteRutin(rutin);
                    return null;
                }
            }.execute();
        }

        public LiveData<Rutin> getRutin(int id) {
            return rutinDatabase.rutinDao().getRutin(id);
        }

        public LiveData<List<Rutin>> getRutin() {
            return rutinDatabase.rutinDao().fetchAllRutin();
        }
}
