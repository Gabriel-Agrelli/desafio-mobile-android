package com.example.marveluniverse

import app.cash.turbine.test
import com.example.marveluniverse.data.model.Character
import com.example.marveluniverse.data.model.CharacterData
import com.example.marveluniverse.data.model.CharacterResponse
import com.example.marveluniverse.data.model.Thumbnail
import com.example.marveluniverse.data.repository.CharacterRepository
import com.example.marveluniverse.data.repository.NetworkResult
import com.example.marveluniverse.viewmodel.CharactersViewModel
import com.example.marveluniverse.viewmodel.CharactersViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class CharactersViewModelTest {
    private val repository: CharacterRepository = mockk(relaxed = true)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val viewModel by lazy {
        CharactersViewModel(repository)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Should emit event error when request failed`() = runTest {
        coEvery { repository.fetchCharacters(10) } returns flow {
            emit(NetworkResult.loading())
            emit(NetworkResult.error("Error", null))
        }

        runBlocking {
            viewModel.viewState.test {
                viewModel.getCharacters(10)

                assertEquals(awaitItem(), CharactersViewState.Default)
                assertEquals(awaitItem(), CharactersViewState.SowProgress)
                assertEquals(awaitItem(), CharactersViewState.OnError(null))
                assertEquals(awaitItem(), CharactersViewState.HideProgress)
                expectNoEvents()
            }
        }
    }

    @Test
    fun `Should list heroes when response is successful`() = runTest {
        val characters = listOf(
            Character(
                id = 1011334,
                name = "3-D Man",
                description = "",
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                ),
                null
            ),
            Character(
                id = 1017100,
                name = "A-Bomb (HAS)",
                description = "",
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
                    extension = "jpg"
                ),
                null
            ),
            Character(
                id = 1017100,
                name = "A-Bomb (HAS)",
                description = "",
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
                    extension = "jpg"
                ),
                null
            ),
            Character(
                id = 1017100,
                name = "A-Bomb (HAS)",
                description = "",
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
                    extension = "jpg"
                ),
                null
            ),
            Character(
                id = 1017100,
                name = "A-Bomb (HAS)",
                description = "",
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
                    extension = "jpg"
                ),
                null
            ),
            Character(
                id = 1017100,
                name = "A-Bomb (HAS)",
                description = "",
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
                    extension = "jpg"
                ),
                null
            )
        )

        val mockedResponse = CharacterResponse(
            code = 200,
            status = "",
            data = CharacterData(
                offset = 20,
                limit = 6,
                total = 6,
                count = 2,
                results = characters
            )
        )

        coEvery { repository.fetchCharacters(10) } returns flow {
            emit(NetworkResult.loading())
            emit(NetworkResult.success(mockedResponse))
        }

        runBlocking {
            viewModel.viewState.test {
                viewModel.getCharacters(10)

                assertEquals(awaitItem(), CharactersViewState.Default)
                assertEquals(awaitItem(), CharactersViewState.SowProgress)
                assertEquals(awaitItem(), CharactersViewState.LoadHighlightsCharacters(characters.take(5)))
                assertEquals(awaitItem(), CharactersViewState.LoadCharacters(characters.drop(5)))
                assertEquals(awaitItem(), CharactersViewState.HideProgress)
                expectNoEvents()
            }
        }
    }
}