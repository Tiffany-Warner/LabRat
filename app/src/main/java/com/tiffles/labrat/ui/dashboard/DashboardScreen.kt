package com.tiffles.labrat.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.BiomarkerStatus
import com.tiffles.labrat.domain.model.BiomarkerTrend
import com.tiffles.labrat.domain.model.PinnedBiomarkerSummary
import com.tiffles.labrat.ui.dashboard.components.BiomarkerSummaryCard
import com.tiffles.labrat.ui.theme.LabRatTheme
import java.time.LocalDate

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onNavigateToBiomarkers: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        DashboardUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        DashboardUiState.Empty -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(32.dp),
                ) {
                    Text(
                        text = "Pin your first biomarker to get started",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                    Button(onClick = onNavigateToBiomarkers) {
                        Text("Go to Biomarkers")
                    }
                }
            }
        }
        is DashboardUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier.fillMaxSize(),
            ) {
                items(uiState.summaries, key = { it.biomarkerId }) { summary ->
                    BiomarkerSummaryCard(
                        summary = summary,
                        onClick = { /* Phase 3.2: navigate to Biomarker Detail */ },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardScreenLoadingPreview() {
    LabRatTheme { DashboardScreen(uiState = DashboardUiState.Loading, onNavigateToBiomarkers = {}) }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardScreenEmptyPreview() {
    LabRatTheme { DashboardScreen(uiState = DashboardUiState.Empty, onNavigateToBiomarkers = {}) }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardScreenSuccessPreview() {
    LabRatTheme {
        DashboardScreen(
            uiState = DashboardUiState.Success(
                summaries = listOf(
                    PinnedBiomarkerSummary(1, "Glucose (Fasting)", 92.0, "mg/dL", LocalDate.of(2026, 6, 7), BiomarkerStatus.IN_RANGE, BiomarkerTrend.DOWN),
                    PinnedBiomarkerSummary(2, "HbA1c", 5.4, "%", LocalDate.of(2026, 6, 7), BiomarkerStatus.IN_RANGE, null),
                    PinnedBiomarkerSummary(3, "LDL Cholesterol", 142.0, "mg/dL", LocalDate.of(2026, 5, 12), BiomarkerStatus.OUT_OF_RANGE, BiomarkerTrend.UP),
                    PinnedBiomarkerSummary(4, "TSH", 2.1, "mIU/L", LocalDate.of(2026, 5, 12), BiomarkerStatus.IN_RANGE, BiomarkerTrend.STABLE),
                )
            ),
            onNavigateToBiomarkers = {},
        )
    }
}