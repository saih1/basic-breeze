import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.RequestState
import common.Status.*
import data.remote.model.WeatherResponse
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.NetworkModule
import presentation.WeatherViewModel

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

    MaterialTheme {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            when (result.status) {
                SUCCESS -> {
                    Text(text = "City -> ${result.data?.location?.name ?: "null"}")
                    Text(text = "Condition -> ${result.data?.current?.condition?.text ?: "null"}")
                    Text(text = "Temperature -> ${result.data?.current?.tempC.toString()}")
                }
                ERROR -> Text(text = "ERROR")
                IDLE -> Text(text = "IDLE")
                LOADING -> Text(text = "LOADING")
            }

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
        }
    }
}

expect fun getPlatformName(): String