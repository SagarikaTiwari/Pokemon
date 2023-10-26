package com.demo.pokedox.presentation.widget

import Roboto
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.demo.pokedox.R

@Composable
fun FilterBoxForType(
    filterName: String,
    listOfOptions: Array<String>,
) {
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .border(1.dp, Color(0xff2e3156), RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp),
        contentAlignment = Alignment.Center

    ) {

        Column {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = filterName,
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2e3156)
                )
                Text(
                    text = "(Normal     +",
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff2e3156)
                )
                Text(
                    text = "5 More)",
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2e3156)
                )
                val painter: Painter = if (mExpanded)
                    painterResource(id = R.drawable.close_dropdown)
                else
                    painterResource(id = R.drawable.add_image)
                Icon(painter = painter, contentDescription = "dropdown image",
                    modifier = Modifier.clickable {
                        mExpanded = !mExpanded
                    })
            }
            if (mExpanded) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 100.dp)
                ) {
                    items(listOfOptions.size) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            val (checkedState, onStateChange) = remember { mutableStateOf(false) }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .toggleable(
                                        value = checkedState,
                                        onValueChange = { onStateChange(!checkedState) },
                                        role = Role.Checkbox
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checkedState,
                                    onCheckedChange = null
                                )
                                Text(
                                    text = listOfOptions[it],
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterBoxForStats(
    filterName: String,
    listOfOptions: Array<String>,
) {
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var sliderPosition by remember { mutableStateOf(70f..150f) }

    Box(
        modifier = Modifier
            .border(1.dp, Color(0xff2e3156), RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp),
        contentAlignment = Alignment.Center

    ) {

        Column {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = filterName,
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2e3156)
                )
                Text(
                    text = "(Normal     +",
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff2e3156)
                )
                Text(
                    text = "5 More)",
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2e3156)
                )
                val painter: Painter = if (mExpanded)
                    painterResource(id = R.drawable.close_dropdown)
                else
                    painterResource(id = R.drawable.add_image)
                Icon(painter = painter, contentDescription = "dropdown image",
                    modifier = Modifier.clickable {
                        mExpanded = !mExpanded
                    })
            }
            if (mExpanded) {
                LazyColumn() {
                    items(listOfOptions.size) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {


                            RangeSlider(
                                values = sliderPosition,
                                steps = 4,
                                onValueChange = { range -> sliderPosition = range },
                                valueRange = 0f..210f,
                                onValueChangeFinished = {
                                    // launch some business logic update with the state you hold
                                    // viewModel.updateSelectedSliderValue(sliderPosition)
                                }, colors = SliderDefaults.colors(
                                    thumbColor = Color.DarkGray, activeTrackColor = Color.DarkGray,
                                    inactiveTrackColor = Color.LightGray
                                )

                            )
                            Text(listOfOptions[it] + "  -  " + sliderPosition.toString())
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun FilterBoxForGender(
    filterName: String,
    listOfOptions: Array<String>,
) {
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .border(1.dp, Color(0xff2e3156), RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp),
        contentAlignment = Alignment.Center

    ) {

        Column {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = filterName,
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2e3156)
                )
                Text(
                    text = "(Normal     +",
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff2e3156)
                )
                Text(
                    text = "5 More)",
                    fontFamily = Roboto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2e3156)
                )
                val painter: Painter = if (mExpanded)
                    painterResource(id = R.drawable.close_dropdown)
                else
                    painterResource(id = R.drawable.add_image)
                Icon(painter = painter, contentDescription = "dropdown image",
                    modifier = Modifier.clickable {
                        mExpanded = !mExpanded
                    })
            }
            if (mExpanded) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 120.dp)
                ) {
                    items(listOfOptions.size) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            val (checkedState, onStateChange) = remember { mutableStateOf(false) }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .toggleable(
                                        value = checkedState,
                                        onValueChange = { onStateChange(!checkedState) },
                                        role = Role.Checkbox
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checkedState,
                                    onCheckedChange = null
                                )
                                Text(
                                    text = listOfOptions[it],
                                    modifier = Modifier.padding(start = 10.dp),
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun FilterDialog(openDialog: Boolean, openDialogState: (Boolean) -> Unit) {

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialogState(false)
            }
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
                    .clip(RectangleShape)
            ) {
                Column {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {


                        Text(
                            text = "Filters",
                            fontFamily = Roboto,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                            color = Color(0xff2e3156)
                        )

                        val painter = painterResource(id = R.drawable.pop_up_close)

                        Icon(painter = painter, contentDescription = "dropdown image",
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 20.dp)
                                .clickable {
                                    openDialogState(false)
                                })


                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Transparent)
                            .padding(start = 10.dp, end = 20.dp)
                            .border(3.dp, color = Color(0xff2e3156))
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    FilterBoxForType(
                        filterName = "Type",
                        stringArrayResource(id = R.array.pokemontype)


                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    FilterBoxForGender(
                        filterName = "Gender", stringArrayResource(id = R.array.pokemongenders),

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    FilterBoxForStats(
                        filterName = "Stats",
                        stringArrayResource(id = R.array.pokemonstats),

                        )

                    Spacer(modifier = Modifier.height(100.dp))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Transparent)
                            .border(3.dp, color = Color(0xff2e3156))
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        OutlinedButton(
                            onClick = {
                                // load all initial values
                            }, border = BorderStroke(1.dp, Color(0xff2e3156)),
                            shape = RoundedCornerShape(30), // = 50% percent
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(
                                    0xff2e3156
                                )
                            )
                        ) {
                            Text(text = "Reset")
                        }
                        OutlinedButton(
                            onClick = {
                            }, border = BorderStroke(1.dp, Color.White),
                            shape = RoundedCornerShape(30), // = 50% percent
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White,
                                backgroundColor = Color(0xff2e3156)
                            )
                        ) {
                            // call pokemon search with new values
                            Text(text = "Apply")
                        }
                    }
                }
            }
        }
    }
}
