import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.RequestState
import common.Status.ERROR
import common.Status.IDLE
import common.Status.LOADING
import common.Status.SUCCESS
import data.remote.model.WeatherResponse
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.NetworkModule
import presentation.DayForecast
import presentation.EnvironmentalFactorsCard
import presentation.ForecastCard
import presentation.HourlyForecast
import presentation.HourlyWeatherCard
import presentation.MainWeatherCard
import presentation.WeatherViewModel
import ui.DesignSystem
import ui.theme.BasicBreezeTheme

@Composable
fun App() {
    val viewModel: WeatherViewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory {
            WeatherViewModel(
                repository = NetworkModule.weatherRepository
            )
        }
    )

    val result: RequestState<WeatherResponse> by viewModel.weather.collectAsState()

    val dummyForecasts = listOf(
        DayForecast("Monday", 60, "https://cdn.weatherapi.com/weather/64x64/day/296.png", 25, 15),
        DayForecast("Tuesday", 20, "https://cdn.weatherapi.com/weather/64x64/day/116.png", 27, 16),
        DayForecast("Wednesday", 10, "https://cdn.weatherapi.com/weather/64x64/day/113.png", 26, 14),
        DayForecast("Thursday", 50, "https://cdn.weatherapi.com/weather/64x64/night/119.png", 20, 12),
        DayForecast("Friday", 70, "https://cdn.weatherapi.com/weather/64x64/night/302.png", 22, 13),
        DayForecast("Saturday", 40, "https://cdn.weatherapi.com/weather/64x64/day/176.png", 23, 14),
        DayForecast("Sunday", 30, "https://cdn.weatherapi.com/weather/64x64/day/122.png", 24, 15)
    )

    BasicBreezeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DesignSystem.Padding.Large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(DesignSystem.Padding.Medium),
            ) {
                item {
                    Button(onClick = {
                        viewModel.getWeather("Sydney, Australia")
                    }) {
                        Text("Sydney")
                    }
                }

                when (result.status) {
                    SUCCESS -> {
                        item {
                            MainWeatherCard(
                                degree = result.data?.current?.tempC.toString(),
                                condition = result.data?.current?.condition?.text.toString(),
                                location = result.data?.location?.name.toString() + ", " + result.data?.location?.country.toString(),
                                highLow = result.data?.forecast?.forecastday?.first()?.day?.maxtempC.toString(),
                                realFeel = result.data?.current?.feelslikeC.toString()
                            )
                        }

                        item {
                            ForecastCard(forecasts = dummyForecasts)
                        }

                        item {
                            val dummyData = listOf(
                                HourlyForecast("01:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 22, 5),
                                HourlyForecast("02:00", "https://cdn.weatherapi.com/weather/64x64/day/113.png", 21, 0),
                                HourlyForecast("03:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 20, 10),
                                HourlyForecast("04:00", "https://cdn.weatherapi.com/weather/64x64/day/119.png", 20, 15),
                                HourlyForecast("05:00", "https://cdn.weatherapi.com/weather/64x64/day/122.png", 19, 20),
                                HourlyForecast("06:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 18, 5),
                                HourlyForecast("07:00", "https://cdn.weatherapi.com/weather/64x64/day/113.png", 18, 0),
                                HourlyForecast("08:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 19, 5),
                                HourlyForecast("09:00", "https://cdn.weatherapi.com/weather/64x64/day/119.png", 21, 10),
                                HourlyForecast("10:00", "https://cdn.weatherapi.com/weather/64x64/day/122.png", 23, 0),
                                HourlyForecast("11:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 24, 5),
                                HourlyForecast("12:00", "https://cdn.weatherapi.com/weather/64x64/day/113.png", 25, 0),
                                HourlyForecast("13:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 26, 10),
                                HourlyForecast("14:00", "https://cdn.weatherapi.com/weather/64x64/day/119.png", 26, 15),
                                HourlyForecast("15:00", "https://cdn.weatherapi.com/weather/64x64/day/122.png", 25, 20),
                                HourlyForecast("16:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 24, 5),
                                HourlyForecast("17:00", "https://cdn.weatherapi.com/weather/64x64/day/113.png", 23, 0),
                                HourlyForecast("18:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 21, 5),
                                HourlyForecast("19:00", "https://cdn.weatherapi.com/weather/64x64/day/119.png", 20, 10),
                                HourlyForecast("20:00", "https://cdn.weatherapi.com/weather/64x64/day/122.png", 19, 0),
                                HourlyForecast("21:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 18, 5),
                                HourlyForecast("22:00", "https://cdn.weatherapi.com/weather/64x64/day/113.png", 17, 0),
                                HourlyForecast("23:00", "https://cdn.weatherapi.com/weather/64x64/day/116.png", 16, 10),
                                HourlyForecast("24:00", "https://cdn.weatherapi.com/weather/64x64/day/119.png", 15, 15)
                            )

                            HourlyWeatherCard(hours = dummyData)
                        }

                        item {
                            EnvironmentalFactorsCard(
                                uvIndex = 5,
                                humidity = 72,
                                windSpeed = 15,
                                sunrise = "6:15 AM",
                                sunset = "7:45 PM"
                            )
                        }
                    }
                    ERROR -> item {Text(text = "❌") }
                    IDLE -> item {Text(text = "🥱") }
                    LOADING -> item {Text(text = "🍭🍭🍭")}
                }
            }
        }
    }
}

expect fun getPlatformName(): String