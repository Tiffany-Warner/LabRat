package com.tiffles.labrat.domain.model

data class Biomarker(
    val id: Long,
    val name: String,
    val unit: String,
    val category: BiomarkerCategory,
    val refRangeLow: Double?,
    val refRangeHigh: Double?,
    val isPinned: Boolean,
    val isArchived: Boolean
)