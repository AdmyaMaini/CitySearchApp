package com.example.citysearchapp.ui.theme



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
import com.example.citysearchapp.fake.FakeDataSource
import com.example.citysearchapp.model.Location
import kotlinx.coroutines.launch

sealed interface CitySearchAppUiState{
    data object Loading : CitySearchAppUiState
    data class Success(val locations : List<Location>) : CitySearchAppUiState
    data class Error(val errorMessage: String?) : CitySearchAppUiState
    data object Rest : CitySearchAppUiState
}
class CitySearchAppViewModel(private val citySearchAppRepository: CitySearchAppRepository ) : ViewModel() {

    var uiState : CitySearchAppUiState by mutableStateOf(CitySearchAppUiState.Rest)
    private set

    fun getSearchResult(nameStartsWith : String){
//        viewModelScope.launch {
//            try{
//                uiState = CitySearchAppUiState.Loading
//                val result :List<Location> = citySearchAppRepository.getCitiesDetails(nameStartsWith = nameStartsWith, maxRows = 10, username ="keep_truckin").locations
//                uiState = CitySearchAppUiState.Success(result)
//            }catch (e: Exception) {
//                uiState = CitySearchAppUiState.Error(e.message)
//            }
//        }
        uiState = CitySearchAppUiState.Success(FakeDataSource.fakeDataList.locations)
    }

   fun backToHomeScreen(){
       uiState = CitySearchAppUiState.Rest
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