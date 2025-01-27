package com.example.citysearchapp.fake

import com.example.citysearchapp.model.ApiResponse
import com.example.citysearchapp.model.Location

object FakeDataSource {
    val fakeDataList = ApiResponse(
        locations = listOf(
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123",
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123"
            ),
            Location(
                name = "San Francisco",
                countryName = "USA",
                adminName = "California",
                longitude = "123",
                latitude = "123"
            )
        )
    )
}
