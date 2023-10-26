package com.demo.pokedox.data.remote

import com.demo.pokedox.data.remote.responses.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(

        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon

    @GET("pokemon-species/{name}")
    suspend fun getPokemonDescription(
        @Path("name") name: String
    ): PokemonDescription

    //strength weakness
    @GET("type/{number}")
    suspend fun getPokemonType(
        @Path("number") number: String
    ): PokemonType

    //gender
    @GET("gender")
    suspend fun getPokemonGender(
    ): PokemonGender

    //evolution chain
    @GET("evolution-chain/{number}")
    suspend fun getPokemonEvolutionChain(
        @Path("number") number: String
    ): PokemonEvolutionChain


}