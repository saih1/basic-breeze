package common

import data.local.model.DayForecast
import data.local.model.HourlyForecast
import data.local.model.WeatherEntity
import data.remote.model.Forecastday
import data.remote.model.Hour
import data.remote.model.WeatherResponse
import kotlinx.datetime.LocalDate
import kotlin.math.roundToInt

fun WeatherResponse.toWeatherEntity(): WeatherEntity = WeatherEntity(
    degree = "${current.tempC.roundToInt()}°",
    condition = current.condition.text,
    location = "${location.name}, ${location.country}",
    highLow = buildString {
        append(forecast.forecastday.first().day.maxtempC.roundToInt())
        append("° /")
        append(forecast.forecastday.first().day.mintempC.roundToInt())
        append("°")
    },
    realFeel = "${current.feelslikeC.roundToInt()}°",
    iconUrl = "https:${current.condition.icon}",
    dayForecasts = forecast.forecastday.map(Forecastday::toDayForecast),
    hourlyForecast = forecast.forecastday.first().hour.map(Hour::toHourlyForecast),
    uvIndex = current.uv.toString(),
    wind = "${current.windKph}km/h",
    humidity = "${current.humidity}%",
    sunset = forecast.forecastday.first().astro.sunset,
    sunrise = forecast.forecastday.first().astro.sunrise
)

fun Hour.toHourlyForecast(): HourlyForecast = HourlyForecast(
    time = time.to12HourFormat() ?: "-",
    iconUrl = "https:${condition.icon}",
    temperature = "${tempC.roundToInt()}°",
    rainChance = "$chanceOfRain%"
)

fun Forecastday.toDayForecast(): DayForecast = DayForecast(
    date = date.toDayOfWeek() ?: "-",
    rainChance = "${day.dailyChanceOfRain}%",
    conditionImageUrl = "https:${day.condition.icon}",
    tempMax = "${day.maxtempC.roundToInt()}°",
    tempMin = "${day.mintempC.roundToInt()}°"
)

fun String.toDayOfWeek(): String? = try {
    val localDate = LocalDate.parse(this)
    localDate.dayOfWeek.name
        .lowercase()
        .replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase()
            else
                it.toString()
        }
} catch (e: Exception) {
    null
}

fun String.to12HourFormat(): String? = try {
    val parts = this.split(" ")[1].split(":")
    val hour = parts[0].toInt()
    val period = if (hour < 12) "am" else "pm"
    val formattedHour =
        if (hour == 0 || hour == 12) 12
        else hour % 12
    "$formattedHour $period"
} catch (e: Exception) {
    null
}