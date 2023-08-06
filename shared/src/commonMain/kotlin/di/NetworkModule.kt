package di

import MyApplication.shared.BuildConfig
import data.remote.api.WeatherApi
import data.remote.api.WeatherApiService
import data.repository.WeatherRepository
import data.repository.WeatherRepositoryImpl

object NetworkModule {
    val apiService: WeatherApiService by lazy {
        WeatherApi(apiKey = BuildConfig.API_KEY)
    }
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(apiService = apiService)
    }
}