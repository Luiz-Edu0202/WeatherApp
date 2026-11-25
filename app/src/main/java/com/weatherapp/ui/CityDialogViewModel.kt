package com.weatherapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.weatherapp.api.WeatherService
import com.weatherapp.model.City
import androidx.lifecycle.ViewModelProvider

class CityDialogViewModel( private val service: WeatherService) : ViewModel() {
    var query by mutableStateOf("")
    var suggestions by mutableStateOf<List<City>>(emptyList())
        private set
    fun onQueryChanged(value: String) {
        query = value
        if (value.length < 3) {
            suggestions = emptyList()
        } else {
            service.cancelAll()
            service.search(query) { res ->
                val cities = res?.map {
                    City(it.name!!, location = LatLng(it.lat!!, it.lon!!))
                }
                cities?.let { suggestions = cities.toList() }
            }
        }
    }
}



class CityDialogViewModelFactory(private val service: WeatherService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityDialogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityDialogViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
