package com.example.spacexcrew.network;

import com.example.spacexcrew.model.Crew;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CrewAPI {

    @GET("v4/crew")
    Call<List<Crew>> getAllCrews();
}
