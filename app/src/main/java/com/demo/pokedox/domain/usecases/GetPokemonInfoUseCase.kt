package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.Pokemon
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(pokemonName: String): Resource<Pokemon> {
        return pokemonRepository.getPokemonInfo(pokemonName)
    }
}