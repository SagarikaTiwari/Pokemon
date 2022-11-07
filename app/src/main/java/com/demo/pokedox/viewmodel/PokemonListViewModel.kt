package com.demo.pokedox.viewmodel

import android.R.attr.bitmap
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.pokedox.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.palette.graphics.Palette
import coil.Coil
import coil.compose.AsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.demo.pokedox.data.models.PokedexListEntry
import com.demo.pokedox.data.remote.responses.Type
import com.demo.pokedox.util.Constants
import com.demo.pokedox.util.Constants.PAGE_SIZE
import com.demo.pokedox.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@HiltViewModel
class PokemonListViewModel @Inject constructor(

    private val repository: PokemonRepository
) : ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)


    init {

        // first time loading

        loadPokemonPaginated()
    }


    fun searchPokemonList(query: String) {

        val listToSearch = if (isSearchStarting) {

            pokemonList.value
        } else {
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch

            }

            val result = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }



            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false

            }

            pokemonList.value = result
            isSearching.value = true

        }
    }


    fun loadPokemonPaginated() {

        viewModelScope.launch {

            isLoading.value = true
            val result = repository.getPokemonList(Constants.PAGE_SIZE, curPage * PAGE_SIZE)

            when (result) {

                is Resource.Success -> {

                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count

                    // fetching image url from the given url by replacing digit in the url
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->

                        val pokemonResult = repository.getPokemonInfo(entry.name)
                        var typeList : List<Type> = emptyList()
                        when(pokemonResult){
                            is Resource.Success->{
                                 typeList = pokemonResult.data!!.types
                            }
                            else -> {}
                        }

                        val number = if (entry.url.endsWith("/")) {

                            entry.url.dropLast(1).takeLastWhile {
                                it.isDigit()
                            }
                        } else {

                            entry.url.takeLastWhile {
                                it.isDigit()
                            }
                        }


                            //val imageurl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"


                        //   val imageurl = "https://pokeres.bastionbot.org/images/pokemon/${number}.png"


                        val name = entry.name
                       val imageurl = "https://img.pokemondb.net/artwork/large/${name}.jpg"
                    //  val imageurl =   "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-viii/icons/${number}.png"
                  // val imageurl =      "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/${number}.png"



                        PokedexListEntry(
                            entry.name.capitalize(Locale.ROOT),
                            imageurl,
                            number.toInt(),
                            typeList
                        )
                    }
                    curPage++


                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries

                }
                is Resource.Error -> {

                    loadError.value = result.message!!
                    isLoading.value = false


                }
                is Resource.Loading -> TODO()
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {

        val bmp = (drawable as BitmapDrawable).bitmap.copy(
            Bitmap.Config.ARGB_8888, true
        )

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun onQueryChanged(it: Any) {

    }


}

