package com.tiffles.labrat.ui.biomarkerdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerDetailEntry
import com.tiffles.labrat.domain.model.DateRange
import com.tiffles.labrat.domain.model.computeBiomarkerStatus
import com.tiffles.labrat.domain.repository.BiomarkerEntryRepository
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BiomarkerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val biomarkerRepository: BiomarkerRepository,
    private val entryRepository: BiomarkerEntryRepository,
) : ViewModel() {

    private val biomarkerId: Long = checkNotNull(savedStateHandle["biomarkerId"])
    private val selectedRange = MutableStateFlow(DateRange.ALL)

    val uiState: StateFlow<BiomarkerDetailUiState> = combine(
        biomarkerRepository.getAll().map { list -> list.find { it.id == biomarkerId } },
        entryRepository.getAllEntriesForBiomarker(biomarkerId),
        selectedRange,
    ) { biomarker, allEntries, range ->
        biomarker ?: return@combine BiomarkerDetailUiState.NotFound
        val cutoff = range.cutoffDate(LocalDate.now())
        val filtered = if (cutoff != null) allEntries.filter { it.date >= cutoff } else allEntries
        val entries = filtered.mapIndexed { index, record ->
            val previous = filtered.getOrNull(index + 1)
            BiomarkerDetailEntry(
                value = record.value,
                date = record.date,
                status = computeBiomarkerStatus(biomarker, record.value),
                delta = previous?.let { record.value - it.value },
            )
        }
        BiomarkerDetailUiState.Success(
            biomarker = biomarker,
            entries = entries,
            selectedRange = range,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), BiomarkerDetailUiState.Loading)

    fun setDateRange(range: DateRange) {
        selectedRange.value = range
    }

    fun togglePin() {
        val state = uiState.value as? BiomarkerDetailUiState.Success ?: return
        viewModelScope.launch {
            biomarkerRepository.update(state.biomarker.copy(isPinned = !state.biomarker.isPinned))
        }
    }

    private fun DateRange.cutoffDate(today: LocalDate): LocalDate? = when (this) {
        DateRange.THREE_MONTHS -> today.minusMonths(3)
        DateRange.SIX_MONTHS -> today.minusMonths(6)
        DateRange.ONE_YEAR -> today.minusYears(1)
        DateRange.ALL -> null
    }


}