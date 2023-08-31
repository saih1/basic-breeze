package presentation

import common.RequestState
import data.local.model.WeatherEntity
import data.remote.model.WeatherQuery
import data.repository.WeatherRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weather: MutableStateFlow<RequestState<WeatherEntity>> =
        MutableStateFlow(RequestState.idle())
    val weather: StateFlow<RequestState<WeatherEntity>> = _weather.asStateFlow()

    // TODO: Remove this after testing, only for testing
    fun getWeather(city: String) {
        viewModelScope.launch {
            _weather.value = RequestState.loading()
            try {
                val result = repository.getWeather(WeatherQuery.City(city))
                _weather.value = RequestState.success(data = result)
            } catch (e: Exception) {
                _weather.value = RequestState.error(e)
            }
        }
    }
}