package com.example.spacexcrew.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.spacexcrew.dao.CrewDao;
import com.example.spacexcrew.model.Crew;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Crew.class}, version = 1, exportSchema = false)
public abstract class CrewDatabase extends RoomDatabase {

    private static final String DATABAE_SNAME = "crew_database";

    public abstract CrewDao crewDao();

    private static volatile CrewDatabase INSTANCE;

    public static CrewDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CrewDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CrewDatabase.class, DATABAE_SNAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
