package com.demo.pokedox.pokemon


import Roboto
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.demo.pokedox.R
import com.demo.pokedox.data.remote.responses.*
import com.demo.pokedox.util.PokemonParser
import com.demo.pokedox.util.PokemonParser.getTypeColor
import com.demo.pokedox.util.PokemonParser.parseStatToAbbr
import com.demo.pokedox.util.PokemonParser.parseStatToColor
import com.demo.pokedox.util.Resource
import com.demo.pokedox.viewmodel.PokemonDetailViewModel
import com.google.android.material.internal.ViewUtils.RelativePadding
import dashedBorder
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.math.round


@Composable
fun PokemonDetailScreen(

    dominantColor: Color,
    pokemonName: String,
    pokemonId: Int,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 150.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val evolutionId: Int = 0
    val pokemonInfo = produceState<Resource<Pokemon>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    val pokemonDesc = produceState<Resource<PokemonDescription>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getPokemonDescription(pokemonName)
    }.value

    val pokemonWeaknessAndAbility = produceState<Resource<PokemonType>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getPokemonType(pokemonId.toString())
    }.value

    val pokemonEvolutionChain = produceState<Resource<PokemonEvolutionChain>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getPokemonEvolutionChain(evolutionId.toString())
    }.value

    val pokemonGender = produceState<Resource<PokemonGender>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getPokemonGender(pokemonId.toString())
    }.value



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(bottom = 16.dp)
    ) {
        val scrollState = rememberScrollState()


        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {


            PokemonTopSection(
                pokemonName,
                pokemonId,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
            )

            PokemonDesciptionStateWrapper(pokemonDesc = pokemonDesc)
            if (pokemonInfo is Resource.Success) {

                var colorList = mutableListOf<Color>()
                if (pokemonInfo.data?.types?.size!! > 1) {
                    for (i in pokemonInfo.data.types) {
                        colorList.add(PokemonParser.getTypeColor(i))
                    }
                } else {
                    for (i in pokemonInfo.data.types) {
                        colorList.add(PokemonParser.getTypeColor(i))

                    }
                    for (i in pokemonInfo.data.types) {
                        colorList.add(PokemonParser.getTypeColor(i))

                    }
                }


                Row() {

                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .aspectRatio(1f)
                            .padding(20.dp)
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
                    ) {
                        pokemonInfo.data?.sprites?.let {
                            val painter = rememberAsyncImagePainter(
                                // model = it.front_default
                                model = "https://img.pokemondb.net/artwork/large/${pokemonName}.jpg"

                            )
                            val painterState = painter.state
                            Image(
                                painter = painter,
                                contentDescription = pokemonInfo.data.name,
                                modifier = Modifier
                                    .size(100.dp)
                                    .offset(y = topPadding)

                            )

                        }
                    }
                    // description
                    var fullDesc: String = ""
                    pokemonDesc.data?.flavor_text_entries?.let { textEntries ->

                        for (i in textEntries) {
                            fullDesc += i.flavor_text
                        }
                    }

                    Column() {


                        Text(
                            text = fullDesc,
                            maxLines = 4,
                            fontFamily = Roboto,
                            fontWeight = FontWeight.Normal,
                            fontSize = 19.sp,
                            modifier = Modifier
                                .padding(
                                    start = 15.dp,
                                    top = 20.dp,
                                    end = 15.dp
                                )
                        )

                        var openDialog by remember {
                            mutableStateOf(false) // Initially dialog is closed
                        }
                        ClickableText(
                            text = AnnotatedString("read more"),
                            maxLines = 4,
                            style = TextStyle(
                                fontFamily = Roboto,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            ),

                            modifier = Modifier
                                .padding(start = 15.dp),
                            onClick = {
                                openDialog = true
                            }

                        )

                        if (openDialog) {
                            PopupWindowDialog(fullDescription = fullDesc) {
                                openDialog = false
                            }
                        }
                    }

                }

            }

            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
            )
        }


    }
}


@Composable
fun PokemonTopSection(
    pokemonName: String,
    pokemonId: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.Transparent
                    )
                )
            )
            .fillMaxWidth(1f)
    ) {
        Row() {


            Text(
                text = "${pokemonName.capitalize(java.util.Locale.ROOT)}" + "\n" + "0${pokemonId}",
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,

                fontSize = 30.sp,
                textAlign = TextAlign.Start,
                color = Color(0xff2e3156), modifier = Modifier
                    .offset(16.dp, 16.dp)
                    .fillMaxWidth(0.8f)

            )
            Icon(

                painter = painterResource(id = R.drawable.close_image),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(36.dp)
                    .offset(16.dp, 20.dp)
                    .padding(5.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

        }
    }

}

@Composable
fun pokemonTypeStateWrapper(
    pokemonType: Resource<PokemonType>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier
) {
    when (pokemonType) {

        is Resource.Success -> {

        }
        is Resource.Error -> {
            Text(
                text = pokemonType.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }


}

@Composable
fun pokemonEvolutionStateWrapper(
    pokemonEvolition: Resource<PokemonEvolutionChain>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier
) {

    when (pokemonEvolition) {

        is Resource.Success -> {


        }
        is Resource.Error -> {
            Text(
                text = pokemonEvolition.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun PokemonDesciptionStateWrapper(
    pokemonDesc: Resource<PokemonDescription>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonDesc) {

        is Resource.Success -> {
//            PokemonDescriptionSection(
//                pokemonDesc = pokemonDesc.data!!,
//                modifier = modifier
//            )
        }
        is Resource.Error -> {
            Text(
                text = pokemonDesc.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }

}


@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonInfo) {
        is Resource.Success -> {

            PokemonDetailSection(
                pokemonInfo = pokemonInfo.data!!,
            )
        }
        is Resource.Error -> {
            Text(
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )

        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )

        }

    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier

) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {


        }

        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokeminHeight = pokemonInfo.height,
            pokemonInfo = pokemonInfo
        )
        PokemonBaseStats(pokemonInfo = pokemonInfo)
    }

}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {


        for (type in types) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .size(35.dp)
                    .padding(horizontal = 2.dp, vertical = 1.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(getTypeColor(type))
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(2.dp))
            ) {

                Text(
                    text = type.type.name.capitalize(java.util.Locale.ROOT),
                    color = Color(0xff2e3156),
                    fontSize = 13.sp
                )
            }
        }
    }
}


