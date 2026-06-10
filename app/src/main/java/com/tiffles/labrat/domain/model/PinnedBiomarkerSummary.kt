package com.tiffles.labrat.domain.model

import java.time.LocalDate

data class PinnedBiomarkerSummary(
    val biomarkerId: Long,
    val name: String,
    val latestValue: Double,
    val unit: String,
    val date: LocalDate,
    val status: BiomarkerStatus,
    val trend: BiomarkerTrend?,
)