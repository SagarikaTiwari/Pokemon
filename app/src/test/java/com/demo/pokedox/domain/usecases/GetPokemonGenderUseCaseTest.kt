package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.PokemonGender
import com.demo.pokedox.data.remote.responses.ResultX
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetPokemonGenderUseCaseTest {
    lateinit var getPokemonGenderUseCase: GetPokemonGenderUseCase
    private val pokemonRepository = mockk<PokemonRepository>()
    private val pokemonGender = PokemonGender(
        3,
        "",
        "",
        listOf(ResultX("male", ""), ResultX("female", ""), ResultX("genderless", ""))
    )

    @Before
    fun setUp() {
        getPokemonGenderUseCase = GetPokemonGenderUseCase(pokemonRepository)
    }

    @Test
    fun `when get pokemon gender is invoked then Pokemon Gender class gets populates correctly`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonGender()
            } returns Resource.Success(pokemonGender)
            getPokemonGenderUseCase()
            coVerify {
                pokemonRepository.getPokemonGender()
            }
        }

    @Test
    fun `when get pokemon gender api fails then response also returns error message`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonGender()
            } returns Resource.Error("An error occurred")
            getPokemonGenderUseCase()
            coVerify {
                pokemonRepository.getPokemonGender()
            }
        }
}