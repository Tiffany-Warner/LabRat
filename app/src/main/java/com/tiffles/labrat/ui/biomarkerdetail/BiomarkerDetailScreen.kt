package com.tiffles.labrat.ui.biomarkerdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.domain.model.BiomarkerDetailEntry
import com.tiffles.labrat.domain.model.BiomarkerStatus
import com.tiffles.labrat.domain.model.DateRange
import com.tiffles.labrat.ui.components.StatusDot
import com.tiffles.labrat.ui.theme.LabRatTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy")

private val DATE_RANGE_OPTIONS = listOf(
    DateRange.THREE_MONTHS to "3M",
    DateRange.SIX_MONTHS to "6M",
    DateRange.ONE_YEAR to "1Y",
    DateRange.ALL to "All",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiomarkerDetailScreen(
    uiState: BiomarkerDetailUiState,
    onNavigateUp: () -> Unit,
    onTogglePin: () -> Unit,
    onSelectRange: (DateRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    val success = uiState as? BiomarkerDetailUiState.Success
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(success?.biomarker?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (success != null) {
                        IconButton(onClick = onTogglePin) {
                            Icon(
                                imageVector = if (success.biomarker.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                contentDescription = if (success.biomarker.isPinned) "Unpin" else "Pin",
                            )
                        }
                    }
                },
            )
        },
        modifier = modifier,
    ) { padding ->
        when (uiState) {
            BiomarkerDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            BiomarkerDetailUiState.NotFound -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Biomarker not found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            is BiomarkerDetailUiState.Success -> {
                BiomarkerDetailContent(
                    uiState = uiState,
                    onSelectRange = onSelectRange,
                    modifier = Modifier.fillMaxSize().padding(padding),
                )
            }
        }
    }
}

@Composable
private fun BiomarkerDetailContent(
    uiState: BiomarkerDetailUiState.Success,
    onSelectRange: (DateRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        item {
            BiomarkerInfoSection(biomarker = uiState.biomarker)
        }
        item {
            ChartPlaceholder()
        }
        item {
            DateRangeFilter(
                selected = uiState.selectedRange,
                onSelect = onSelectRange,
            )
        }
        if (uiState.entries.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "No results logged yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            items(uiState.entries, key = { "${it.date}-${it.value}" }) { entry ->
                EntryRow(entry = entry, unit = uiState.biomarker.unit)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun BiomarkerInfoSection(
    biomarker: Biomarker,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        text = biomarker.category.displayName,
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
            )
            Text(
                text = biomarker.unit,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = refRangeText(biomarker),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun ChartPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Chart coming soon",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangeFilter(
    selected: DateRange,
    onSelect: (DateRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        DATE_RANGE_OPTIONS.forEachIndexed { index, (range, label) ->
            SegmentedButton(
                selected = selected == range,
                onClick = { onSelect(range) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = DATE_RANGE_OPTIONS.size),
                label = { Text(label) },
            )
        }
    }
}

@Composable
private fun EntryRow(
    entry: BiomarkerDetailEntry,
    unit: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = entry.date.format(DATE_FORMATTER),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusDot(status = entry.status)
            Text(
                text = "${entry.value} $unit",
                style = MaterialTheme.typography.bodyMedium,
            )
            entry.delta?.let { delta ->
                Text(
                    text = formatDelta(delta),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun refRangeText(biomarker: Biomarker): String {
    val low = biomarker.refRangeLow
    val high = biomarker.refRangeHigh
    return when {
        low != null && high != null -> "Ref: $low–$high ${biomarker.unit}"
        low != null -> "Ref: >$low ${biomarker.unit}"
        high != null -> "Ref: <$high ${biomarker.unit}"
        else -> "No reference range set"
    }
}

private fun formatDelta(delta: Double): String =
    (if (delta >= 0) "+" else "") + "%.1f".format(delta)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiomarkerDetailLoadingPreview() {
    LabRatTheme {
        BiomarkerDetailScreen(
            uiState = BiomarkerDetailUiState.Loading,
            onNavigateUp = {},
            onTogglePin = {},
            onSelectRange = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiomarkerDetailSuccessPreview() {
    val biomarker = Biomarker(
        id = 1,
        name = "Glucose (Fasting)",
        unit = "mg/dL",
        category = BiomarkerCategory.METABOLIC,
        refRangeLow = 70.0,
        refRangeHigh = 99.0,
        isPinned = true,
        isArchived = false,
    )
    LabRatTheme {
        BiomarkerDetailScreen(
            uiState = BiomarkerDetailUiState.Success(
                biomarker = biomarker,
                selectedRange = DateRange.ALL,
                entries = listOf(
                    BiomarkerDetailEntry(92.0, LocalDate.of(2026, 6, 7), BiomarkerStatus.IN_RANGE, +4.2),
                    BiomarkerDetailEntry(87.8, LocalDate.of(2026, 3, 15), BiomarkerStatus.IN_RANGE, -2.0),
                    BiomarkerDetailEntry(89.8, LocalDate.of(2025, 12, 10), BiomarkerStatus.IN_RANGE, null),
                ),
            ),
            onNavigateUp = {},
            onTogglePin = {},
            onSelectRange = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiomarkerDetailEmptyPreview() {
    val biomarker = Biomarker(
        id = 2,
        name = "HbA1c",
        unit = "%",
        category = BiomarkerCategory.METABOLIC,
        refRangeLow = null,
        refRangeHigh = 5.7,
        isPinned = false,
        isArchived = false,
    )
    LabRatTheme {
        BiomarkerDetailScreen(
            uiState = BiomarkerDetailUiState.Success(
                biomarker = biomarker,
                selectedRange = DateRange.THREE_MONTHS,
                entries = emptyList(),
            ),
            onNavigateUp = {},
            onTogglePin = {},
            onSelectRange = {},
        )
    }
}