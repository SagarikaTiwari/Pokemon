package com.demo.pokedox.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import com.demo.pokedox.data.remote.responses.RedBlue
import com.demo.pokedox.data.remote.responses.RubySapphire
import com.demo.pokedox.data.remote.responses.Silver
import com.demo.pokedox.data.remote.responses.Species
import com.demo.pokedox.data.remote.responses.Sprites
import com.demo.pokedox.data.remote.responses.UltraSunUltraMoon
import com.demo.pokedox.data.remote.responses.Versions
import com.demo.pokedox.data.remote.responses.XY
import com.demo.pokedox.data.remote.responses.Yellow
import com.demo.pokedox.data.util.Resource
import com.demo.pokedox.domain.usecases.GetPokemonDescriptionUseCase
import com.demo.pokedox.domain.usecases.GetPokemonEvolutionChainUseCase
import com.demo.pokedox.domain.usecases.GetPokemonGenderUseCase
import com.demo.pokedox.domain.usecases.GetPokemonInfoUseCase
import com.demo.pokedox.domain.usecases.GetPokemonTypeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PokemonDetailViewModelTest {
    private val getPokemonInfoUseCase = mockk<GetPokemonInfoUseCase>()
    private val getPokemonTypeUseCase = mockk<GetPokemonTypeUseCase>()
    private val getPokemonEvolutionChainUseCase = mockk<GetPokemonEvolutionChainUseCase>()
    private val getPokemonDescriptionUseCase = mockk<GetPokemonDescriptionUseCase>()
    private val getPokemonGenderUseCase = mockk<GetPokemonGenderUseCase>()

    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

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
    private val pokemon = Pokemon(
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
        "ditto",
        214,
        emptyList(),
        Species("ditto", "https://pokeapi.co/api/v2/pokemon-species/132/"),
        spirits, emptyList(), emptyList(), 40
    )


    @Before
    fun setUp() {
        pokemonDetailViewModel = PokemonDetailViewModel(
            getPokemonInfoUseCase,
            getPokemonTypeUseCase,
            getPokemonEvolutionChainUseCase,
            getPokemonDescriptionUseCase,
            getPokemonGenderUseCase
        )
    }

    @Test
    fun `when pokemon Info api returns correctly then show success`() = runTest {
        coEvery { getPokemonInfoUseCase("ditto") } returns Resource.Success(pokemon)
        val response = pokemonDetailViewModel.getPokemonInfo("ditto")
        assert(response.data?.name == "ditto")
        assert(response.data?.id == 132)
        assert(response.data?.sprites?.versions?.generationi?.redblue?.back_default == "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/back/132.png")

    }
    @Test
    fun `when pokemon Info api fails then  show error message`() = runTest {
        coEvery { getPokemonInfoUseCase("ditto") } returns Resource.Error("An error occurred")
        val response = pokemonDetailViewModel.getPokemonInfo("ditto")
        assert(response.message=="An error occurred")
    }
}