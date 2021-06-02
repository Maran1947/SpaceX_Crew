package com.example.spacexcrew.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.spacexcrew.AppExecutors;
import com.example.spacexcrew.dao.CrewDao;
import com.example.spacexcrew.database.CrewDatabase;
import com.example.spacexcrew.model.Crew;
import com.example.spacexcrew.network.CrewAPI;
import com.example.spacexcrew.network.RetrofitRequest;

import java.util.List;

public class CrewRepository {

    private CrewDatabase database;
    private LiveData<List<Crew>> getAllCrews;
//    private List<Crew> getRemoteCrews;
//    private CrewRemoteRepository crewRemoteRepository;

    public CrewRepository(Application application) {
        database = CrewDatabase.getDatabase(application);
//        crewRemoteRepository = new CrewRemoteRepository();
        getAllCrews = database.crewDao().getAllCrews();
//        getRemoteCrews = crewRemoteRepository.getAllRemoteCrews();
    }

    public void insert(List<Crew> list) {
        Log.d("insert", "insert: " + list.toString());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.crewDao().insert(list);
            }
        });
    }

    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.crewDao().deleteAll();
            }
        });
    }

    public LiveData<List<Crew>> getAllCrew() {
        return getAllCrews;
    }
}
