package com.example.airquality

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@Composable
fun CityListScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    var readings by remember { mutableStateOf<Map<String, Int?>>(emptyMap()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        readings = cities
            .map { city ->
                async {
                    city.name to try {
                        AirQualityApi.fetchUsAqi(city.latitude, city.longitude)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            .awaitAll()
            .toMap()
        loading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Cities", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cities) { city ->
                CityRow(name = city.name, aqi = readings[city.name], loading = loading)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
private fun CityRow(name: String, aqi: Int?, loading: Boolean) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            if (aqi != null) {
                val status = statusForAqi(aqi)
                Text(
                    text = "$aqi · ${status.label}",
                    color = status.color,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(if (loading) "Loading..." else "Unavailable")
            }
        }
    }
}
