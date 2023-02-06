package com.example.marveluniverse.viewmodel

import com.example.marveluniverse.data.model.Character

sealed class CharactersViewState {

    object Default : CharactersViewState()

    object SowProgress : CharactersViewState()

    object HideProgress : CharactersViewState()

    data class LoadCharacters(val characters: List<Character>) : CharactersViewState()

    data class LoadHighlightsCharacters(val characters: List<Character>) : CharactersViewState()

    data class OnError(val error: String?) : CharactersViewState()
}