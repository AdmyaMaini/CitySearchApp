package com.example.citysearchapp.data

import com.example.citysearchapp.network.CitySearchAppApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val citySearchAppRepository : CitySearchAppRepository
}
 class DefaultAppContainer : AppContainer{
     private val baseUrl =
         "https://secure.geonames.org/"
//         "https://android-kotlin-fun-mars-server.appspot.com/"
     private val json = Json {
         ignoreUnknownKeys = true
     }
     private val retrofit = Retrofit.Builder()
         .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
         .baseUrl(baseUrl)
         .build()
     private val retrofitService: CitySearchAppApiService by lazy {
         retrofit.create(CitySearchAppApiService::class.java)
     }
     override val citySearchAppRepository by lazy {
         NetworkCitySearchRepository(retrofitService)
     }

 }
