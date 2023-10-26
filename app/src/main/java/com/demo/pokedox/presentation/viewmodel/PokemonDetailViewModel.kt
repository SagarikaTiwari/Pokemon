package com.demo.pokedox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.pokedox.data.remote.responses.*
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import com.demo.pokedox.domain.usecases.GetPokemonDescriptionUseCase
import com.demo.pokedox.domain.usecases.GetPokemonEvolutionChainUseCase
import com.demo.pokedox.domain.usecases.GetPokemonGenderUseCase
import com.demo.pokedox.domain.usecases.GetPokemonInfoUseCase
import com.demo.pokedox.domain.usecases.GetPokemonTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
    private val getPokemonTypeUseCase: GetPokemonTypeUseCase,
    private val getPokemonEvolutionChainUseCase: GetPokemonEvolutionChainUseCase,
    private val getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase,
    private val getPokemonGenderUseCase: GetPokemonGenderUseCase
) : ViewModel() {


    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return getPokemonInfoUseCase(pokemonName)

    }

    suspend fun getPokemonType(number: String): Resource<PokemonType> {
        return getPokemonTypeUseCase(number)
    }

    suspend fun getPokemonEvolutionChain(number: String): Resource<PokemonEvolutionChain> {
        return getPokemonEvolutionChainUseCase(number)
    }

    suspend fun getPokemonGender(): Resource<PokemonGender> {
        return getPokemonGenderUseCase()
    }

    suspend fun getPokemonDescription(number: String): Resource<PokemonDescription> {
        return getPokemonDescriptionUseCase(number)
    }
}