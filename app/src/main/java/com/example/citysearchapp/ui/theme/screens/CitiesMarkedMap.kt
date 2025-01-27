package com.example.citysearchapp.ui.theme.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.citysearchapp.model.Location
import com.example.citysearchapp.ui.theme.components.BasicMarkersMapContent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CitiesMarkedMap(
    boundingBox: LatLngBounds? = null,
    locations: List<Location>
){
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(boundingBox!!.center, 5f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = { isMapLoaded = true },
        cameraPositionState = cameraPositionState
    )
    {
        BasicMarkersMapContent(locations)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = boundingBox) {
        zoomAll(scope, cameraPositionState, boundingBox!!)
    }

}
fun zoomAll(
    scope: CoroutineScope,
    cameraPositionState: CameraPositionState,
    boundingBox: LatLngBounds
) {
    scope.launch {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngBounds(boundingBox, 64),
            durationMs = 1000
        )
    }
}