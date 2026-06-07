package com.tiffles.labrat.ui.addresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.ui.theme.LabRatTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

private val DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, yyyy")
private val MILLIS_PER_DAY = TimeUnit.DAYS.toMillis(1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLabResultScreen(
    uiState: AddLabResultUiState,
    onNavigateUp: () -> Unit,
    onAddValues: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onLabNameChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onRemoveEntry: (BiomarkerEntryDraft) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = uiState.date.toEpochDay() * MILLIS_PER_DAY
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateSelected(LocalDate.ofEpochDay(millis / MILLIS_PER_DAY))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Lab Result") },
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
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = uiState.date.format(DATE_FORMATTER),
                onValueChange = {},
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Filled.CalendarToday, contentDescription = "Pick date")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = uiState.labName,
                onValueChange = onLabNameChange,
                label = { Text("Lab Name (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = onNotesChange,
                label = { Text("Notes (optional)") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedButton(
                onClick = onAddValues,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Add Values")
            }

            if (uiState.pendingEntries.isNotEmpty()) {
                HorizontalDivider()
                Text(
                    text = "Biomarker Values",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                uiState.pendingEntries.forEach { draft ->
                    BiomarkerEntryRow(
                        draft = draft,
                        onRemove = { onRemoveEntry(draft) },
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = onNavigateUp,
                    modifier = Modifier.weight(1f),
                ) { Text("Cancel") }
                Button(
                    onClick = onSave,
                    enabled = uiState.pendingEntries.isNotEmpty() && !uiState.isSaving,
                    modifier = Modifier.weight(1f),
                ) { Text("Save") }
            }

            uiState.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
private fun BiomarkerEntryRow(
    draft: BiomarkerEntryDraft,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = draft.biomarkerName,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "${draft.value} ${draft.unit}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Filled.Close, contentDescription = "Remove")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLabResultScreenEmptyPreview() {
    LabRatTheme {
        AddLabResultScreen(
            uiState = AddLabResultUiState(
                date = LocalDate.of(2026, 6, 7),
                labName = "",
                notes = "",
            ),
            onNavigateUp = {},
            onAddValues = {},
            onDateSelected = {},
            onLabNameChange = {},
            onNotesChange = {},
            onRemoveEntry = {},
            onSave = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLabResultScreenWithEntriesPreview() {
    LabRatTheme {
        AddLabResultScreen(
            uiState = AddLabResultUiState(
                date = LocalDate.of(2026, 6, 7),
                labName = "Quest Diagnostics",
                notes = "Fasted for 12 hours",
                pendingEntries = listOf(
                    BiomarkerEntryDraft(biomarkerId = 1, biomarkerName = "Glucose (Fasting)", unit = "mg/dL", value = 92.0),
                    BiomarkerEntryDraft(biomarkerId = 2, biomarkerName = "HbA1c", unit = "%", value = 5.4),
                    BiomarkerEntryDraft(biomarkerId = 3, biomarkerName = "Total Cholesterol", unit = "mg/dL", value = 187.0),
                ),
            ),
            onNavigateUp = {},
            onAddValues = {},
            onDateSelected = {},
            onLabNameChange = {},
            onNotesChange = {},
            onRemoveEntry = {},
            onSave = {},
        )
    }
}