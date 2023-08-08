package presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.DesignSystem

@Composable
fun WeatherCard(
    degree: String,
    condition: String,
    location: String,
    highLow: String,
    realFeel: String
) {
    Card(modifier = Modifier.fillMaxWidth(),) {
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
                // TODO: Fix this
                Text(text = location, style = MaterialTheme.typography.headlineSmall)
                Text(text = condition, style = MaterialTheme.typography.bodyMedium)
                Text(text = highLow, style = MaterialTheme.typography.bodySmall)
                Text(text = "Real feel: $realFeel", style = MaterialTheme.typography.bodySmall)
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                // Ideally, you'd use an icon that represents the weather condition.
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = degree, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}