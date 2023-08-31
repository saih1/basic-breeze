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
import data.local.model.WeatherEntity
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.NetworkModule
import presentation.EnvironmentalFactorsCard
import presentation.ForecastCard
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

    val result: RequestState<WeatherEntity> by viewModel.weather.collectAsState()

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
                // TODO: Remove this
                item {
                    Button(onClick = {
                        viewModel.getWeather("Sydney, Australia")
                    }) {
                        Text("Sydney")
                    }
                }

                when (result.status) {
                    SUCCESS -> {
                        with(result.data!!) {
                            item {
                                MainWeatherCard(
                                    degree = degree,
                                    condition = condition,
                                    location = location,
                                    highLow = highLow,
                                    realFeel = realFeel,
                                    iconUrl = iconUrl
                                )
                            }

                            item {
                                ForecastCard(forecasts = dayForecasts)
                            }

                            item {
                                HourlyWeatherCard(hours = hourlyForecast)
                            }

                            item {
                                EnvironmentalFactorsCard(
                                    uvIndex = uvIndex,
                                    humidity = humidity,
                                    windSpeed = wind,
                                    sunrise = sunrise,
                                    sunset = sunset
                                )
                            }
                        }
                    }

                    ERROR -> item { Text(text = "❌") }
                    IDLE -> item { Text(text = "🥱") }
                    LOADING -> item { Text(text = "🍭🍭🍭") }
                }
            }
        }
    }
}

expect fun getPlatformName(): String