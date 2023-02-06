package com.example.marveluniverse.data.repository

import com.example.marveluniverse.data.api.MarvelAPI
import com.example.marveluniverse.data.model.CharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class CharacterRepository(private val api: MarvelAPI) {

    suspend fun fetchCharacters(offset: Int): Flow<NetworkResult<CharacterResponse>?> = flow {
        emit(NetworkResult.loading(null))

        try {
            val response = api.getCharacters(offset)
            emit(NetworkResult.success(response.body()))
        } catch (e: Exception) {
            emit(NetworkResult.error(message = e.message ?: "Unknow Error", null))
        }
    }
}