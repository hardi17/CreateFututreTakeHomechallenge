package com.createfuture.takehome.ui

import app.cash.turbine.test
import com.createfuture.takehome.data.models.ApiCharacter
import com.createfuture.takehome.data.repository.CharacterRepository
import com.createfuture.takehome.ui.home.CharacterViewModel
import com.createfuture.takehome.utils.DispatcherProvider
import com.createfuture.takehome.utils.TestDispatcherProvider
import com.createfuture.takehome.utils.Uistate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TestCharacterViewModel {

    @Mock
    private lateinit var repository: CharacterRepository

    private lateinit var dispatcher: DispatcherProvider

    @Before
    fun setUp() {
        dispatcher = TestDispatcherProvider()
    }

    @Test
    fun getCharacterList_SuccessFully() {
        runTest {
            val characterList = listOf(
                ApiCharacter(
                    name = "abc",
                    gender = "Unknown",
                    culture = "Test Culture",
                    born = "Test Born Info",
                    died = "",
                    aliases = listOf("Alias A", "Alias B"),
                    tvSeries = listOf("Season 1", "Season 2"),
                    playedBy = listOf("Actor One", "Actor Two")
                ),
                ApiCharacter(
                    name = "xyz",
                    gender = "Unknown",
                    culture = "Test Culture",
                    born = "Test Born Info",
                    died = "",
                    aliases = listOf("Alias A", "Alias B"),
                    tvSeries = listOf("Season 1", "Season 2"),
                    playedBy = listOf("Actor One", "Actor Two")
                )
            )

            val filterList = listOf(
                ApiCharacter(
                    name = "xyz",
                    gender = "Unknown",
                    culture = "Test Culture",
                    born = "Test Born Info",
                    died = "",
                    aliases = listOf("Alias A", "Alias B"),
                    tvSeries = listOf("Season 1", "Season 2"),
                    playedBy = listOf("Actor One", "Actor Two")
                )
            )

            doReturn(flowOf(characterList)).`when`(repository).fetchCharacters()

            val viewModel = CharacterViewModel(repository, dispatcher)

            viewModel.loadCharacters()

            viewModel.uiState.test {
                assertEquals(Uistate.Success(characterList), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.filterCharacterListOnSearch("x")

            viewModel.filterCharacterList.test {
                assertEquals(filterList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun fetch_all_breed_failed() {
        runTest {
            val errorMessage = "Unable to fetch characters"

            doReturn(flow<List<ApiCharacter>> {
                throw Exception(errorMessage)
            }).`when`(repository).fetchCharacters()


            val viewModel = CharacterViewModel(repository, dispatcher)

            viewModel.loadCharacters()
            viewModel.uiState.test {
                assertEquals(Uistate.Error(errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}