package com.createfuture.takehome.data.repository

import com.createfuture.takehome.data.api.Service
import com.createfuture.takehome.data.models.ApiCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val service: Service
) {

    fun fetchCharacters() : Flow<List<ApiCharacter>> {
        return flow {
            val response = service.getCharacters()
            emit(response)
        }
    }
}