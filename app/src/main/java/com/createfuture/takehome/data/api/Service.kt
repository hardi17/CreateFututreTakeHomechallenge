package com.createfuture.takehome.data.api

import com.createfuture.takehome.data.models.ApiCharacter
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface Service {

    @GET("/characters")
    suspend fun getCharacters(): List<ApiCharacter>

}
