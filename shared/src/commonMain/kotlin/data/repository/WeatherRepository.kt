package data.repository

import data.remote.api.WeatherApiService
import data.remote.model.WeatherQuery
import data.remote.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(query: WeatherQuery): WeatherResponse
}

class WeatherRepositoryImpl(private val apiService: WeatherApiService) : WeatherRepository {
    override suspend fun getWeather(query: WeatherQuery): WeatherResponse {
        return apiService.fetchWeather(query = query, days = 2)
    }
}