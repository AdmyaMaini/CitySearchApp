package com.example.citysearchapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                    },
                    style = MaterialTheme.typography.titleLarge,
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
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}