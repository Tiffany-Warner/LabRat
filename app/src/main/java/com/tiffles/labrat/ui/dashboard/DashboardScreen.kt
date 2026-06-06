package com.tiffles.labrat.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tiffles.labrat.ui.theme.LabRatTheme

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Dashboard")
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    LabRatTheme { DashboardScreen() }
}