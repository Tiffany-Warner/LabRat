package com.tiffles.labrat.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.domain.model.BiomarkerStatus
import com.tiffles.labrat.domain.model.BiomarkerTrend
import com.tiffles.labrat.domain.model.PinnedBiomarkerSummary
import com.tiffles.labrat.ui.components.StatusDot
import com.tiffles.labrat.ui.theme.LabRatTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d")

@Composable
fun BiomarkerSummaryCard(
    summary: PinnedBiomarkerSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 120.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                StatusDot(status = summary.status)
                Text(
                    text = summary.name,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "${summary.latestValue} ${summary.unit}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    summary.trend?.let { trend ->
                        Text(
                            text = trend.arrow(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                Text(
                    text = summary.date.format(DATE_FORMATTER),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun BiomarkerTrend.arrow(): String = when (this) {
    BiomarkerTrend.UP -> "↑"
    BiomarkerTrend.DOWN -> "↓"
    BiomarkerTrend.STABLE -> "→"
}

@Preview(showBackground = true)
@Composable
private fun BiomarkerSummaryCardInRangePreview() {
    LabRatTheme {
        BiomarkerSummaryCard(
            summary = PinnedBiomarkerSummary(
                biomarkerId = 1,
                name = "Glucose (Fasting)",
                latestValue = 92.0,
                unit = "mg/dL",
                date = LocalDate.of(2026, 6, 7),
                status = BiomarkerStatus.IN_RANGE,
                trend = BiomarkerTrend.DOWN,
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiomarkerSummaryCardOutOfRangePreview() {
    LabRatTheme {
        BiomarkerSummaryCard(
            summary = PinnedBiomarkerSummary(
                biomarkerId = 2,
                name = "LDL Cholesterol",
                latestValue = 142.0,
                unit = "mg/dL",
                date = LocalDate.of(2026, 6, 7),
                status = BiomarkerStatus.OUT_OF_RANGE,
                trend = BiomarkerTrend.UP,
            ),
            onClick = {},
        )
    }
}