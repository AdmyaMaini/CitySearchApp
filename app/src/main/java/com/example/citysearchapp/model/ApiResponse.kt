package com.example.citysearchapp.model

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
    val adminName: String
)