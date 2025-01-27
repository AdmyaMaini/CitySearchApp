package com.example.citysearchapp

import com.example.citysearchapp.data.NetworkCitySearchRepository
import com.example.citysearchapp.fake.FakeCitySearchAppApiService
import com.example.citysearchapp.fake.FakeDataSource
import com.example.citysearchapp.model.ApiResponse
import com.example.citysearchapp.network.CitySearchAppApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkCitySearchRepositoryTest {
    //thingUnderTest_TriggerOfTest_ResultOfTest
    @Test
    fun networkCitySearchRepository_getCitiesDetails_apiResponseVerified()= runTest{
        val repository = NetworkCitySearchRepository(citySearchAppApiService = FakeCitySearchAppApiService())
        assertEquals(repository.getCitiesDetails("San", maxRows = 10, username = "keep_truckin"),FakeDataSource.fakeDataList)
    }
}