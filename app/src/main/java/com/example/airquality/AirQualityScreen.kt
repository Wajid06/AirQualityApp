package com.example.airquality

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airquality.AirQualityApi
import com.example.airquality.statusForAqi
import com.example.airquality.ui.theme.AirQualityTheme
import kotlinx.coroutines.launch

private const val LATITUDE = 57.149651
private const val LONGITUDE = -2.099075

@Composable
fun AirQualityScreen(
    modifier: Modifier = Modifier,
    onViewCities: () -> Unit = {}
) {
    var aqi by rememberSaveable { mutableStateOf<Int?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun refresh() {
        scope.launch {
            loading = true
            error = null
            try {
                aqi = AirQualityApi.fetchUsAqi(LATITUDE, LONGITUDE)
            } catch (e: Exception) {
                Log.e("AirQuality", "Fetch failed", e)
                error = "Couldn't load air quality. Check your connection and try again."
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        if (aqi == null) refresh()
    }

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

        val current = aqi
        if (current != null) {
            val status = statusForAqi(current)
            Text(
                text = current.toString(),
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                color = status.color
            )
            Text(
                text = status.label,
                style = MaterialTheme.typography.headlineMedium,
                color = status.color,
                textAlign = TextAlign.Center
            )
        } else if (loading) {
            CircularProgressIndicator()
        }

        error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { refresh() }, enabled = !loading) {
            Text(if (loading) "Loading..." else "Refresh")
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = onViewCities) {
            Text("Compare cities")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Data: Open-Meteo / CAMS",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirQualityScreenPreview() {
    AirQualityTheme {
        AirQualityScreen()
    }
}