@Composable
fun PokemonDetailDataSection(
    pokemonInfo: Pokemon,
    pokemonWeight: Int,
    pokeminHeight: Int,
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight * 100f) / 1000f
    }


    val pokemonHeightInMeters = remember {
        round(pokeminHeight * 100f) / 1000f
    }

    var ability: String = ""

    for (i in pokemonInfo.abilities) {
        ability += i.ability.name + ", "
    }



    Column() {


        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PokemonDetailDataItem(
                dataValue = pokemonWeightInKg,
                dataUnit = "kg",
                dataTitle = "Weight",
                //dataIcon = painterResource(id = R.drawable.ic_weight),
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier
                    .size(1.dp, sectionHeight)
                    .background(Color.Transparent)
            )

            PokemonDetailDataItem(
                dataValue = pokemonHeightInMeters,
                dataUnit = "m",
                dataTitle = "Height",

                // dataIcon = painterResource(id = R.drawable.ic_height),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PokemonDetailDataItemText(
                dataValue = "NA",
                dataTitle = "Gender(s)",
                //dataIcon = painterResource(id = R.drawable.ic_weight),
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier
                    .size(1.dp, sectionHeight)
                    .background(Color.Transparent)
            )

            PokemonDetailDataItemText(
                dataValue = "Monster, Dragons",
                dataTitle = "Egg Groups",

                // dataIcon = painterResource(id = R.drawable.ic_height),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {


            PokemonDetailDataItemText(
                dataValue = ability,
                dataTitle = "Abilities",
                //dataIcon = painterResource(id = R.drawable.ic_weight),
                modifier = Modifier.weight(0.5f)
            )

            Spacer(
                modifier = Modifier
                    .size(1.dp, sectionHeight)
                    .background(Color.Transparent)
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(20.dp)

            ) {


                Text(
                    text = "Types",
                    fontWeight = Bold,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(start = 20.dp)

                )
                Spacer(modifier = Modifier.height(8.dp))
                PokemonTypeSection(types = pokemonInfo.types)


            }

        }
    }

}


@Composable
fun PokemonDetailDataItemText(
    dataValue: String,
    dataTitle: String,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(20.dp)
    ) {


        Text(
            text = dataTitle,
            fontWeight = Bold,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$dataValue",
            color = MaterialTheme.colors.onSurface
        )
    }

}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataTitle: String,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(20.dp)
    ) {


        Text(
            text = dataTitle,
            fontWeight = Bold,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$dataValue$dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }

}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPecrent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true

    }

    Row(
        modifier = Modifier.padding(start = 30.dp, end = 30.dp)

    ) {

        Text(
            text = statName,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RectangleShape)
                .background(
                    if (isSystemInDarkTheme()) {
                        Color(0xff93b3b3)
                    } else {
                        Color(0xff93b3b3)
                    }
                )
        ) {


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(curPecrent.value)
                    .clip(RectangleShape)
                    .background(Color(0xff2e3156))
            ) {

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = (curPecrent.value * statMaxValue).toInt().toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animationDelayPerItem: Int = 100
) {

    val maxBaseStat = remember {

        pokemonInfo.stats.maxOf {
            it.base_stat
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffb0d2d2))
    ) {
        Text(
            text = "Stats",
            fontSize = 20.sp,
            fontWeight = Bold,
            fontFamily = Roboto,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = 30.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.base_stat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animationDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun PopupWindowDialog(
    fullDescription: String,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .padding(20.dp),
            color = Color(0xff2e3156),
            elevation = 4.dp
        ) {

            val scrollState = rememberScrollState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {


                Icon(

                    painter = painterResource(id = R.drawable.pop_up_close),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(36.dp)
                        .padding(10.dp)
                        .clickable {

                            onDismiss
                        },

                    )



                Text(
                    modifier = Modifier.padding(20.dp),
                    text = fullDescription,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.roboto_light, FontWeight.Bold)),
                        fontSize = 20.sp
                    )
                )


            }
        }
    }
}































