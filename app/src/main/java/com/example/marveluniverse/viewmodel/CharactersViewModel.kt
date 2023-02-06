package com.example.marveluniverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marveluniverse.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.marveluniverse.data.model.Character
import com.example.marveluniverse.data.model.CharacterData
import com.example.marveluniverse.data.repository.NetworkResult
import com.example.marveluniverse.viewmodel.CharactersViewState.*

class CharactersViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _viewState: MutableStateFlow<CharactersViewState> = MutableStateFlow(Default)
    val viewState: StateFlow<CharactersViewState> = _viewState

    private var highlightsCharacters: List<Character> = emptyList()
    private var characters: MutableList<Character> = mutableListOf()

    fun getCharacters(offset: Int) {
        viewModelScope.launch {
            repository.fetchCharacters(offset).collect {
                when (it?.status) {
                    NetworkResult.Status.LOADING -> _viewState.emit(SowProgress)
                    NetworkResult.Status.SUCCESS -> {
                        if (it.data == null) {
                            _viewState.emit(OnError(null))
                        } else {
                            handleResponseSuccess(it.data.data)
                        }
                    }
                    NetworkResult.Status.ERROR -> _viewState.emit(OnError(it.error?.message))
                    else -> _viewState.emit(OnError(null))
                }
            }
            _viewState.emit(HideProgress)
        }
    }

    private suspend fun handleResponseSuccess(data: CharacterData) {
        if (highlightsCharacters.isEmpty()) {
            highlightsCharacters = data.results.take(TOTAL_HIGHLIGHT_CHARACTERS)
            characters.addAll(data.results.drop(TOTAL_HIGHLIGHT_CHARACTERS))

            _viewState.emit(LoadHighlightsCharacters(highlightsCharacters))
        } else {
            characters.addAll(data.results)
        }
        _viewState.emit(LoadCharacters(characters))
    }

    companion object {
        private const val TOTAL_HIGHLIGHT_CHARACTERS = 5
    }
}