package com.example.spacexcrew.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spacexcrew.model.Crew;
import com.example.spacexcrew.repository.CrewRepository;

import java.util.List;

public class CrewViewModel extends AndroidViewModel {

    private CrewRepository crewRepository;
//    private CrewRemoteRepository crewRemoteRepository;
    private LiveData<List<Crew>> getAllCrews;
//    private LiveData<List<Crew>> getRemoteCrews;

    public CrewViewModel(@NonNull Application application) {
        super(application);
        crewRepository = new CrewRepository(application);
//        crewRemoteRepository = new CrewRemoteRepository();
        getAllCrews = crewRepository.getAllCrew();
//        getRemoteCrews = crewRemoteRepository.getAllRemoteCrews();
    }

    public void insert(List<Crew> list) {
        crewRepository.insert(list);
    }

    public void deteleAll() {
        crewRepository.deleteAll();
    }

//    public LiveData<List<Crew>> getRemoteCrew() {
//        return getRemoteCrews;
//    }

    public LiveData<List<Crew>> getAllCrew() {
        return getAllCrews;
    }

}
