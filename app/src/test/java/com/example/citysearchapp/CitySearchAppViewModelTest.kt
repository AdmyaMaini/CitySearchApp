package com.example.citysearchapp

import com.example.citysearchapp.fake.FakeDataSource
import com.example.citysearchapp.fake.FakeNetworkCitySearchRepository
import com.example.citysearchapp.ui.theme.CitySearchAppUiState
import com.example.citysearchapp.ui.theme.CitySearchAppViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CitySearchAppViewModelTest {
    @Test
    fun citySearchAppViewModel_getSearchResult_verifyUiState() = runTest{
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        performSearchOperation(viewModel)
        assertEquals(CitySearchAppUiState.Success(FakeDataSource.fakeDataList.locations),viewModel.uiState)

    }
    @Test
    fun citySearchAppViewModel_backToHomeScreen_verifyUiState() = runTest{
        val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
        performSearchOperation(viewModel)
        viewModel.backToHomeScreen()
        assertEquals(CitySearchAppUiState.Rest,viewModel.uiState)
    }

    private fun performSearchOperation(viewModel: CitySearchAppViewModel){
        viewModel.getSearchResult("Sam")
    }
}