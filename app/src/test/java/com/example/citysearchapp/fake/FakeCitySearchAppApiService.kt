package com.example.citysearchapp.fake

import com.example.citysearchapp.model.ApiResponse
import com.example.citysearchapp.network.CitySearchAppApiService

class FakeCitySearchAppApiService : CitySearchAppApiService {
    override suspend fun getSearchResult(
        nameStartsWith: String,
        maxRows: Int,
        username: String
    ): ApiResponse {
        return FakeDataSource.fakeDataList
    }
}