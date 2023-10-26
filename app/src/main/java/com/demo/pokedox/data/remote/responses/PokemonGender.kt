package com.demo.pokedox.data.remote.responses

data class PokemonGender(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<ResultX>
)