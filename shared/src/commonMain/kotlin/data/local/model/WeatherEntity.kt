package data.local.model

data class WeatherEntity(
    var degree: String,
    var condition: String,
    var location: String,
    var highLow: String,
    var realFeel: String,
    var iconUrl: String,
    var dayForecasts: List<DayForecast>,
    var hourlyForecast: List<HourlyForecast>,
    var uvIndex: String,
    var wind: String,
    var humidity: String,
    var sunset: String,
    var sunrise: String
)

data class DayForecast(
    val date: String,
    val rainChance: String,
    val conditionImageUrl: String,
    val tempMax: String,
    val tempMin: String
)

data class HourlyForecast(
    val time: String,
    val iconUrl: String,
    val temperature: String,
    val rainChance: String
)