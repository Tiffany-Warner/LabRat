package com.tiffles.labrat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tiffles.labrat.ui.navigation.LabRatBottomBar
import com.tiffles.labrat.ui.navigation.LabRatNavHost
import com.tiffles.labrat.ui.navigation.NavRoutes
import com.tiffles.labrat.ui.navigation.Routes
import com.tiffles.labrat.ui.theme.LabRatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabRatTheme {
                LabRatApp()
            }
        }
    }
}

@Composable
private fun LabRatApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = { LabRatBottomBar(navController) },
        floatingActionButton = {
            if (currentDestination?.hierarchy?.any { it.route == Routes.Dashboard.route } == true) {
                FloatingActionButton(
                    onClick = { navController.navigate(NavRoutes.AddLabResult.route) }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Lab Result")
                }
            }
        }
    ) { innerPadding ->
        LabRatNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}