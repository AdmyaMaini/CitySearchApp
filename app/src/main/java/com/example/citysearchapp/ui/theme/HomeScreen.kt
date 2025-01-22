package com.example.citysearchapp.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citysearchapp.R
import com.example.citysearchapp.model.Location
@Composable
fun CitySearchApp(
    uiState: CitySearchAppUiState,
    onSubmit :(String) -> Unit,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier){
        SearchOption(onSubmit)
        when(uiState){
            is CitySearchAppUiState.Error -> ErrorScreen()
            is CitySearchAppUiState.Loading -> LoadingScreen()
            is CitySearchAppUiState.Rest -> RestScreen()
            is CitySearchAppUiState.Success -> CityList(
                uiState.locations,
            )
        }

    }

}
@Composable
fun SearchOption(onSubmit: (String) -> Unit, modifier: Modifier = Modifier){
    var searchInput: String by rememberSaveable{mutableStateOf("")}
    Row (modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)){
        TextField(
            value = searchInput,
            onValueChange = {searchInput = it},
            label = { Text("Search cities") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                IconButton(
                    onClick = {onSubmit(searchInput)}
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
fun CityList(locations: List<Location>,modifier: Modifier = Modifier){
    LazyColumn {
        items(locations.size){
            index -> CityCard(locations[index],modifier)
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
