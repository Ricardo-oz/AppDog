package com.example.appdog.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsApi {

    @GET("breeds/list/all")
    suspend fun getBreedsList(): Response<RetroBreed>

    @GET("breed/{breed}/images")
    suspend fun getImagesList(@Path("breed") breed: String): Response<RetroImages>


}