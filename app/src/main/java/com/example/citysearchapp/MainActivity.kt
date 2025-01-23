package com.example.citysearchapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citysearchapp.ui.theme.CitiesMarkedMap
import com.example.citysearchapp.ui.theme.CitySearchApp
import com.example.citysearchapp.ui.theme.CitySearchAppTheme
import com.example.citysearchapp.ui.theme.CitySearchAppTopBar
import com.example.citysearchapp.ui.theme.CitySearchAppUiState
import com.example.citysearchapp.ui.theme.CitySearchAppViewModel
import com.example.citysearchapp.ui.theme.FloatingActionButton

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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
                            Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

