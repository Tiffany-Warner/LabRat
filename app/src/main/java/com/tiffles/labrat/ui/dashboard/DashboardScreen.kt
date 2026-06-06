package com.tiffles.labrat.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tiffles.labrat.ui.theme.LabRatTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    // TODO: remove once real Dashboard ViewModel is wired up
    LaunchedEffect(Unit) {
        viewModel.triggerDatabaseInit()
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Dashboard")
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    LabRatTheme { DashboardScreen() }
}