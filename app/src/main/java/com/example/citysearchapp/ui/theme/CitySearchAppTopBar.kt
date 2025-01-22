package com.example.citysearchapp.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citysearchapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySearchAppTopBar(onClick : () -> Unit,uiState: CitySearchAppUiState){
    TopAppBar(
        title = {
            Text(
                text = when(uiState){
                    is CitySearchAppUiState.Rest -> "Search"
                    is CitySearchAppUiState.Loading -> "Searching..."
                    else -> "Search Results"
                }
            )
                },
        navigationIcon = {
            if(uiState is CitySearchAppUiState.Error || uiState is CitySearchAppUiState.Success)
            {
                IconButton(onClick = {onClick()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}