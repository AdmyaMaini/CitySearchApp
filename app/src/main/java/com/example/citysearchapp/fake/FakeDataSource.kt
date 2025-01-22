package com.example.citysearchapp.fake

import com.example.citysearchapp.model.ApiResponse
import com.example.citysearchapp.model.Location

object FakeDataSource {
    val fakeDataList = ApiResponse(
        locations = listOf(
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California"
            )
        )
    )
}
