package com.tiffles.labrat.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabRoutes(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Dashboard : TabRoutes("dashboard", "Dashboard", Icons.Filled.Home)
    data object Biomarkers : TabRoutes("biomarkers", "Biomarkers", Icons.Filled.Science)
    data object History : TabRoutes("history", "History", Icons.Filled.History)
    data object Settings : TabRoutes("settings", "Settings", Icons.Filled.Settings)

    companion object {
        val tabs by lazy { listOf(Dashboard, Biomarkers, History, Settings) }
    }
}

sealed class FullScreenRoutes(val route: String) {
    data object AddLabResult : FullScreenRoutes("add_lab_result")
    data object BiomarkerPicker : FullScreenRoutes("biomarker_picker")
}