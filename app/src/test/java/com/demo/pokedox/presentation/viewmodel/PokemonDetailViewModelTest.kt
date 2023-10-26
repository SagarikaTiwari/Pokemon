package com.demo.pokedox.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.pokedox.data.remote.responses.Animated
import com.demo.pokedox.data.remote.responses.BlackWhite
import com.demo.pokedox.data.remote.responses.Chain
import com.demo.pokedox.data.remote.responses.Crystal
import com.demo.pokedox.data.remote.responses.DamageRelations
import com.demo.pokedox.data.remote.responses.DiamondPearl
import com.demo.pokedox.data.remote.responses.DoubleDamageFrom
import com.demo.pokedox.data.remote.responses.DoubleDamageTo
import com.demo.pokedox.data.remote.responses.DreamWorld
import com.demo.pokedox.data.remote.responses.Emerald
import com.demo.pokedox.data.remote.responses.EvolutionChainX
import com.demo.pokedox.data.remote.responses.EvolvesTo
import com.demo.pokedox.data.remote.responses.EvolvesToX
import com.demo.pokedox.data.remote.responses.FireredLeafgreen
import com.demo.pokedox.data.remote.responses.FlavorTextEntryX
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
import com.demo.pokedox.data.remote.responses.LanguageX
import com.demo.pokedox.data.remote.responses.OfficialArtwork
import com.demo.pokedox.data.remote.responses.OmegarubyAlphasapphire
import com.demo.pokedox.data.remote.responses.Other
import com.demo.pokedox.data.remote.responses.Platinum
import com.demo.pokedox.data.remote.responses.Pokemon
import com.demo.pokedox.data.remote.responses.PokemonDescription
import com.demo.pokedox.data.remote.responses.PokemonEvolutionChain
import com.demo.pokedox.data.remote.responses.PokemonGender
import com.demo.pokedox.data.remote.responses.PokemonType
import com.demo.pokedox.data.remote.responses.RedBlue
import com.demo.pokedox.data.remote.responses.ResultX
import com.demo.pokedox.data.remote.responses.RubySapphire
import com.demo.pokedox.data.remote.responses.Silver
import com.demo.pokedox.data.remote.responses.Species
import com.demo.pokedox.data.remote.responses.SpeciesXXX
import com.demo.pokedox.data.remote.responses.Sprites
import com.demo.pokedox.data.remote.responses.UltraSunUltraMoon
import com.demo.pokedox.data.remote.responses.VersionXX
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
import io.mockk.coVerify
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

    private val pokemonGender = PokemonGender(
        3,
        "",
        "",
        listOf(ResultX("male", ""), ResultX("female", ""), ResultX("genderless", ""))
    )
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
        pokemonDetailViewModel = PokemonDetailViewModel(
            getPokemonInfoUseCase,
            getPokemonTypeUseCase,
            getPokemonEvolutionChainUseCase,
            getPokemonDescriptionUseCase,
            getPokemonGenderUseCase
        )
    }

    @Test
    fun `when pokemon Info function is call then it returns correctly then show success`() =
        runTest {
            coEvery { getPokemonInfoUseCase("ditto") } returns Resource.Success(pokemon)
            val response = pokemonDetailViewModel.getPokemonInfo("ditto")
            assert(response is Resource.Success)
            assert(response.data?.name == "ditto")
            assert(response.data?.id == 132)
            assert(response.data?.sprites?.versions?.generationi?.redblue?.back_default == "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/back/132.png")

        }

    @Test
    fun `when pokemon Info function fails then  show error message`() = runTest {
        coEvery { getPokemonInfoUseCase("ditto") } returns Resource.Error("An error occurred")
        val response = pokemonDetailViewModel.getPokemonInfo("ditto")
        assert(response.message == "An error occurred")
    }

    @Test
    fun `when get pokemon Type function is called then returns correct response in success`() =
        runTest {
            coEvery { getPokemonTypeUseCase("3") } returns Resource.Success(pokemonType)

            val response = pokemonDetailViewModel.getPokemonType("3")
            assert(response is Resource.Success)
            assert(response.data?.damage_relations?.double_damage_to?.get(0)?.name == "fighting")
            assert(response.data?.damage_relations?.double_damage_from?.get(0)?.name == "rock")
            assert(response.data?.name == "flying")
        }

    @Test
    fun `when get pokemon type function fails then view model returns error message`() =
        runTest {
            coEvery {
                getPokemonTypeUseCase("3")
            } returns Resource.Error("An error occurred")
            val response = pokemonDetailViewModel.getPokemonType("3")
            assert(response is Resource.Error)
            assert(response.message == "An error occurred")
        }


    @Test
    fun `when get pokemon evolution chain function then view model returns success with correct content`() =
        runTest {
            coEvery {
                getPokemonEvolutionChainUseCase("2")
            } returns Resource.Success(evolutionChain)

            val response = pokemonDetailViewModel.getPokemonEvolutionChain("2")
            assert(response is Resource.Success)
            assert(response?.data?.chain?.species?.name == "charmander")
            assert(response?.data?.chain?.evolves_to?.get(0)?.species?.name == "charmeleon")
            assert(response?.data?.chain?.evolves_to?.get(0)?.evolves_to?.get(0)?.species?.name == "charizard")
        }

    @Test
    fun `when get pokemon evolution function fails then response also returns error message`() =
        runTest {
            coEvery {
                getPokemonEvolutionChainUseCase("2")
            } returns Resource.Error("An error occurred")
            var response = pokemonDetailViewModel.getPokemonEvolutionChain("2")
            assert(response is Resource.Error)
            assert(response.message == "An error occurred")
        }

    @Test
    fun `when get pokemon gender function is called  then view model returns success with correct response`() =
        runTest {
            coEvery {
                getPokemonGenderUseCase()
            } returns Resource.Success(pokemonGender)
            var response = pokemonDetailViewModel.getPokemonGender()
            assert(response is Resource.Success)
            assert(response.data?.results?.get(0)?.name == "male")
            assert(response.data?.results?.get(1)?.name == "female")
            assert(response.data?.results?.get(2)?.name == "genderless")
        }

    @Test
    fun `when get pokemon gender function fails then response also returns error message`() =
        runTest {
            coEvery { getPokemonGenderUseCase() } returns Resource.Error("An error occurred !")

            var response = pokemonDetailViewModel.getPokemonGender()

            assert(response is Resource.Error)
            assert(response.message == "An error occurred !")
        }


    @Test
    fun `when get pokemon description function is callen then it returns response as success with correct result`() =
        runTest {
            coEvery {
                getPokemonDescriptionUseCase("aegislash")
             } returns Resource.Success(pokemonDescription)
            var response = pokemonDetailViewModel.getPokemonDescription("aegislash")
            assert(response is Resource.Success)
            assert(response?.data?.order == 682)
            assert(response?.data?.flavor_text_entries?.get(0)?.version?.url == "https://pokeapi.co/api/v2/version/23/")

        }

    @Test
    fun `when get pokemon description api fails then response also returns error message`() =
        runTest {
            coEvery {
                getPokemonDescriptionUseCase("aegislash")
            } returns Resource.Error("An error occurred")
            var response = pokemonDetailViewModel.getPokemonDescription("aegislash")
            assert(response is Resource.Error)
            assert(response.message == "An error occurred")
        }


}
