package com.tiffles.labrat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.BiomarkerStatus
import com.tiffles.labrat.ui.theme.StatusBorderline
import com.tiffles.labrat.ui.theme.StatusInRange
import com.tiffles.labrat.ui.theme.StatusNeutral
import com.tiffles.labrat.ui.theme.StatusOutOfRange

@Composable
fun StatusDot(
    status: BiomarkerStatus,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(status.color()),
    )
}

private fun BiomarkerStatus.color(): Color = when (this) {
    BiomarkerStatus.IN_RANGE -> StatusInRange
    BiomarkerStatus.BORDERLINE -> StatusBorderline
    BiomarkerStatus.OUT_OF_RANGE -> StatusOutOfRange
    BiomarkerStatus.NEUTRAL -> StatusNeutral
}