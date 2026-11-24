package com.example.airquality

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object AirQualityApi {

    suspend fun fetchUsAqi(latitude: Double, longitude: Double): Int =
        withContext(Dispatchers.IO) {
            val url = URL(
                "https://air-quality-api.open-meteo.com/v1/air-quality" +
                        "?latitude=$latitude&longitude=$longitude" +
                        "&current=us_aqi&timezone=auto"
            )
            val connection = url.openConnection() as HttpURLConnection
            try {
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                val body = connection.inputStream.bufferedReader().use { it.readText() }
                JSONObject(body).getJSONObject("current").getInt("us_aqi")
            } finally {
                connection.disconnect()
            }
        }
}