package com.demo.pokedox.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.pokedox.data.remote.PokeApi
import com.demo.pokedox.repository.PokemonRepository
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
 class PokemonListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private lateinit var pokemonRepository: PokemonRepository
    private lateinit var pokemonListViewModel: PokemonListViewModel


    @Before
    fun setUp() {
        //pokemonRepository = mockk()
        //pokemonListViewModel = mockk()
    }



    @Test
    fun searchPokemonListSucess() {
        runBlocking {
            pokemonListViewModel.searchPokemonList("bulbasur")
            verify { }
        }
    }


    @Test
    fun testpokemonLoadingFromForFirstTime() {


        var result =
            "{\"count\":1154,\"next\":\"https://pokeapi.co/api/v2/pokemon?offset=20&limit=20\",\"previous\":null,\"results\":[{\"name\":\"bulbasaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"},{\"name\":\"ivysaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/2/\"},{\"name\":\"venusaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/3/\"},{\"name\":\"charmander\",\"url\":\"https://pokeapi.co/api/v2/pokemon/4/\"},{\"name\":\"charmeleon\",\"url\":\"https://pokeapi.co/api/v2/pokemon/5/\"},{\"name\":\"charizard\",\"url\":\"https://pokeapi.co/api/v2/pokemon/6/\"},{\"name\":\"squirtle\",\"url\":\"https://pokeapi.co/api/v2/pokemon/7/\"},{\"name\":\"wartortle\",\"url\":\"https://pokeapi.co/api/v2/pokemon/8/\"},{\"name\":\"blastoise\",\"url\":\"https://pokeapi.co/api/v2/pokemon/9/\"},{\"name\":\"caterpie\",\"url\":\"https://pokeapi.co/api/v2/pokemon/10/\"},{\"name\":\"metapod\",\"url\":\"https://pokeapi.co/api/v2/pokemon/11/\"},{\"name\":\"butterfree\",\"url\":\"https://pokeapi.co/api/v2/pokemon/12/\"},{\"name\":\"weedle\",\"url\":\"https://pokeapi.co/api/v2/pokemon/13/\"},{\"name\":\"kakuna\",\"url\":\"https://pokeapi.co/api/v2/pokemon/14/\"},{\"name\":\"beedrill\",\"url\":\"https://pokeapi.co/api/v2/pokemon/15/\"},{\"name\":\"pidgey\",\"url\":\"https://pokeapi.co/api/v2/pokemon/16/\"},{\"name\":\"pidgeotto\",\"url\":\"https://pokeapi.co/api/v2/pokemon/17/\"},{\"name\":\"pidgeot\",\"url\":\"https://pokeapi.co/api/v2/pokemon/18/\"},{\"name\":\"rattata\",\"url\":\"https://pokeapi.co/api/v2/pokemon/19/\"},{\"name\":\"raticate\",\"url\":\"https://pokeapi.co/api/v2/pokemon/20/\"}]}"
        runBlocking {

            var response = pokemonRepository.getPokemonList(20, 0)
            Assert.assertEquals(response, response)
        }
    }


}