package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.DamageRelations
import com.demo.pokedox.data.remote.responses.DoubleDamageFrom
import com.demo.pokedox.data.remote.responses.DoubleDamageTo
import com.demo.pokedox.data.remote.responses.PokemonType
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetPokemonTypeUseCaseTest {
    lateinit var getPokemonTypeUseCase: GetPokemonTypeUseCase
    private val pokemonRepository = mockk<PokemonRepository>()
    private val damageRelations = DamageRelations(
        listOf(
            DoubleDamageFrom("rock", ""),
            DoubleDamageFrom("electric", ""),
            DoubleDamageFrom("flying", "")
        ), listOf(
            DoubleDamageTo("fighting", ""), DoubleDamageTo("bug", ""), DoubleDamageTo("grass", "")
        ), emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )
    private val pokemonType = PokemonType(damageRelations, 3, "flying")
    @Before
    fun setUp() {
        getPokemonTypeUseCase = GetPokemonTypeUseCase(pokemonRepository)
    }


    @Test
    fun `when get pokemon type is invoked then Pokemon Type class gets populates correctly`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonTypeForWeaknessandability("3")
            } returns Resource.Success(pokemonType)
            getPokemonTypeUseCase("3")
            coVerify {
                pokemonRepository.getPokemonTypeForWeaknessandability("3")
            }
        }

    @Test
    fun `when get pokemon description api fails then response also returns error message`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonTypeForWeaknessandability("3")
            } returns Resource.Error("An error occurred")
            getPokemonTypeUseCase("3")
            coVerify {
                pokemonRepository.getPokemonTypeForWeaknessandability("3")
            }
        }

}