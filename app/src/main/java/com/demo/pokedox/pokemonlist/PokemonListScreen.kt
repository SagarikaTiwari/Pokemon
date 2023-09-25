package com.demo.pokedox.pokemonlist

import Roboto
import RobotoCondensed
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import coil.compose.*
import coil.request.ImageRequest
import com.demo.pokedox.R
import com.demo.pokedox.data.models.PokedexListEntry
import com.demo.pokedox.data.remote.responses.Type
import com.demo.pokedox.util.PokemonParser
import com.demo.pokedox.viewmodel.PokemonListViewModel
import com.demo.pokedox.widget.FilterBox
import com.plcoding.jetpackcomposepokedex.ui.theme.searchBarBackgroundColor
import dashedBorder
import kotlinx.coroutines.launch

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    var openfilterialog by remember {
        mutableStateOf(false) // Initially dialog is closed
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Pokédex",
                fontFamily = Roboto,
                fontSize = 40.sp,
                fontWeight = Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                color = Color(0xff2e3156)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Transparent)
                    .padding(start = 20.dp, end = 20.dp)
                    .border(3.dp, color = Color(0xff2e3156))
            )


            Text(
                text = "Search for any Pokémon that exists on the planet",
                fontFamily = Roboto,
                fontSize = 18.sp,
                fontWeight = Normal,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                color = Color(0xff2e3156)
            )



            Row(
                modifier = Modifier.padding(start = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                SearchBar(
                    hint = "Name or number",
                    modifier = Modifier
                        .fillMaxWidth(07f)
                        .background(MaterialTheme.colors.primary)
                        .clip(RectangleShape)
                ) {
                    viewModel.searchPokemonList(it)
                }


                Image(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "Pokemon Logo",
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(15.dp)
                        .clickable {
                            openfilterialog = true
                        }
                )

                if (openfilterialog) {
                    FilterBox(
                        filterName = "Type",
                        listOf("Normal", "Flying", "Poison", "Ground", "Rock"),
                        expanded = true

                    )
                }
            }



            PokemonList(navController = navController)


        }
    }

}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(Color(0xffc9dde2), RoundedCornerShape(5.dp))
                .onFocusChanged {
                    isHintDisplayed = !(it.isFocused)

                },
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color(0x5c5f7e80),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

        }

    }


}

@Composable
 fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel(),

    ) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {

        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1

        }

        items(itemCount) {

            if ((it >= (itemCount - 1)) && !endReached && !isLoading && !isSearching) {
                //pagination

                LaunchedEffect(key1 = true) {
                    viewModel.loadPokemonPaginated()
                }
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }

        }
    }
}


@Composable
fun PokedexEntry(

    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()

) {

    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    var colorList = mutableListOf<Color>()
    if (entry.typeList.size > 1) {
        for (i in entry.typeList) {
            colorList.add(PokemonParser.getTypeColor(i))
        }
    } else {
        for (i in entry.typeList) {
            colorList.add(PokemonParser.getTypeColor(i))

        }
        for (i in entry.typeList) {
            colorList.add(PokemonParser.getTypeColor(i))

        }
    }


    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .dashedBorder(
                width = 1.dp,
                color = colorResource(id = R.color.border_color),
                shape = MaterialTheme.shapes.small, on = 5.dp, off = 5.dp
            )
            .background(

                Brush.verticalGradient(
                    colors = colorList

                )
            )
            .clickable {

                navController.navigate("pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}/${entry.number}")
            }

    ) {

        Column {


            val painter = rememberAsyncImagePainter(
                entry.imageUrl
            )
            Image(
                painter = painter,
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
            )



            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)

            )
        }
    }

}

@Composable

fun PokedexRow(
    rowIndex: Int,
    entries: List<PokedexListEntry>,
    navController: NavController
) {

    Column() {

        Row() {
            PokedexEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {

                PokedexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {

                Spacer(modifier = Modifier.weight(1f))

            }

        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Composable

fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }

    }
}






