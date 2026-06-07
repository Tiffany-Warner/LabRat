package com.tiffles.labrat.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.tiffles.labrat.ui.addresult.AddLabResultScreen
import com.tiffles.labrat.ui.addresult.AddLabResultViewModel
import com.tiffles.labrat.ui.addresult.BiomarkerPickerScreen
import com.tiffles.labrat.ui.biomarkers.BiomarkersScreen
import com.tiffles.labrat.ui.dashboard.DashboardScreen
import com.tiffles.labrat.ui.history.HistoryScreen
import com.tiffles.labrat.ui.settings.SettingsScreen

@Composable
fun LabRatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Dashboard.route,
        modifier = modifier
    ) {
        composable(Routes.Dashboard.route) { DashboardScreen() }
        composable(Routes.Biomarkers.route) { BiomarkersScreen() }
        composable(Routes.History.route) { HistoryScreen() }
        composable(Routes.Settings.route) { SettingsScreen() }
        composable(NavRoutes.AddLabResult.route) { entry ->
            val viewModel: AddLabResultViewModel = hiltViewModel(entry)
            AddLabResultScreen(
                onNavigateUp = { navController.navigateUp() },
                onAddValues = { navController.navigate(NavRoutes.BiomarkerPicker.route) },
                viewModel = viewModel,
            )
        }
        composable(NavRoutes.BiomarkerPicker.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(NavRoutes.AddLabResult.route)
            }
            BiomarkerPickerScreen(
                onNavigateUp = { navController.navigateUp() },
                viewModel = hiltViewModel(parentEntry),
            )
        }
    }
}

@Composable
fun LabRatBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        Routes.tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}