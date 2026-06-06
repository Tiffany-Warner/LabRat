package com.tiffles.labrat.ui.biomarkers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tiffles.labrat.ui.theme.LabRatTheme

@Composable
fun BiomarkersScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Biomarkers")
    }
}

@Preview(showBackground = true)
@Composable
private fun BiomarkersScreenPreview() {
    LabRatTheme { BiomarkersScreen() }
}