package com.tiffles.labrat.ui.biomarkers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.ui.components.CategoryHeader
import com.tiffles.labrat.ui.theme.LabRatTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BiomarkersScreen(
    uiState: BiomarkersUiState,
    onTogglePin: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        BiomarkersUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is BiomarkersUiState.Success -> {
            val grouped = remember(uiState.biomarkers) {
                uiState.biomarkers
                    .groupBy { it.category }
                    .toSortedMap(compareBy { it.ordinal })
            }
            LazyColumn(modifier = modifier.fillMaxSize()) {
                grouped.forEach { (category, biomarkers) ->
                    stickyHeader(key = category.name) {
                        CategoryHeader(category = category)
                    }
                    items(biomarkers, key = { it.id }) { biomarker ->
                        BiomarkerListRow(
                            biomarker = biomarker,
                            onTogglePin = { onTogglePin(biomarker.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BiomarkerListRow(
    biomarker: Biomarker,
    onTogglePin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = biomarker.name,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = biomarker.unit,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        IconButton(onClick = onTogglePin) {
            Icon(
                imageVector = if (biomarker.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                contentDescription = if (biomarker.isPinned) "Unpin ${biomarker.name}" else "Pin ${biomarker.name}",
                tint = if (biomarker.isPinned) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiomarkersScreenLoadingPreview() {
    LabRatTheme {
        BiomarkersScreen(uiState = BiomarkersUiState.Loading, onTogglePin = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiomarkersScreenSuccessPreview() {
    LabRatTheme {
        BiomarkersScreen(
            uiState = BiomarkersUiState.Success(
                biomarkers = listOf(
                    Biomarker(1, "Glucose (Fasting)", "mg/dL", BiomarkerCategory.METABOLIC, 70.0, 99.0, isPinned = true, isArchived = false),
                    Biomarker(2, "HbA1c", "%", BiomarkerCategory.METABOLIC, null, 5.7, isPinned = false, isArchived = false),
                    Biomarker(3, "LDL Cholesterol", "mg/dL", BiomarkerCategory.CARDIAC, null, 100.0, isPinned = true, isArchived = false),
                    Biomarker(4, "HDL Cholesterol", "mg/dL", BiomarkerCategory.CARDIAC, 40.0, null, isPinned = false, isArchived = false),
                    Biomarker(5, "TSH", "mIU/L", BiomarkerCategory.THYROID, 0.4, 4.0, isPinned = false, isArchived = false),
                )
            ),
            onTogglePin = {},
        )
    }
}