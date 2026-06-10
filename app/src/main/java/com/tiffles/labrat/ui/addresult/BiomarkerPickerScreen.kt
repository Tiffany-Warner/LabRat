package com.tiffles.labrat.ui.addresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.ui.components.CategoryHeader

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BiomarkerPickerScreen(
    onNavigateUp: () -> Unit,
    searchQuery: String,
    biomarkers: List<Biomarker>,
    addedIds: Set<Long>,
    onSearchQueryChange: (String) -> Unit,
    onEntryConfirmed: (BiomarkerEntryDraft) -> Unit,
    modifier: Modifier = Modifier,
) {
    val grouped = remember(biomarkers) {
        biomarkers
            .groupBy { it.category }
            .toSortedMap(compareBy { it.ordinal })
    }

    var selectedBiomarker by remember { mutableStateOf<Biomarker?>(null) }

    selectedBiomarker?.let { biomarker ->
        ValueInputDialog(
            biomarker = biomarker,
            onConfirm = { value ->
                onEntryConfirmed(
                    BiomarkerEntryDraft(
                        biomarkerId = biomarker.id,
                        biomarkerName = biomarker.name,
                        unit = biomarker.unit,
                        value = value,
                    )
                )
                selectedBiomarker = null
            },
            onDismiss = { selectedBiomarker = null },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Values") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search biomarkers") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(Icons.Filled.Close, contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )

            if (grouped.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No biomarkers found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    grouped.forEach { (category, categoryBiomarkers) ->
                        stickyHeader(key = category.name) {
                            CategoryHeader(category = category)
                        }
                        items(categoryBiomarkers, key = { it.id }) { biomarker ->
                            BiomarkerRow(
                                biomarker = biomarker,
                                isAdded = biomarker.id in addedIds,
                                onClick = { selectedBiomarker = biomarker },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BiomarkerRow(
    biomarker: Biomarker,
    isAdded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = biomarker.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isAdded) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            )
            Text(
                text = biomarker.unit,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (isAdded) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Already added",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}