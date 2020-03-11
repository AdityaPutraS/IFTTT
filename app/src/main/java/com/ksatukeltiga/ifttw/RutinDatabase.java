package com.ksatukeltiga.ifttw;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Rutin.class}, version = 1, exportSchema = false)
public abstract class RutinDatabase extends RoomDatabase {
    public abstract RutinDao rutinDao();
}
