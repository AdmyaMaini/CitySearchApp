package com.example.citysearchapp

import com.example.citysearchapp.fake.FakeDataSource
import com.example.citysearchapp.fake.FakeNetworkCitySearchRepository
import com.example.citysearchapp.viewModel.CitySearchAppUiState
import com.example.citysearchapp.viewModel.CitySearchAppViewModel
import com.example.citysearchapp.viewModel.ScreenMode
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CitySearchAppViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun citySearchAppViewModel_getSearchResult_verifyUiState() = runTest{
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        viewModel.getSearchResult("Sam")
        advanceUntilIdle()
        val results = FakeDataSource.fakeDataList.locations
        val builder = viewModel.createLatLngBounds(results)
        assertEquals(CitySearchAppUiState.Success(results,builder),viewModel.uiState)

    }
    @Test
    fun citySearchAppViewModel_backToHomeScreen_verifyUiState() = runTest{
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        performSearchOperation(viewModel)
        viewModel.backToHomeScreen()
        assertEquals(CitySearchAppUiState.Rest,viewModel.uiState)
    }
    @Test
    fun citySearchAppViewMode_makeSearchInput_verifySearchInputUpdate(){
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        val input = "Input"
        viewModel.updateSearchInput(input)
        assertEquals(input,viewModel.searchInput)
    }
    @Test
    fun citySearchAppViewModel_toggleScreenMode_verifyChangeInScreenMode(){
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        viewModel.toggleScreenMode()
        assertEquals(ScreenMode.MapMode,viewModel.screenMode)
        viewModel.toggleScreenMode()
        assertEquals(ScreenMode.ListMode,viewModel.screenMode)
    }
    @Test
    fun citySearchAppViewModel_createLatLngBounds_verifyBounds(){
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        val builder = viewModel.createLatLngBounds(FakeDataSource.fakeDataList.locations)
        assertEquals(builder,LatLngBounds(LatLng(123.0, 123.0), LatLng(123.0, 123.0)))
    }
    @Test
    fun citySearchAppViewModel_getSearchResultForEmptySearchString_verifyUiState(){
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        viewModel.getSearchResult("")
        assertEquals(CitySearchAppUiState.NoResult("Search input is empty !"),viewModel.uiState)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun citySearchAppViewModel_getSearchResultForNoExistingCity_verifyUiState() = runTest{
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        viewModel.getSearchResult("ABC")
        advanceUntilIdle()
        assertEquals(CitySearchAppUiState.NoResult("No cities found !"),viewModel.uiState)
    }
    private fun performSearchOperation(viewModel: CitySearchAppViewModel){
        viewModel.getSearchResult("Sam")
    }

}