package com.example.citysearchapp.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.citysearchapp.viewModel.CitySearchAppUiState


@Composable
fun SearchOption(
    uiState: CitySearchAppUiState,
    onSubmit: (String) -> Unit,
    onBack:() -> Unit,
    searchInput : String,
    updateSearchInput : (String) -> Unit,
    modifier: Modifier = Modifier
){
    val keyboardController = LocalSoftwareKeyboardController.current
    Row (
        modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        TextField(
            value = searchInput,
            onValueChange = {updateSearchInput(it)},
            singleLine = true,
            label = { Text("Search cities") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSubmit(searchInput)
                }
            ),
            leadingIcon = {
                if(uiState is CitySearchAppUiState.Success ||uiState is CitySearchAppUiState.Error || uiState is CitySearchAppUiState.NoResult){
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            onBack()
                        }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Search")
                    }
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        keyboardController?.hide()
                        onSubmit(searchInput)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

    }
}