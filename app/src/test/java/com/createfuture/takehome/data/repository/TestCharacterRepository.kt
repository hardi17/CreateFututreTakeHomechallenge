package com.createfuture.takehome.data.repository

import app.cash.turbine.test
import com.createfuture.takehome.data.api.Service
import com.createfuture.takehome.data.models.ApiCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TestCharacterRepository {

    @Mock
    private lateinit var service: Service

    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        repository = CharacterRepository(service)
    }

    @Test
    fun getFetchCharacter_Respone_Success() {
        runTest {
            val characterList = listOf(
                ApiCharacter(
                    name = "Test Character1",
                    gender = "Unknown",
                    culture = "Test Culture",
                    born = "Test Born Info",
                    died = "",
                    aliases = listOf("Alias A", "Alias B"),
                    tvSeries = listOf("Season 1", "Season 2"),
                    playedBy = listOf("Actor One", "Actor Two")
                ),
                ApiCharacter(
                    name = "Test Character2",
                    gender = "Unknown",
                    culture = "Test Culture",
                    born = "Test Born Info",
                    died = "",
                    aliases = listOf("Alias A", "Alias B"),
                    tvSeries = listOf("Season 1", "Season 2"),
                    playedBy = listOf("Actor One", "Actor Two")
                )
            )

            doReturn(characterList).`when`(service).getCharacters()

            repository.fetchCharacters().test {
                assertEquals(characterList, awaitItem())
                cancelAndConsumeRemainingEvents()
            }

            verify(service, times(1)).getCharacters()
        }
    }


    @Test
    fun fetch_all_breed_failed() {
        runTest {
            val error = "JsonSyntaxException"

            doThrow(RuntimeException(error)).`when`(service).getCharacters()

            repository.fetchCharacters().test {
                assertEquals(error, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }

            verify(service, times(1)).getCharacters()

        }
    }
}