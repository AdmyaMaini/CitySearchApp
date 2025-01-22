package com.example.citysearchapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citysearchapp.ui.theme.CitySearchApp
import com.example.citysearchapp.ui.theme.CitySearchAppTheme
import com.example.citysearchapp.ui.theme.CitySearchAppTopBar
import com.example.citysearchapp.ui.theme.CitySearchAppViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val citySearchAppViewModel: CitySearchAppViewModel =
                viewModel(factory = CitySearchAppViewModel.Factory)
            CitySearchAppTheme {
                Scaffold(
                    topBar = {
                        CitySearchAppTopBar(citySearchAppViewModel::backToHomeScreen,citySearchAppViewModel.uiState)
                    },
                    content = {
                        innerPadding ->

                        CitySearchApp(
                            citySearchAppViewModel.uiState,
                            citySearchAppViewModel::getSearchResult,
                            Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

