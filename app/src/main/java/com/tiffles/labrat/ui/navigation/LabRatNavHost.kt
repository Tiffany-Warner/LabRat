package com.tiffles.labrat.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tiffles.labrat.ui.addresult.AddLabResultScreen
import com.tiffles.labrat.ui.addresult.AddLabResultViewModel
import com.tiffles.labrat.ui.addresult.BiomarkerPickerScreen
import com.tiffles.labrat.ui.biomarkers.BiomarkersScreen
import com.tiffles.labrat.ui.biomarkers.BiomarkersViewModel
import com.tiffles.labrat.ui.dashboard.DashboardScreen
import com.tiffles.labrat.ui.dashboard.DashboardViewModel
import com.tiffles.labrat.ui.history.HistoryScreen
import com.tiffles.labrat.ui.settings.SettingsScreen

@Composable
fun LabRatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TabRoutes.Dashboard.route,
        modifier = modifier
    ) {
        composable(TabRoutes.Dashboard.route) {
            val viewModel: DashboardViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DashboardScreen(
                uiState = uiState,
                onNavigateToBiomarkers = {
                    navController.navigate(TabRoutes.Biomarkers.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable(TabRoutes.Biomarkers.route) {
            val viewModel: BiomarkersViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            BiomarkersScreen(
                uiState = uiState,
                onTogglePin = viewModel::togglePin,
            )
        }
        composable(TabRoutes.History.route) { HistoryScreen() }
        composable(TabRoutes.Settings.route) { SettingsScreen() }
        composable(FullScreenRoutes.AddLabResult.route) { entry ->
            val viewModel: AddLabResultViewModel = hiltViewModel(entry)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            LaunchedEffect(viewModel) {
                viewModel.navigateUp.collect { navController.navigateUp() }
            }
            AddLabResultScreen(
                uiState = uiState,
                onNavigateUp = { navController.navigateUp() },
                onAddValues = { navController.navigate(FullScreenRoutes.BiomarkerPicker.route) },
                onDateSelected = viewModel::updateDate,
                onLabNameChange = viewModel::updateLabName,
                onNotesChange = viewModel::updateNotes,
                onRemoveEntry = viewModel::removeEntry,
                onSave = viewModel::saveLabResult,
            )
        }
        composable(FullScreenRoutes.BiomarkerPicker.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(FullScreenRoutes.AddLabResult.route)
            }
            val viewModel: AddLabResultViewModel = hiltViewModel(parentEntry)
            val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
            val biomarkers by viewModel.filteredBiomarkers.collectAsStateWithLifecycle()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val addedIds = remember(uiState.pendingEntries) {
                uiState.pendingEntries.map { it.biomarkerId }.toSet()
            }
            LaunchedEffect(viewModel) {
                viewModel.entryAdded.collect { navController.navigateUp() }
            }
            BiomarkerPickerScreen(
                onNavigateUp = { navController.navigateUp() },
                searchQuery = searchQuery,
                biomarkers = biomarkers,
                addedIds = addedIds,
                onSearchQueryChange = viewModel::updateSearchQuery,
                onEntryConfirmed = viewModel::addEntry,
            )
        }
    }
}

@Composable
fun LabRatBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        TabRoutes.tabs.forEach { tab ->
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