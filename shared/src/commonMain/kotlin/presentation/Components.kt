package presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrightnessHigh
import androidx.compose.material.icons.rounded.BrightnessLow
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.rounded.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ui.DesignSystem

@Composable
fun MainWeatherCard(
    degree: String,
    condition: String,
    location: String,
    highLow: String,
    realFeel: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DesignSystem.Padding.Medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(DesignSystem.Padding.ExtraSmall)
            ) {
                // Location
                Text(
                    text = location,
                    style = MaterialTheme.typography.headlineSmall
                )
                // Condition
                Text(
                    text = condition,
                    style = MaterialTheme.typography.bodyMedium
                )
                // Maximum and Minimum temperatures
                Text(
                    text = highLow,
                    style = MaterialTheme.typography.bodySmall
                )
                // Real feel temperature
                Text(
                    text = "Real feel: $realFeel",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                // Ideally, you'd use an icon that represents the weather condition.
                // https://cdn.weatherapi.com/weather/64x64/day/296.png
                // TODO: Logic to convert image url from api to usable data
                KamelImage(
                    resource = asyncPainterResource(data = "https://cdn.weatherapi.com/weather/64x64/day/296.png"),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(DesignSystem.Size.ExtraLarge)
                )
                Spacer(
                    modifier = Modifier.height(DesignSystem.Size.Small)
                )
                Text(
                    text = degree,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

// TODO: TESTING
@Composable
fun ForecastCard(
    forecasts: List<DayForecast> // A list of DayForecast data class instances (to be defined below)
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DesignSystem.Padding.Medium),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            forecasts.forEach { forecast ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DesignSystem.Padding.Small),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Date Segment
                    Box(modifier = Modifier.weight(2f)) {
                        Text(
                            text = forecast.date,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Start
                        )
                    }

                    // Rain Chance Segment
                    Box(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(DesignSystem.Padding.ExtraSmall)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.WaterDrop,
                                contentDescription = "Chance of rain",
                                modifier = Modifier.size(DesignSystem.Size.Medium)
                            )
                            Text(
                                text = "${forecast.rainChance}%",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Condition Image Segment
                    Box(modifier = Modifier.weight(1f)) {
                        KamelImage(
                            resource = asyncPainterResource(forecast.conditionImageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(DesignSystem.Size.Large)
                        )
                    }

                    // Max Temperature Segment
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${forecast.tempMax}°/${forecast.tempMin}°",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

data class DayForecast(
    val date: String,
    val rainChance: Int,
    val conditionImageUrl: String,
    val tempMax: Int,
    val tempMin: Int
)

@Composable
fun HourlyWeatherCard(hours: List<HourlyForecast>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(DesignSystem.Padding.Small)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            hours.forEach { hourForecast ->
                Column(
                    modifier = Modifier
                        .width(80.dp) // fixed width for each hour
                        .padding(DesignSystem.Padding.Small)
                        .align(Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(DesignSystem.Size.Small)
                ) {
                    Text(
                        text = hourForecast.time,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    KamelImage(
                        resource = asyncPainterResource(hourForecast.iconUrl),
                        contentDescription = null,
                        modifier = Modifier.size(DesignSystem.Size.Large)
                    )
                    Text(
                        text = "${hourForecast.temperature}°",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.WaterDrop, // You can change to appropriate rain icon
                            contentDescription = "Rain drop",
                            modifier = Modifier.size(DesignSystem.Size.Medium)
                        )
                        Text(
                            text = "${hourForecast.rainChance}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

data class HourlyForecast(
    val time: String,
    val iconUrl: String,
    val temperature: Int,
    val rainChance: Int
)

// TODO REMOVE
@Composable
fun EnvironmentalFactorsCard(
    uvIndex: Int,
    humidity: Int,
    windSpeed: Int,
    sunrise: String,
    sunset: String
) {
    val factors = listOf(
        EnvironmentalFactor(value = uvIndex.toString(), label = "UV Index", icon = Icons.Rounded.WbSunny),
        EnvironmentalFactor(value = "$humidity%", label = "Humidity", icon = Icons.Rounded.WaterDrop),
        EnvironmentalFactor(value = "${windSpeed}km/h", label = "Wind", icon = Icons.Rounded.WindPower),
        EnvironmentalFactor(value = sunrise, label = "Sunrise", icon = Icons.Rounded.BrightnessHigh),
        EnvironmentalFactor(value = sunset, label = "Sunset", icon = Icons.Rounded.BrightnessLow),
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DesignSystem.Padding.Medium),
            contentAlignment = Alignment.Center
        ) {
            // Divide factors into groups of 2
            val rows: List<List<EnvironmentalFactor>> = factors.chunked(2)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                rows.forEach { rowFactors ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowFactors.forEach { factor ->
                            EnvironmentalFactorItem(
                                value = factor.value,
                                label = factor.label,
                                icon = factor.icon
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = DesignSystem.Padding.Small)
                    )
                }
            }
        }
    }
}

@Composable
fun EnvironmentalFactorItem(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier
                    .size(DesignSystem.Size.ExtraLarge)
                    .padding(end = DesignSystem.Padding.Small)
            )
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = DesignSystem.Padding.Small)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = DesignSystem.Padding.Small)
                )
            }
        }
    }
}

data class EnvironmentalFactor(val value: String, val label: String, val icon: ImageVector)