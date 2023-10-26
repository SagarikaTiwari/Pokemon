package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.PokemonGender
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import javax.inject.Inject

class GetPokemonGenderUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Resource<PokemonGender> {
        return pokemonRepository.getPokemonGender()
    }
}