package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.Chain
import com.demo.pokedox.data.remote.responses.EvolvesTo
import com.demo.pokedox.data.remote.responses.EvolvesToX
import com.demo.pokedox.data.remote.responses.PokemonEvolutionChain
import com.demo.pokedox.data.remote.responses.SpeciesXXX
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetPokemonEvolutionChainUseCaseTest {
    lateinit var getPokemonEvolutionChainUseCase : GetPokemonEvolutionChainUseCase
    private val pokemonRepository = mockk<PokemonRepository>()
    private val evolvesToX = EvolvesToX(
        emptyList(),
        false,
        SpeciesXXX("charizard", "https://pokeapi.co/api/v2/pokemon-species/6/")
    )
    private val evolvesTo = EvolvesTo(
        emptyList(), listOf(evolvesToX), false,
        SpeciesXXX("charmeleon", "https://pokeapi.co/api/v2/pokemon-species/5/")
    )
    private val chain = Chain(
        emptyList(),
        listOf(evolvesTo),
        false,
        SpeciesXXX("charmander", "https://pokeapi.co/api/v2/pokemon-species/4/")
    )
    private val evolutionChain = PokemonEvolutionChain("", chain, 2)
    @Before
    fun setUp() {
        getPokemonEvolutionChainUseCase = GetPokemonEvolutionChainUseCase(pokemonRepository)
    }

    @Test
    fun `when get pokemon evolution chain is invoked then Pokemon Evolution class gets populates correctly`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonEvolutionChain("2")
            } returns Resource.Success(evolutionChain)
            getPokemonEvolutionChainUseCase("2")
            coVerify {
                pokemonRepository.getPokemonEvolutionChain("2")
            }
        }

    @Test
    fun `when get pokemon description api fails then response also returns error message`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonEvolutionChain("2")
            } returns Resource.Error("An error occurred")
            getPokemonEvolutionChainUseCase("2")
            coVerify {
                pokemonRepository.getPokemonEvolutionChain("2")
            }
        }
}