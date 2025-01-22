package com.example.citysearchapp.network

import com.example.citysearchapp.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CitySearchAppApiService {

    @GET("searchJSON")
    suspend fun getSearchResult(
        @Query("name_startsWith", encoded = true) nameStartsWith: String,
        @Query("maxRows") maxRows: Int,
        @Query("username") username: String
    ):ApiResponse

}