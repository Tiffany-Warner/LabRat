package com.tiffles.labrat.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: replace with full Dashboard ViewModel once Dashboard feature is implemented
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val biomarkerRepository: BiomarkerRepository
) : ViewModel() {

    fun triggerDatabaseInit() {
        viewModelScope.launch {
            biomarkerRepository.getAll().collect {}
        }
    }
}