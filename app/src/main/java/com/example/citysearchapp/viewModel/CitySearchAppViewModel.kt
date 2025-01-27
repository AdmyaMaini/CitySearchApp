package com.example.citysearchapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.citysearchapp.CitySearchApplication
import com.example.citysearchapp.data.CitySearchAppRepository
import com.example.citysearchapp.model.Location
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.launch

sealed interface CitySearchAppUiState{
    data object Loading : CitySearchAppUiState
    data class Success (
        val locations : List<Location>,
        val boundingBox: LatLngBounds? = null
    ) : CitySearchAppUiState
    data object Error : CitySearchAppUiState
    data object Rest : CitySearchAppUiState
    data class NoResult (
        val message: String
    ) : CitySearchAppUiState
}
enum class ScreenMode{
    ListMode,MapMode
}
class CitySearchAppViewModel(private val citySearchAppRepository: CitySearchAppRepository ) : ViewModel() {

    var uiState : CitySearchAppUiState by mutableStateOf(CitySearchAppUiState.Rest)
    private set
    var screenMode : ScreenMode by mutableStateOf(ScreenMode.ListMode)
    private set
    var searchInput: String by mutableStateOf("")
    private set
    fun updateSearchInput(inputValue: String){
        searchInput = inputValue
    }
    private fun makeSearchInputEmpty(){
        searchInput = ""
    }
    fun toggleScreenMode(){
        screenMode = if(screenMode == ScreenMode.ListMode) ScreenMode.MapMode else ScreenMode.ListMode
    }
    fun getSearchResult(nameStartsWith : String){
        if(nameStartsWith.isEmpty())
        {
            uiState = CitySearchAppUiState.NoResult("Search input is empty !")
            return
        }
        viewModelScope.launch {
            try{
                uiState = CitySearchAppUiState.Loading
                val result :List<Location> = citySearchAppRepository.getCitiesDetails(nameStartsWith = nameStartsWith, maxRows = 10, username ="keep_truckin").locations
                if(result.isEmpty()){
                    uiState = CitySearchAppUiState.NoResult("No cities found !")
                }
                else
                {
                    val latLanBuilder = createLatLngBounds(result)
                    uiState = CitySearchAppUiState.Success(result, latLanBuilder)
                }
            }catch (e: Exception) {
                uiState = CitySearchAppUiState.Error
            }
        }
    }
    fun createLatLngBounds(points: List<Location>): LatLngBounds? {
        return try {
            val builder = LatLngBounds.Builder()
            points.forEach { point ->
                builder.include(point.latLng)
            }
            builder.build()
        } catch (e: Exception) {
            return null
        }
    }
   fun backToHomeScreen(){
       uiState = CitySearchAppUiState.Rest
       makeSearchInputEmpty()
   }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CitySearchApplication)
                val citySearchAppRepository : CitySearchAppRepository = application.container.citySearchAppRepository
                CitySearchAppViewModel(citySearchAppRepository)

            }
        }
    }
}