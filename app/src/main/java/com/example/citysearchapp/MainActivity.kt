package com.example.citysearchapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citysearchapp.ui.theme.CitySearchApp
import com.example.citysearchapp.ui.theme.theme.CitySearchAppTheme
import com.example.citysearchapp.viewModel.CitySearchAppViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val citySearchAppViewModel: CitySearchAppViewModel =
                viewModel(factory = CitySearchAppViewModel.Factory)
            val screenWidth = calculateWindowSizeClass(this)
            CitySearchAppTheme {
                Scaffold(
                    content = {
                        innerPadding ->
                        CitySearchApp(
                            citySearchAppViewModel.uiState,
                            citySearchAppViewModel::getSearchResult,
                            screenWidth.widthSizeClass,
                            citySearchAppViewModel::backToHomeScreen,
                            citySearchAppViewModel.screenMode,
                            citySearchAppViewModel::toggleScreenMode,
                            citySearchAppViewModel.searchInput,
                            citySearchAppViewModel::updateSearchInput,
                            Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

