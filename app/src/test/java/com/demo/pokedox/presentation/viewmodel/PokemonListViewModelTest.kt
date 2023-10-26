package com.demo.pokedox.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.mutableStateOf
import com.demo.pokedox.data.models.PokedexListEntry
import com.demo.pokedox.data.remote.responses.Animated
import com.demo.pokedox.data.remote.responses.BlackWhite
import com.demo.pokedox.data.remote.responses.Crystal
import com.demo.pokedox.data.remote.responses.DiamondPearl
import com.demo.pokedox.data.remote.responses.DreamWorld
import com.demo.pokedox.data.remote.responses.Emerald
import com.demo.pokedox.data.remote.responses.FireredLeafgreen
import com.demo.pokedox.data.remote.responses.GenerationI
import com.demo.pokedox.data.remote.responses.GenerationIi
import com.demo.pokedox.data.remote.responses.GenerationIii
import com.demo.pokedox.data.remote.responses.GenerationIv
import com.demo.pokedox.data.remote.responses.GenerationV
import com.demo.pokedox.data.remote.responses.GenerationVi
import com.demo.pokedox.data.remote.responses.GenerationVii
import com.demo.pokedox.data.remote.responses.GenerationViii
import com.demo.pokedox.data.remote.responses.Gold
import com.demo.pokedox.data.remote.responses.HeartgoldSoulsilver
import com.demo.pokedox.data.remote.responses.Home
import com.demo.pokedox.data.remote.responses.Icons
import com.demo.pokedox.data.remote.responses.OfficialArtwork
import com.demo.pokedox.data.remote.responses.OmegarubyAlphasapphire
import com.demo.pokedox.data.remote.responses.Other
import com.demo.pokedox.data.remote.responses.Platinum
import com.demo.pokedox.data.remote.responses.Pokemon
import com.demo.pokedox.data.remote.responses.PokemonList
import com.demo.pokedox.data.remote.responses.RedBlue
import com.demo.pokedox.data.remote.responses.Result
import com.demo.pokedox.data.remote.responses.RubySapphire
import com.demo.pokedox.data.remote.responses.Silver
import com.demo.pokedox.data.remote.responses.Species
import com.demo.pokedox.data.remote.responses.Sprites
import com.demo.pokedox.data.remote.responses.Type
import com.demo.pokedox.data.remote.responses.TypeX
import com.demo.pokedox.data.remote.responses.UltraSunUltraMoon
import com.demo.pokedox.data.remote.responses.Versions
import com.demo.pokedox.data.remote.responses.XY
import com.demo.pokedox.data.remote.responses.Yellow
import com.demo.pokedox.data.util.Constants
import com.demo.pokedox.data.util.Resource
import com.demo.pokedox.domain.usecases.GetPokemonInfoUseCase
import com.demo.pokedox.domain.usecases.GetPokemonListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
@ExperimentalCoroutinesApi

class PokemonListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var pokemonListViewModel: PokemonListViewModel
    private val getPokemonInfoUseCase = mockk<GetPokemonInfoUseCase>()
    private val getPokemonListUseCase = mockk<GetPokemonListUseCase>()

    private var isSearchStarting = true


    private val pokedexListEntry = PokedexListEntry(
        pokemonName = "Bulbasaur",
        imageUrl = "https: //img.pokemondb.net/artwork/large/bulbasaur.jpg",
        number = 1,
        typeList = listOf(
            Type(
                slot = 1,
                type = TypeX(
                    name = "grass",
                    url = "https: //pokeapi.co/api/v2/type/12/"
                )
            ),
            Type(
                slot = 2,
                type = TypeX(
                    name = "poison",
                    url = "https: //pokeapi.co/api/v2/type/4/"
                )
            )
        )
    )


    private val pokedexListEntry1 = PokedexListEntry(
        pokemonName = "Ivysaur",
        imageUrl = "https: //img.pokemondb.net/artwork/large/ivysaur.jpg",
        number = 2,
        typeList = listOf(
            Type(
                slot = 1,
                type = TypeX(
                    name = "grass",
                    url = "https: //pokeapi.co/api/v2/type/12/"
                )
            ),
            Type(
                slot = 2,
                type = TypeX(
                    name = "poison",
                    url = "https: //pokeapi.co/api/v2/type/4/"
                )
            )
        )
    )

    private val pokedexList = listOf<PokedexListEntry>(pokedexListEntry, pokedexListEntry1)

    private val pokemonsList = listOf<Result>(
        Result("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1"),
        Result("ivysaur", "https://pokeapi.co/api/v2/pokemon/2"),
    )
    private val pokemonListMain = PokemonList(
        1292,
        "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
        "null",
        pokemonsList
    )

    private val other = Other(DreamWorld("", ""), Home("", "", "", ""), OfficialArtwork(""))

    private var i =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/back/132.png"
    private val generationI = GenerationI(RedBlue(i, i, i, i, i, i), Yellow(i, i, i, i, i, i))
    private val generationIi =
        GenerationIi(Crystal(i, i, i, i, i, i, i, i), Gold(i, i, i, i, i), Silver(i, i, i, i, i))
    private val generationIii =
        GenerationIii(Emerald(i, i), FireredLeafgreen(i, i, i, i), RubySapphire(i, i, i, i))
    private val generationIv = GenerationIv(
        DiamondPearl(i, i, i, i, i, i, i, i), HeartgoldSoulsilver(i, i, i, i, i, i, i, i),
        Platinum(i, i, i, i, i, i, i, i)
    )
    private val generationV =
        GenerationV(BlackWhite(Animated(i, i, i, i, i, i, i, i), i, i, i, i, i, i, i, i))
    private val generationVi = GenerationVi(OmegarubyAlphasapphire(i, i, i, i), XY(i, i, i, i))
    private val generationVii = GenerationVii(Icons(i, i), UltraSunUltraMoon(i, i, i, i))
    private val generationViii = GenerationViii(Icons(i, i))
    private val versions = Versions(
        generationI,
        generationIi,
        generationIii,
        generationIv,
        generationV,
        generationVi,
        generationVii,
        generationViii
    )
    private val spirits = Sprites("", "", "", "", "", "", "", "", other, versions)
    private val pokemonBulbasaur = Pokemon(
        emptyList(),
        101,
        emptyList(),
        emptyList(),
        3,
        emptyList(),
        131,
        true,
        "https://pokeapi.co/api/v2/pokemon/132/encounters",
        emptyList(),
        "Bulbasaur",
        214,
        emptyList(),
        Species("bulbasaur", "https://pokeapi.co/api/v2/pokemon-species/132/"),
        spirits, emptyList(), emptyList(), 40
    )
    private val pokemonIvasaur = Pokemon(
        emptyList(),
        101,
        emptyList(),
        emptyList(),
        3,
        emptyList(),
        132,
        true,
        "https://pokeapi.co/api/v2/pokemon/132/encounters",
        emptyList(),
        "Ivysaur",
        214,
        emptyList(),
        Species("ivysaur", "https://pokeapi.co/api/v2/pokemon-species/132/"),
        spirits, emptyList(), emptyList(), 41
    )

    @Before
    fun setUp() {
        pokemonListViewModel =
            PokemonListViewModel(getPokemonListUseCase, getPokemonInfoUseCase)
    }

    @Test
    fun `when function loadPokemonPaginated is running and returns loading then loading state is true `() =
        runTest {
            coEvery { getPokemonListUseCase(2, 0) } returns Resource.Loading()

            pokemonListViewModel.loadPokemonPaginated()
            dispatcher.scheduler.advanceUntilIdle()
            assert(pokemonListViewModel.isLoading.value)
        }


    @Test
    fun `when function loadPokemonPaginated is running and returns success then loading state is false `() =
        runTest {
            coEvery { getPokemonListUseCase(Constants.PAGE_SIZE, 0) } returns Resource.Success(
                pokemonListMain
            )
            coEvery { getPokemonListUseCase(Constants.PAGE_SIZE, 20) } returns Resource.Success(
                pokemonListMain
            )
            coEvery { getPokemonInfoUseCase("bulbasaur") } returns Resource.Success(pokemonBulbasaur)
            coEvery { getPokemonInfoUseCase("ivysaur") } returns Resource.Success(pokemonIvasaur)

            pokemonListViewModel.loadPokemonPaginated()
            dispatcher.scheduler.advanceUntilIdle()
            assert(!pokemonListViewModel.isLoading.value)
        }

    @Test
    fun `when function loadPokemonPaginated  returns success then pokemon list state value is set correctly `() =
        runTest {
            coEvery { getPokemonListUseCase(Constants.PAGE_SIZE, 0) } returns Resource.Success(
                pokemonListMain
            )
            coEvery { getPokemonListUseCase(Constants.PAGE_SIZE, 20) } returns Resource.Success(
                pokemonListMain
            )
            coEvery { getPokemonInfoUseCase("bulbasaur") } returns Resource.Success(pokemonBulbasaur)
            coEvery { getPokemonInfoUseCase("ivysaur") } returns Resource.Success(pokemonIvasaur)

            pokemonListViewModel.loadPokemonPaginated()
            dispatcher.scheduler.advanceUntilIdle()
            assert(pokemonListViewModel.pokemonList.value[0].pokemonName == "Bulbasaur")
            assert(pokemonListViewModel.pokemonList.value[1].pokemonName == "Ivysaur")
            assert(pokemonListViewModel.pokemonList.value[0].imageUrl == "https://img.pokemondb.net/artwork/large/bulbasaur.jpg")

        }

    @Test
    fun `when function loadPokemonPaginated  returns error then isloading state is false and loaderror stores the error message `() =
        runTest {
            coEvery {
                getPokemonListUseCase(
                    Constants.PAGE_SIZE,
                    0
                )
            } returns Resource.Error("An error occurred !")

            pokemonListViewModel.loadPokemonPaginated()
            dispatcher.scheduler.advanceUntilIdle()
            assert(pokemonListViewModel.loadError.value == "An error occurred !")
            assert(!pokemonListViewModel.isLoading.value)

        }


    @Test
    fun `when search pokemon List is called with query then it sets the pokemon list with filtered value`() =
        runTest {
            var query = "bul"
            isSearchStarting = true
            pokemonListViewModel.pokemonList.value = pokedexList


            pokemonListViewModel.searchPokemonList(query)
            dispatcher.scheduler.advanceUntilIdle()

            assert(pokemonListViewModel.pokemonList.value.size == 1)
            assert(pokemonListViewModel.pokemonList.value[0].pokemonName == "Bulbasaur")

        }

    @Test
    fun `when search pokemon List is called with query that doesn't match then it returns empty list`() =
        runTest {
            var query = "pikachu"
            isSearchStarting = true
            pokemonListViewModel.pokemonList.value = pokedexList


            pokemonListViewModel.searchPokemonList(query)
            dispatcher.scheduler.advanceUntilIdle()

            assert(pokemonListViewModel.pokemonList.value.isEmpty())

        }

}