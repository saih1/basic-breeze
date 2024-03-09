package data.remote.api

import data.remote.model.WeatherQuery
import data.remote.model.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val BASE_URL = "https://api.weatherapi.com/v1/forecast.json"

interface WeatherApiService {
    suspend fun fetchWeather(query: WeatherQuery, days: Int): WeatherResponse
}

class WeatherApi(private val apiKey: String) : WeatherApiService {
    private val customJson: Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val httpClient = HttpClient {
        install(plugin = ContentNegotiation) {
            json(json = customJson)
        }
    }

    override suspend fun fetchWeather(query: WeatherQuery, days: Int): WeatherResponse {
        val urlString: String =  when (query) {
            is WeatherQuery.City -> BASE_URL +
                    "?key=$apiKey" +
                    "&q=${query.city}" +
                    "&days=$days" +
                    "&aqi=no" +
                    "&alerts=no"
            is WeatherQuery.LatLong -> BASE_URL +
                    "?key=$apiKey" +
                    "&q=${query.latitude},${query.longitude}" +
                    "&days=$days" +
                    "&aqi=no" +
                    "&alerts=no"
        }
        return httpClient
            .get(urlString = urlString)
            .body<WeatherResponse>()
    }
}

