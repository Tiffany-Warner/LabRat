package com.tiffles.labrat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tiffles.labrat.ui.navigation.LabRatBottomBar
import com.tiffles.labrat.ui.navigation.LabRatNavHost
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
    Scaffold(
        bottomBar = { LabRatBottomBar(navController) }
    ) { innerPadding ->
        LabRatNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}