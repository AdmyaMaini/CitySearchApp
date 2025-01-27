package com.example.citysearchapp.ui.theme



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citysearchapp.ui.theme.components.SearchOption
import com.example.citysearchapp.ui.theme.screens.CityResult
import com.example.citysearchapp.ui.theme.screens.ErrorScreen
import com.example.citysearchapp.ui.theme.screens.LoadingScreen
import com.example.citysearchapp.ui.theme.screens.NoResultScreen
import com.example.citysearchapp.ui.theme.screens.RestScreen
import com.example.citysearchapp.viewModel.CitySearchAppUiState
import com.example.citysearchapp.viewModel.ScreenMode


@Composable
fun CitySearchApp(
    uiState: CitySearchAppUiState,
    onSubmit :(String) -> Unit,
    windowWidth : WindowWidthSizeClass,
    onBack: () -> Unit,
    screenMode: ScreenMode,
    toggleScreenMode: () -> Unit,
    searchInput: String,
    updateSearchInput : (String) -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SearchOption(
            uiState,
            onSubmit,
            onBack,
            searchInput,
            updateSearchInput
        )
        when(uiState){
            is CitySearchAppUiState.Error -> ErrorScreen(
                onSubmit,
                searchInput
            )
            is CitySearchAppUiState.Loading -> LoadingScreen()
            is CitySearchAppUiState.Rest -> RestScreen()
            is CitySearchAppUiState.NoResult -> NoResultScreen(
                uiState.message
            )
            is CitySearchAppUiState.Success -> CityResult(
                uiState.locations,
                windowWidth,
                uiState.boundingBox,
                screenMode,
                toggleScreenMode
            )
        }

    }

}



