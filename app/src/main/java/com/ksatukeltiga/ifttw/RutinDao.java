package com.ksatukeltiga.ifttw;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RutinDao {

        @Insert
        Long insertRutin(Rutin note);


        @Query("SELECT * FROM Rutin")
        LiveData<List<Rutin>> fetchAllRutin();


        @Query("SELECT * FROM Rutin WHERE id =:rutinId")
        LiveData<Rutin> getRutin(int rutinId);


        @Update
        void updateRutin(Rutin rutin);


        @Delete
        void deleteRutin(Rutin rutin);
}
