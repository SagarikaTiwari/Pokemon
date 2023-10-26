package com.demo.pokedox.data.remote.responses

data class PokemonDescription(

    val evolution_chain: EvolutionChainX,
    val flavor_text_entries: List<FlavorTextEntryX>,
    val name: String,
    val order: Int,

)