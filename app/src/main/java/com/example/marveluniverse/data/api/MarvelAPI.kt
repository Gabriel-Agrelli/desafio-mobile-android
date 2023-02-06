package com.example.marveluniverse.data.api

import com.example.marveluniverse.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("v1/public/characters?")
    suspend fun getCharacters(@Query("offset") offset: Int): Response<CharacterResponse>
}