package com.example.citysearchapp.model

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerialName("geonames")
    val locations: List<Location>
)

@Serializable
data class Location(
    @SerialName("toponymName")
    val name: String,
    @SerialName("countryName")
    val countryName: String,
    @SerialName("adminName1")
    val adminName: String,
    @SerialName("lng")
    val longitude: String,
    @SerialName("lat")
    val latitude :String
){
    val latLng: LatLng
        get() = LatLng(latitude.toDoubleOrNull() ?: 0.0, longitude.toDoubleOrNull() ?: 0.0)
}