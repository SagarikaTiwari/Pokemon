package com.demo.pokedox.data.util

import com.demo.pokedox.data.remote.responses.Stat
import com.demo.pokedox.data.remote.responses.Type
import com.plcoding.jetpackcomposepokedex.ui.theme.*
import java.util.*

object PokemonParser {
    fun getTypeColor(type: Type): androidx.compose.ui.graphics.Color {
        return when (type.type.name.toLowerCase(Locale.ROOT)) {
            "normal" -> TypeNormal
            "fire" -> TypeFire
            "water" -> TypeWater
            "electric" -> TypeElectric
            "grass" -> TypeGrass
            "ice" -> TypeIce
            "ghost" -> TypeGhost
            "steel" -> TypeSteel
            "dragon" -> TypeDragon
            "dark" -> TypeDark
            "grass" -> TypeGrass
            "fairy" -> TypeFairy
            "fighting" -> TypeFighting
            "poison" -> TypePoison
            "psychic" -> TypePsychic
            "bug" -> TypeBug
            "rock" -> TypeRock
            "flying" -> TypeFlying
            "ground" -> TypeGround
            else -> androidx.compose.ui.graphics.Color.Black
        }
    }

    fun getTypeColorFromString(string: String): androidx.compose.ui.graphics.Color {
        return when (string.toLowerCase(Locale.ROOT)) {
            "normal" -> TypeNormal
            "fire" -> TypeFire
            "water" -> TypeWater
            "electric" -> TypeElectric
            "grass" -> TypeGrass
            "ice" -> TypeIce
            "ghost" -> TypeGhost
            "steel" -> TypeSteel
            "dragon" -> TypeDragon
            "dark" -> TypeDark
            "grass" -> TypeGrass
            "fairy" -> TypeFairy
            "fighting" -> TypeFighting
            "poison" -> TypePoison
            "psychic" -> TypePsychic
            "bug" -> TypeBug
            "rock" -> TypeRock
            "flying" -> TypeFlying
            "ground" -> TypeGround
            else -> androidx.compose.ui.graphics.Color.Black
        }
    }

    fun parseStatToColor(stat: Stat): androidx.compose.ui.graphics.Color {

        return when (stat.stat.name.toLowerCase()) {
            "hp" -> HPColor
            "attack" -> AtkColor
            "defense" -> DefColor
            "special-attack" -> SpAtkColor
            "special-defense" -> SpDefColor
            "speed" -> SpdColor
            else -> androidx.compose.ui.graphics.Color.White
        }
    }


    fun parseStatToAbbr(stat: Stat): String {

        return when (stat.stat.name.toLowerCase()) {
            "hp" -> "HP"
            "attack" -> "Attack"
            "defense" -> "Defense"
            "special-attack" -> "Special Attack"
            "special-defense" -> "Special Defense"
            "speed" -> "Speed"
            else -> ""
        }
    }

    fun parseTypeToTypeId(type: String): Int {

        return when (type.toLowerCase()) {
            "normal" -> 1

            "fighting" -> 2

            "flying" -> 3

            "poison" -> 4

            "ground" -> 5

            "rock" -> 6

            "bug" -> 7

            "ghost" -> 8

            "steel" -> 9

            "fire" -> 10

            "water" -> 11

            "grass" -> 12

            "electric" -> 13

            "psychic" -> 14

            "ice" -> 15

            "dragon" -> 16

            "dark" -> 17

            "fairy" -> 18

            "unknown" -> 19

            "shadow" -> 20

            else -> 19
        }
    }


}