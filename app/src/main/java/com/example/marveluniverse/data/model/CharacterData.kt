package com.example.marveluniverse.data.model

import com.example.marveluniverse.data.model.Character

data class CharacterData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    var results: List<Character>
)