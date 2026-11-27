package com.example.airquality

import androidx.compose.ui.graphics.Color

data class AqiStatus(val label: String, val color: Color)

fun statusForAqi(aqi: Int): AqiStatus = when {
    aqi <= 50 -> AqiStatus("Good", Color(0xFF2E7D32))
    aqi <= 100 -> AqiStatus("Moderate", Color(0xFFF9A825))
    aqi <= 150 -> AqiStatus("Unhealthy for Sensitive Groups", Color(0xFFEF6C00))
    aqi <= 200 -> AqiStatus("Unhealthy", Color(0xFFC62828))
    aqi <= 300 -> AqiStatus("Very Unhealthy", Color(0xFF6A1B9A))
    else -> AqiStatus("Hazardous", Color(0xFF4E342E))
}