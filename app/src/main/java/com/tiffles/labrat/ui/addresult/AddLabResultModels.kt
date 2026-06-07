package com.tiffles.labrat.ui.addresult

import java.time.LocalDate

data class BiomarkerEntryDraft(
    val biomarkerId: Long,
    val biomarkerName: String,
    val unit: String,
    val value: Double,
)

data class AddLabResultUiState(
    val date: LocalDate = LocalDate.now(),
    val labName: String = "",
    val notes: String = "",
    val pendingEntries: List<BiomarkerEntryDraft> = emptyList(),
    val isSaving: Boolean = false,
    val error: String? = null,
)