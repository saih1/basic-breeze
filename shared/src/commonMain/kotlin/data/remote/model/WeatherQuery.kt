package data.remote.model

sealed class WeatherQuery {
    data class City(val city: String): WeatherQuery()
    data class LatLong(val latitude: Double, val longitude: Double): WeatherQuery()
}
