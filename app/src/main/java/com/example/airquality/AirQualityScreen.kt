package com.example.airquality

import androidx.compose.ui.graphics.Color

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