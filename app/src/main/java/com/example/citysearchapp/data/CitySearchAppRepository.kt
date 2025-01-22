package com.example.citysearchapp.data

import com.example.citysearchapp.model.ApiResponse
import com.example.citysearchapp.network.CitySearchAppApiService

interface CitySearchAppRepository {
    suspend fun getCitiesDetails(nameStartsWith: String,
                                 maxRows: Int,
                                 username: String):ApiResponse
}
class NetworkCitySearchRepository(private val citySearchAppApiService: CitySearchAppApiService): CitySearchAppRepository{

   override suspend fun getCitiesDetails(
        nameStartsWith: String,
        maxRows: Int ,
        username: String
    ): ApiResponse = citySearchAppApiService.getSearchResult(nameStartsWith,maxRows,username)
}