package com.example.citysearchapp.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.citysearchapp.R
import com.example.citysearchapp.model.Location
@Composable
fun CitySearchApp(
    uiState: CitySearchAppUiState,
    onSubmit :(String) -> Unit,
    windowWidth : WindowWidthSizeClass,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SearchOption(onSubmit)
        when(uiState){
            is CitySearchAppUiState.Error -> ErrorScreen()
            is CitySearchAppUiState.Loading -> LoadingScreen()
            is CitySearchAppUiState.Rest -> RestScreen()
            is CitySearchAppUiState.Success -> CityList(
                uiState.locations, windowWidth
            )
        }

    }

}
@Composable
fun SearchOption(onSubmit: (String) -> Unit, modifier: Modifier = Modifier){
    var searchInput: String by rememberSaveable{mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current
    Row (modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        TextField(
            value = searchInput,
            onValueChange = {searchInput = it},
            singleLine = true,
            label = { Text("Search cities") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            //Added keyboard actions
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
@Composable
fun ErrorScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.fillMaxSize().size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}
@Composable
fun CityList(locations: List<Location>,windowWidth: WindowWidthSizeClass,modifier: Modifier = Modifier){

    when (windowWidth){
        WindowWidthSizeClass.Compact ->LazyColumn(
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
        WindowWidthSizeClass.Medium -> LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ){
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
        WindowWidthSizeClass.Expanded-> LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ){
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
        else -> LazyColumn(
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ){
            items(locations.size){
                    index -> CityCard(locations[index],modifier)
            }
        }
    }

}
@Composable
fun CityCard(location: Location ,modifier: Modifier = Modifier){
    Card (modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)){
        Column (
            modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
            ){
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleLarge
            )
            Row (modifier = modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    text = location.adminName,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = location.countryName,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
@Composable
fun RestScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.home_24px),
            contentDescription = stringResource(R.string.home_page)
        )
        Text(stringResource(R.string.home_screen), modifier = Modifier.padding(16.dp))
    }
}
