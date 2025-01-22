package com.example.citysearchapp.fake

import com.example.citysearchapp.data.CitySearchAppRepository
import com.example.citysearchapp.model.ApiResponse

class FakeNetworkCitySearchRepository : CitySearchAppRepository {
    override suspend fun getCitiesDetails(
        nameStartsWith: String,
        maxRows: Int,
        username: String
    ): ApiResponse {
        return FakeDataSource.fakeDataList
    }
}