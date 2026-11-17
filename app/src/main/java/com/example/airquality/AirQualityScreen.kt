package com.example.airquality

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airquality.ui.theme.AirQualityTheme

data class AirQualityReading(
    val aqi: Int,
    val status: String,
    val color: Color
)

val dummyReadings = listOf(
    AirQualityReading(42, "Good", Color(0xFF2E7D32)),
    AirQualityReading(87, "Moderate", Color(0xFFF9A825)),
    AirQualityReading(156, "Unhealthy", Color(0xFFC62828))
)

@Composable
fun AirQualityScreen(modifier: Modifier = Modifier) {
    var index by remember { mutableIntStateOf(0) }
    val reading = dummyReadings[index]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Air Quality Index",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = reading.aqi.toString(),
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            color = reading.color
        )
        Text(
            text = reading.status,
            style = MaterialTheme.typography.headlineMedium,
            color = reading.color
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { index = (index + 1) % dummyReadings.size }) {
            Text("Refresh")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirQualityScreenPreview() {
    AirQualityTheme {
        AirQualityScreen()
    }
}