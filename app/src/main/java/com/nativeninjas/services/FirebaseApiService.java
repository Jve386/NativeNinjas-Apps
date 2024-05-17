package com.nativeninjas.services;

import com.nativeninjas.modelo.Partida;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FirebaseApiService {
    // Obtener una partida por su ID
    @GET("partida/{id}")
    Call<Partida> obtenerPartida(@Path("id") String id);
}