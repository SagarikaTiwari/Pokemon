package com.demo.pokedox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.demo.pokedox.presentation.screens.PokemonDetailScreen
import com.demo.pokedox.presentation.screens.PokemonListScreen
import java.util.*

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.PokemonList.route)
    {
        composable(route = Screens.PokemonList.route){
            PokemonListScreen(navController)
        }
        composable(
            Screens.PokemonDetail.route+"/{pokemonName}/{pokemonId}",
            arguments = listOf(
                navArgument("pokemonName"){
                    type = NavType.StringType
                },
                navArgument("pokemonId"){
                    type = NavType.IntType
                }


            )){
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            val pokemonId = remember {
                it.arguments?.getInt("pokemonId")
            }
            PokemonDetailScreen(
                pokemonName = pokemonName?.toLowerCase(Locale.ROOT)?:"",
                pokemonId= pokemonId!!,
                navController = navController
            )
        }
    }
}

