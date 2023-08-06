package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current") var current: Current,
    @SerialName("forecast") var forecast: Forecast,
    @SerialName("location") var location: Location
)

@Serializable
data class Current(
    @SerialName("cloud") var cloud: Int,
    @SerialName("condition") var condition: Condition,
    @SerialName("feelslike_c") var feelslikeC: Double,
    @SerialName("feelslike_f") var feelslikeF: Double,
    @SerialName("gust_kph") var gustKph: Double,
    @SerialName("gust_mph") var gustMph: Double,
    @SerialName("humidity") var humidity: Int,
    @SerialName("is_day") var isDay: Int,
    @SerialName("last_updated") var lastUpdated: String,
    @SerialName("last_updated_epoch") var lastUpdatedEpoch: Int,
    @SerialName("precip_in") var precipIn: Double,
    @SerialName("precip_mm") var precipMm: Double,
    @SerialName("pressure_in") var pressureIn: Double,
    @SerialName("pressure_mb") var pressureMb: Double,
    @SerialName("temp_c") var tempC: Double,
    @SerialName("temp_f") var tempF: Double,
    @SerialName("uv") var uv: Double,
    @SerialName("vis_km") var visKm: Double,
    @SerialName("vis_miles") var visMiles: Double,
    @SerialName("wind_degree") var windDegree: Int,
    @SerialName("wind_dir") var windDir: String,
    @SerialName("wind_kph") var windKph: Double,
    @SerialName("wind_mph") var windMph: Double
)

@Serializable
data class Forecast(
    @SerialName("forecastday") var forecastday: List<Forecastday>
)

@Serializable
data class Location(
    @SerialName("country") var country: String,
    @SerialName("lat") var lat: Double,
    @SerialName("localtime") var localtime: String,
    @SerialName("localtime_epoch") var localtimeEpoch: Int,
    @SerialName("lon") var lon: Double,
    @SerialName("name") var name: String,
    @SerialName("region") var region: String,
    @SerialName("tz_id") var tzId: String
)

@Serializable
data class Forecastday(
    @SerialName("astro") var astro: Astro,
    @SerialName("date") var date: String,
    @SerialName("date_epoch") var dateEpoch: Int,
    @SerialName("day") var day: Day,
    @SerialName("hour") var hour: List<Hour>
)

@Serializable
data class Astro(
    @SerialName("is_moon_up") var isMoonUp: Int,
    @SerialName("is_sun_up") var isSunUp: Int,
    @SerialName("moon_illumination") var moonIllumination: String,
    @SerialName("moon_phase") var moonPhase: String,
    @SerialName("moonrise") var moonrise: String,
    @SerialName("moonset") var moonset: String,
    @SerialName("sunrise") var sunrise: String,
    @SerialName("sunset") var sunset: String
)

@Serializable
data class Hour(
    @SerialName("chance_of_rain") var chanceOfRain: Int,
    @SerialName("chance_of_snow") var chanceOfSnow: Int,
    @SerialName("cloud") var cloud: Int,
    @SerialName("condition") var condition: Condition,
    @SerialName("dewpoint_c") var dewpointC: Double,
    @SerialName("dewpoint_f") var dewpointF: Double,
    @SerialName("feelslike_c") var feelslikeC: Double,
    @SerialName("feelslike_f") var feelslikeF: Double,
    @SerialName("gust_kph") var gustKph: Double,
    @SerialName("gust_mph") var gustMph: Double,
    @SerialName("heatindex_c") var heatindexC: Double,
    @SerialName("heatindex_f") var heatindexF: Double,
    @SerialName("humidity") var humidity: Int,
    @SerialName("is_day") var isDay: Int,
    @SerialName("precip_in") var precipIn: Double,
    @SerialName("precip_mm") var precipMm: Double,
    @SerialName("pressure_in") var pressureIn: Double,
    @SerialName("pressure_mb") var pressureMb: Double,
    @SerialName("temp_c") var tempC: Double,
    @SerialName("temp_f") var tempF: Double,
    @SerialName("time") var time: String,
    @SerialName("time_epoch") var timeEpoch: Int,
    @SerialName("uv") var uv: Double,
    @SerialName("vis_km") var visKm: Double,
    @SerialName("vis_miles") var visMiles: Double,
    @SerialName("will_it_rain") var willItRain: Int,
    @SerialName("will_it_snow") var willItSnow: Int,
    @SerialName("wind_degree") var windDegree: Int,
    @SerialName("wind_dir") var windDir: String,
    @SerialName("wind_kph") var windKph: Double,
    @SerialName("wind_mph") var windMph: Double,
    @SerialName("windchill_c") var windchillC: Double,
    @SerialName("windchill_f") var windchillF: Double
)

@Serializable
data class Day(
    @SerialName("avghumidity") var avghumidity: Double,
    @SerialName("avgtemp_c") var avgtempC: Double,
    @SerialName("avgtemp_f") var avgtempF: Double,
    @SerialName("avgvis_km") var avgvisKm: Double,
    @SerialName("avgvis_miles") var avgvisMiles: Double,
    @SerialName("condition") var condition: Condition,
    @SerialName("daily_chance_of_rain") var dailyChanceOfRain: Int,
    @SerialName("daily_chance_of_snow") var dailyChanceOfSnow: Int,
    @SerialName("daily_will_it_rain") var dailyWillItRain: Int,
    @SerialName("daily_will_it_snow") var dailyWillItSnow: Int,
    @SerialName("maxtemp_c") var maxtempC: Double,
    @SerialName("maxtemp_f") var maxtempF: Double,
    @SerialName("maxwind_kph") var maxwindKph: Double,
    @SerialName("maxwind_mph") var maxwindMph: Double,
    @SerialName("mintemp_c") var mintempC: Double,
    @SerialName("mintemp_f") var mintempF: Double,
    @SerialName("totalprecip_in") var totalprecipIn: Double,
    @SerialName("totalprecip_mm") var totalprecipMm: Double,
    @SerialName("totalsnow_cm") var totalsnowCm: Double,
    @SerialName("uv") var uv: Double
)

@Serializable
data class Condition(
    @SerialName("code") var code: Int,
    @SerialName("icon") var icon: String,
    @SerialName("text") var text: String
)