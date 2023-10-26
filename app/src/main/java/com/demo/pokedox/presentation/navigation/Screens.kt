package com.demo.pokedox.presentation.navigation


sealed class Screens(val route: String) {
    object PokemonList: Screens("pokemon_list_screen")
    object PokemonDetail: Screens("pokemon_detail_screen")
 }