package com.demo.pokedox.data.repository

import com.demo.pokedox.data.remote.PokeApi
import com.demo.pokedox.data.remote.responses.*
import com.demo.pokedox.data.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
)  {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {

        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {

        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonDescription(pokemonName: String): Resource<PokemonDescription> {
        val response = try {
            api.getPokemonDescription(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonTypeForWeaknessandability(number: String): Resource<PokemonType> {
        val response = try {
            api.getPokemonType(number)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonEvolutionChain(number: String): Resource<PokemonEvolutionChain> {
        val response = try {
            api.getPokemonEvolutionChain(number)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonGender(): Resource<PokemonGender> {
        val response = try {
            api.getPokemonGender()
        } catch (e: Exception) {
            return Resource.Error("An error occured !")
        }
        return Resource.Success(response)
    }


}