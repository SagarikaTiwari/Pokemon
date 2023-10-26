package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.PokemonList
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): Resource<PokemonList> {
        return pokemonRepository.getPokemonList(limit, offset)
    }
}