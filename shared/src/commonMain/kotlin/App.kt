import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import presentation.WeatherCard
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

    BasicBreezeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DesignSystem.Padding.Large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(DesignSystem.Padding.Medium),
            ) {
                Button(onClick = {
                    viewModel.getWeather("Sydney, Australia")
                }) {
                    Text("Sydney")
                }

                Button(onClick = {
                    viewModel.getWeather("Melbourne, Australia")
                }) {
                    Text("Melbourne")
                }
                when (result.status) {
                    SUCCESS -> {
                        WeatherCard(
                            degree = result.data?.current?.tempC.toString(),
                            condition = result.data?.current?.condition?.text.toString(),
                            location = result.data?.location?.name.toString() + ", " + result.data?.location?.country.toString(),
                            highLow = result.data?.forecast?.forecastday?.first()?.day?.maxtempC.toString(),
                            realFeel = result.data?.current?.feelslikeC.toString()
                        )
                    }
                    ERROR -> Text(text = "❌")
                    IDLE -> Text(text = "🥱")
                    LOADING -> Text(text = "🍭🍭🍭")
                }
            }
        }
    }
}

expect fun getPlatformName(): String