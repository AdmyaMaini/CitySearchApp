package com.example.citysearchapp.ui.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.citysearchapp.model.Location


@Composable
fun CityList(
    locations: List<Location>,
    windowWidth: WindowWidthSizeClass,
    modifier: Modifier = Modifier
){
    when (windowWidth){
        WindowWidthSizeClass.Compact -> LazyColumn(
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
        WindowWidthSizeClass.Medium -> LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ){
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
        WindowWidthSizeClass.Expanded-> LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ){
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
        else -> LazyColumn(
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ){
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
    }

}