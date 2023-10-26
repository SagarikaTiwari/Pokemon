package com.demo.pokedox.domain.usecases

import com.demo.pokedox.data.remote.responses.EvolutionChainX
import com.demo.pokedox.data.remote.responses.FlavorTextEntryX
import com.demo.pokedox.data.remote.responses.LanguageX
import com.demo.pokedox.data.remote.responses.PokemonDescription
import com.demo.pokedox.data.remote.responses.VersionXX
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetPokemonDescriptionUseCaseTest {
    private lateinit var getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase
    private val pokemonRepository = mockk<PokemonRepository>()
    private val flavorTextEntry = FlavorTextEntryX(
        "\"れきだいの　おうが　つれていた。 れいりょくで　ひとや　ポケモンの こころを　あやつり　したがわせる。\"\n", LanguageX(
            "ja-Hrkt", "https://pokeapi.co/api/v2/language/1/"
        ), VersionXX(
            "x", "https://pokeapi.co/api/v2/version/23/"
        )
    )
    private val pokemonDescription = PokemonDescription(
        EvolutionChainX("https://pokeapi.co/api/v2/evolution-chain/349"),
        listOf(flavorTextEntry, flavorTextEntry, flavorTextEntry), "aegislash", 682
    )

    @Before
    fun setUp() {
        getPokemonDescriptionUseCase = GetPokemonDescriptionUseCase(pokemonRepository)
    }

    @Test
    fun `when get pokemon description is invoked then Pokemon Description class gets populates correctly`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonDescription("aegislash")
            } returns Resource.Success(pokemonDescription)
            getPokemonDescriptionUseCase("aegislash")
            coVerify {
                pokemonRepository.getPokemonDescription("aegislash")
            }
        }

    @Test
    fun `when get pokemon description api fails then response also returns error message`() =
        runTest {
            coEvery {
                pokemonRepository.getPokemonDescription("abcd")
            } returns Resource.Error("An error occurred")
            getPokemonDescriptionUseCase("abcd")
            coVerify {
                pokemonRepository.getPokemonDescription("abcd")
            }
        }

}