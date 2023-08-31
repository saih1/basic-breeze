package data.repository

import common.toWeatherEntity
import data.local.model.WeatherEntity
import data.remote.api.WeatherApiService
import data.remote.model.WeatherQuery

interface WeatherRepository {
    suspend fun getWeather(query: WeatherQuery): WeatherEntity
}

class WeatherRepositoryImpl(private val apiService: WeatherApiService) : WeatherRepository {
    override suspend fun getWeather(query: WeatherQuery): WeatherEntity {
        return apiService.fetchWeather(query = query, days = 10).toWeatherEntity()
    }
}