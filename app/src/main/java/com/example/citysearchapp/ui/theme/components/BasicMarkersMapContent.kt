package com.example.citysearchapp.ui.theme.components

import androidx.compose.runtime.Composable
import com.example.citysearchapp.model.Location
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState


@Composable
@GoogleMapComposable
fun BasicMarkersMapContent(
    mountains: List<Location>,
    onMountainClick: (Marker) -> Boolean = { false }
) {
    mountains.forEach { location ->
        Marker(
            state = rememberMarkerState(position = location.latLng),
            title = location.name,
            snippet = location.countryName,
            tag = location,
            onClick = { marker ->
                onMountainClick(marker)
                false
            }
        )
    }
}