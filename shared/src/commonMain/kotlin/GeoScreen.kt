import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.LocationTrackerFactory
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// TODO: TO BE REMOVED AFTER TESTING

@Composable
internal fun GeoScreen() {
    val locationTrackerFactory: LocationTrackerFactory = rememberLocationTrackerFactory(
        accuracy = LocationTrackerAccuracy.Best
    )

    GeoScreen(
        viewModel = getViewModel(
            key = "geo-screen",
            factory = viewModelFactory {
                GeoViewModel(
                    locationTracker = locationTrackerFactory.createLocationTracker()
                )
            }
        )
    )
}

@Composable
private fun GeoScreen(
    viewModel: GeoViewModel
) = Surface {
    BindLocationTrackerEffect(viewModel.locationTracker)

    val text: String by viewModel.result.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(onClick = viewModel::onStartClick) {
            Text(text = "Start")
        }

        Button(onClick = viewModel::onStopClick) {
            Text(text = "Stop")
        }

        Text(
            text = text,
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    }
}

internal class GeoViewModel(val locationTracker: LocationTracker) : ViewModel() {
    private val _result: MutableStateFlow<String> = MutableStateFlow("press button")
    val result: StateFlow<String> = _result.asStateFlow()

    init {
        locationTracker.getLocationsFlow()
            .onEach { _result.value = it.toString() }
            .launchIn(viewModelScope)
    }

    fun onStartClick() {
        viewModelScope.launch {
            try {
                locationTracker.startTracking()
            } catch (exc: Exception) {
                _result.value = exc.toString()
            }
        }
    }

    fun onStopClick() {
        locationTracker.stopTracking()
    }
}