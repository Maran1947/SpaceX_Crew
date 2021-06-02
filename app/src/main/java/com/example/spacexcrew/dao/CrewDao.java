package com.example.spacexcrew.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spacexcrew.model.Crew;

import java.util.List;

@Dao
public interface CrewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Crew> crewList);

    @Query("SELECT * FROM crews")
    LiveData<List<Crew>> getAllCrews();

    @Query("DELETE FROM crews")
    void deleteAll();

}
