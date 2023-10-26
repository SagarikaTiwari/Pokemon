package com.demo.pokedox.data.repository

import com.demo.pokedox.data.remote.PokeApi
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
import com.demo.pokedox.data.remote.responses.FlavorTextEntry
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
import com.demo.pokedox.data.remote.responses.HalfDamageFrom
import com.demo.pokedox.data.remote.responses.HeartgoldSoulsilver
import com.demo.pokedox.data.remote.responses.Home
import com.demo.pokedox.data.remote.responses.Icons
import com.demo.pokedox.data.remote.responses.Language
import com.demo.pokedox.data.remote.responses.LanguageX
import com.demo.pokedox.data.remote.responses.OfficialArtwork
import com.demo.pokedox.data.remote.responses.OmegarubyAlphasapphire
import com.demo.pokedox.data.remote.responses.Other
import com.demo.pokedox.data.remote.responses.Platinum
import com.demo.pokedox.data.remote.responses.Pokemon
import com.demo.pokedox.data.remote.responses.PokemonDescription
import com.demo.pokedox.data.remote.responses.PokemonEvolutionChain
import com.demo.pokedox.data.remote.responses.PokemonGender
import com.demo.pokedox.data.remote.responses.PokemonList
import com.demo.pokedox.data.remote.responses.PokemonType
import com.demo.pokedox.data.remote.responses.RedBlue
import com.demo.pokedox.data.remote.responses.Result
import com.demo.pokedox.data.remote.responses.ResultX
import com.demo.pokedox.data.remote.responses.RubySapphire
import com.demo.pokedox.data.remote.responses.Silver
import com.demo.pokedox.data.remote.responses.Species
import com.demo.pokedox.data.remote.responses.SpeciesXXX
import com.demo.pokedox.data.remote.responses.Sprites
import com.demo.pokedox.data.remote.responses.UltraSunUltraMoon
import com.demo.pokedox.data.remote.responses.Version
import com.demo.pokedox.data.remote.responses.VersionX
import com.demo.pokedox.data.remote.responses.VersionXX
import com.demo.pokedox.data.remote.responses.Versions
import com.demo.pokedox.data.remote.responses.XY
import com.demo.pokedox.data.remote.responses.Yellow
import com.demo.pokedox.data.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class PokemonRepositoryTest {

    private lateinit var pokemonRepository: PokemonRepository
    private val api = mockk<PokeApi>()

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

    @Before
    fun setUp() {
        pokemonRepository = PokemonRepository(api)
    }

    @Test
    fun `when get pokemon List is called with limit and offset then it returns pokemon list with correct size and values`() =
        runTest {
            coEvery {
                api.getPokemonList(5, 1)
            } returns pokemonListMain

            val response = api.getPokemonList(5, 1)
            assert(response.count == 1292)

            if (response != null) {
                assert(response.results[0].name == "bulbasaur")
                assert(response.results[1].name == "ivysaur")
                assert(response.results[2].url == "https://pokeapi.co/api/v2/pokemon/3")
                assert(response.results[3].url == "https://pokeapi.co/api/v2/pokemon/4")

            }
        }

    @Test
    fun `when get pokemon info is called then it returns pokemon entity as response`() = runTest {
        coEvery {
            api.getPokemonInfo("ditto")
        } returns pokemon

        val response = api.getPokemonInfo("ditto")
        assert(response.name == "ditto")
        assert(response.id == 132)
        assert(response.sprites.versions.generationi.redblue.back_default == "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/back/132.png")

    }


    @Test
    fun `when pokemon description is called then it returns pokemon description entity as response`() =
        runTest {
            coEvery { api.getPokemonDescription("aegislash") } returns pokemonDescription

            val response = api.getPokemonDescription("aegislash")

            assert(response.order == 682)
            assert(response.flavor_text_entries[0].version.url == "https://pokeapi.co/api/v2/version/23/")
        }

    @Test
    fun `when get pokemon type is called then it returns pokemon type entity as response`() =
        runTest {
            coEvery { api.getPokemonType("3") } returns pokemonType

            val response = api.getPokemonType("3")

            assert(response.damage_relations.double_damage_to[0].name == "fighting")
            assert(response.damage_relations.double_damage_from[0].name == "rock")
            assert(response.name == "flying")
            assert(response.damage_relations.double_damage_to[0].name == "fighting")
            assert(response.damage_relations.double_damage_from[0].name == "rock")
            assert(response.name == "flying")

        }

    @Test
    fun `when get evolution chain is called then it returns the evolution chain entity as respomse`() =
        runTest {
            coEvery { api.getPokemonEvolutionChain("2") } returns evolutionChain
            val response = api.getPokemonEvolutionChain("2")
            assert(response.chain.species.name == "charmander")
            assert(response.chain.evolves_to[0].species.name == "charmeleon")
            assert(response.chain.evolves_to[0].evolves_to[0].species.name == "charizard")
        }

    @Test
    fun `when get pokemon gender is called then it returns pokemon gender entity as response`() =
        runTest {
            coEvery { api.getPokemonGender() } returns pokemonGender
            val response = api.getPokemonGender()
            assert(response.results[0].name == "male")
            assert(response.results[1].name == "female")
            assert(response.results[2].name == "genderless")

        }
}