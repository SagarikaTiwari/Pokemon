package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.PokemonEvolutionChain
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import javax.inject.Inject

class GetPokemonEvolutionChainUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(number : String): Resource<PokemonEvolutionChain> {
        return pokemonRepository.getPokemonEvolutionChain(number)
    }

}