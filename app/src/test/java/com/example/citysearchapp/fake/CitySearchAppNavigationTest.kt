package com.example.citysearchapp.fake

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.citysearchapp.ui.theme.CitySearchAppTopBar
import com.example.citysearchapp.ui.theme.CitySearchAppViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.citysearchapp.R

class CitySearchAppNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    @Before
    fun setupComposeTestRule() {
        composeTestRule.setContent {
            val viewModel = CitySearchAppViewModel(citySearchAppRepository = FakeNetworkCitySearchRepository())
            CitySearchAppTopBar(viewModel::backToHomeScreen,viewModel.uiState)
        }
    }
    @Test
    fun topAppBar_verifyBackButtonNotVisibleInRestUiState(){
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).assertDoesNotExist()
    }
}