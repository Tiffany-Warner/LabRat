package com.tiffles.labrat.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Dashboard : Routes("dashboard", "Dashboard", Icons.Filled.Home)
    data object Biomarkers : Routes("biomarkers", "Biomarkers", Icons.Filled.Science)
    data object History : Routes("history", "History", Icons.Filled.History)
    data object Settings : Routes("settings", "Settings", Icons.Filled.Settings)

    companion object {
        val tabs by lazy { listOf(Dashboard, Biomarkers, History, Settings) }
    }
}

sealed class NavRoutes(val route: String) {
    data object AddLabResult : NavRoutes("add_lab_result")
}