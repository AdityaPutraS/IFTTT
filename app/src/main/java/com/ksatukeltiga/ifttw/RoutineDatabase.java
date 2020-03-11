package com.ksatukeltiga.ifttw;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Routine.class}, version = 1, exportSchema = false)
public abstract class RoutineDatabase extends RoomDatabase {
    public abstract RoutineDao routineDao();
}
