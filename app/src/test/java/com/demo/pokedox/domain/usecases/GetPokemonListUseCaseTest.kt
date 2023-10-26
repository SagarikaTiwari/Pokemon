package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.PokemonList
import com.demo.pokedox.data.remote.responses.Result
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetPokemonListUseCaseTest {
    lateinit var getPokemonListUseCase: GetPokemonListUseCase
    private val pokemonRepository = mockk<PokemonRepository>()
    private val pokemonList = listOf<Result>(
        Result("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1"),
        Result("ivysaur", "https://pokeapi.co/api/v2/pokemon/2"),
        Result("venusaur", "https://pokeapi.co/api/v2/pokemon/3"),
        Result("charmander", "https://pokeapi.co/api/v2/pokemon/4"),
        Result("charmeleon", "https://pokeapi.co/api/v2/pokemon/5"),
    )
    private val pokemonListMain = PokemonList(
        1292,
        "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
        "null",
        pokemonList
    )
    @Before
    fun setUp() {
        getPokemonListUseCase = GetPokemonListUseCase(pokemonRepository)
    }

    @Test
    fun `when get pokemon list is invoked then Pokemon List class gets populates correctly`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonList(5,0)
            } returns Resource.Success(pokemonListMain)
            getPokemonListUseCase(5,0)
            coVerify {
                pokemonRepository.getPokemonList(5,0)
            }
        }

    @Test
    fun `when get pokemon list api fails then response also returns error message`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonList(6,0)
            } returns Resource.Error("An error occurred")
            getPokemonListUseCase(6,0)
            coVerify {
                pokemonRepository.getPokemonList(6,0)
            }
        }
}