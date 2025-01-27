package com.example.citysearchapp.ui.theme.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.citysearchapp.model.Location
import com.example.citysearchapp.viewModel.ScreenMode
import com.example.citysearchapp.ui.theme.components.CityList
import com.google.android.gms.maps.model.LatLngBounds

@Composable
fun CityResult(
    locations: List<Location>,
    windowWidth: WindowWidthSizeClass,
    boundingBox: LatLngBounds?,
    screenMode: ScreenMode,
    toggleScreenMode : () -> Unit
){

    Box(
        Modifier
            .fillMaxSize()
    ){
        if(screenMode == ScreenMode.ListMode){
            CityList(locations,windowWidth)
        }
        else{
            CitiesMarkedMap(boundingBox,locations)
        }
        Box(
            modifier = Modifier
                .align(
                    if (screenMode == ScreenMode.ListMode)
                        Alignment.BottomEnd
                    else
                        Alignment.BottomStart
                )
                .padding(end = 16.dp, bottom = 32.dp, start = 16.dp)
        ) {
            IconButton(
                onClick = {
                    toggleScreenMode()
                },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        3.dp,
                        Color.Black,
                        if (screenMode == ScreenMode.MapMode) RoundedCornerShape(8.dp) else CircleShape
                    )
                    .align(Alignment.BottomStart)
            ) {
                Icon(
                    imageVector = if (screenMode == ScreenMode.ListMode) Icons.Default.Place else Icons.AutoMirrored.Default.List,
                    contentDescription = if (screenMode == ScreenMode.ListMode) "List Icon" else "Map Icon",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}