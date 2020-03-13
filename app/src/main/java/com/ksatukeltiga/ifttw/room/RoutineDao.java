package com.ksatukeltiga.ifttw.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoutineDao {

        @Insert
        Long insertRoutine(Routine note);


        @Query("SELECT * FROM Routine")
        LiveData<List<Routine>> fetchAllRoutine();

        @Query("SELECT * FROM Routine WHERE active =:status")
        LiveData<List<Routine>> fetchAllRoutine(boolean status);


        @Query("SELECT * FROM Routine WHERE id =:routineId")
        LiveData<Routine> getRoutine(int routineId);


        @Update
        void updateRoutine(Routine routine);


        @Delete
        void deleteRoutine(Routine routine);
}
