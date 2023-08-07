import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

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
        Button(onClick = viewModel::getLocation) {
            Text(text = "Get current location")
        }
        Text(
            text = text,
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

internal class GeoViewModel(
    val locationTracker: LocationTracker
) : ViewModel() {
    private val _result: MutableStateFlow<String> = MutableStateFlow("press button")
    val result: StateFlow<String> = _result.asStateFlow()

    fun getLocation() {
        viewModelScope.launch {
            // Stop tracking after 10 seconds
            withTimeout(10000) {
                try {
                    locationTracker.startTracking()
                    locationTracker.getLocationsFlow()
                        .take(5) // only take 5 emissions
                        .onEach { _result.value = it.toString() }
                        .onCompletion { locationTracker.stopTracking() }
                        .collect()
                } catch (e: Exception) {
                    _result.value = e.toString()
                    locationTracker.stopTracking()
                }
            }
        }
    }
}