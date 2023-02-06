package com.example.marveluniverse.di

import com.example.marveluniverse.data.api.MarvelAPI
import com.example.marveluniverse.data.api.buildRetrofit
import com.example.marveluniverse.data.repository.CharacterRepository
import com.example.marveluniverse.viewmodel.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single { buildRetrofit(`class` = MarvelAPI::class.java) }

    factory { CharacterRepository(get()) }

    viewModel {
        CharactersViewModel(get())
    }
}