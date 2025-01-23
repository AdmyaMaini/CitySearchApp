package com.example.citysearchapp.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citysearchapp.R
import com.example.citysearchapp.model.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

enum class ScreenMode{
    ListMode,MapMode
}
@Composable
fun CitySearchApp(
    uiState: CitySearchAppUiState,
    onSubmit :(String) -> Unit,
    windowWidth : WindowWidthSizeClass,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SearchOption(uiState,onSubmit,onBack)
        when(uiState){
            is CitySearchAppUiState.Error -> ErrorScreen()
            is CitySearchAppUiState.Loading -> LoadingScreen()
            is CitySearchAppUiState.Rest -> RestScreen()
            is CitySearchAppUiState.Success -> CityResult(
                uiState.locations, windowWidth, uiState.boundingBox
            )
        }

    }

}
@Composable
fun CityResult(locations: List<Location>,windowWidth: WindowWidthSizeClass,boundingBox: LatLngBounds?){
    var screenMode by rememberSaveable { mutableStateOf(ScreenMode.ListMode) }
    Box(
        Modifier
        .fillMaxSize()
    ){
        if(screenMode == ScreenMode.ListMode){
            CityList(locations,windowWidth)
        }
        else{
            CitiesMarkedMap(boundingBox,locations)
        }
        Box(
            modifier = Modifier
                .align(if (screenMode == ScreenMode.ListMode) Alignment.BottomEnd else Alignment.BottomStart) // Fixed position at the bottom-start
                .padding(end = 16.dp, bottom = 32.dp, start = 16.dp) // Padding from the screen edges (bottom-left)
        ) {
            IconButton(
                onClick = {
                    // Toggle screen mode (List <-> Map)
                    screenMode =
                        if (screenMode == ScreenMode.ListMode) ScreenMode.MapMode else ScreenMode.ListMode
                },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        3.dp,
                        Color.Black,
                        if (screenMode == ScreenMode.MapMode) RoundedCornerShape(8.dp) else CircleShape
                    )
                    .align(Alignment.BottomStart)
            ) {
                Icon(
                    imageVector = if (screenMode == ScreenMode.ListMode) Icons.Default.Place else Icons.Default.List,
                    contentDescription = if (screenMode == ScreenMode.ListMode) "List Icon" else "Map Icon",
                    modifier = Modifier.size(32.dp) // Icon size
                )
            }
        }
    }
}
@Composable
fun SearchOption(uiState: CitySearchAppUiState,onSubmit: (String) -> Unit,onBack:() -> Unit, modifier: Modifier = Modifier){
    var searchInput: String by rememberSaveable{mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current
    var isListIcon by rememberSaveable{ mutableStateOf(true) }
    Row (
        modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        TextField(
            value = searchInput,
            onValueChange = {searchInput = it},
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
            leadingIcon = {
                if(uiState is CitySearchAppUiState.Success ||uiState is CitySearchAppUiState.Error){
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            onBack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Search")
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
        modifier = modifier
            .fillMaxSize()
            .size(200.dp),
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
    var dropDown by rememberSaveable{mutableStateOf(false) }
    Card (modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)){
        Column (
            modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
            ){
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
                ){
                Column{
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "${location.adminName}, ${location.countryName}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {dropDown = !dropDown},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, // Replace with your desired icon
                        contentDescription = "Forward Icon"
                    )
                }
            }
            if(dropDown){
                Row (
                    modifier = modifier.fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text="Latitude - ${location.latitude}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text="Longitude - ${location.longitude}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

    }
}
@Composable
fun CitiesMarkedMap(boundingBox: LatLngBounds? = null, locations: List<Location>, modifier: Modifier = Modifier){
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(boundingBox!!.center, 5f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = { isMapLoaded = true },
        cameraPositionState = cameraPositionState
    )
    {
        BasicMarkersMapContent(locations)
    }
}

@Composable
@GoogleMapComposable
fun BasicMarkersMapContent(
    mountains: List<Location>,
    onMountainClick: (Marker) -> Boolean = { false }
) {
    mountains.forEach { location ->
        Marker(
            state = rememberMarkerState(position = location.latLng),
            title = location.name,
            snippet = location.countryName,
            tag = location,
            onClick = { marker ->
                onMountainClick(marker)
                false
            }
        )
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
