package com.ksatukeltiga.ifttw;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class RoutineRepository {
        private String DB_NAME = "db_routine";

        private RoutineDatabase routineDatabase;
        public RoutineRepository(Context context) {
            routineDatabase = Room.databaseBuilder(context, RoutineDatabase.class, DB_NAME).build();
        }

        public void insertRoutine(ConditionModule kondisi,
                                  ActionModule aksi,
                                  Context context)  {
            Routine routine = new Routine(kondisi, aksi);
            routine.initRoutine(context);

            insertRoutine(routine);
        }

        public void insertRoutine(final Routine routine) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    routineDatabase.routineDao().insertRoutine(routine);
                    return null;
                }
            }.execute();
        }

        public void updateRoutine(final Routine routine) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    routineDatabase.routineDao().updateRoutine(routine);
                    return null;
                }
            }.execute();
        }

        public void deleteRoutine(final int id) {
            final LiveData<Routine> routine = getRoutine(id);
            if(routine != null) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        routineDatabase.routineDao().deleteRoutine(routine.getValue());
                        return null;
                    }
                }.execute();
            }
        }

        public void deleteRoutine(final Routine routine) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    routineDatabase.routineDao().deleteRoutine(routine);
                    return null;
                }
            }.execute();
        }

        public LiveData<Routine> getRoutine(int id) {
            return routineDatabase.routineDao().getRoutine(id);
        }

        public LiveData<List<Routine>> getRoutine() {
            return routineDatabase.routineDao().fetchAllRoutine();
        }
}
