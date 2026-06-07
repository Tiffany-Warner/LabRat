package com.tiffles.labrat.ui.addresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.ui.theme.LabRatTheme

@Composable
fun ValueInputDialog(
    biomarker: Biomarker,
    onConfirm: (Double) -> Unit,
    onDismiss: () -> Unit,
) {
    var input by remember { mutableStateOf("") }
    val parsedValue = input.toDoubleOrNull()
    val isValid = parsedValue != null && parsedValue > 0
    val showError = input.isNotEmpty() && !isValid

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(biomarker.name) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                refRangeText(biomarker)?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Value (${biomarker.unit})") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    isError = showError,
                    supportingText = if (showError) {
                        { Text("Enter a positive number") }
                    } else null,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { parsedValue?.let { onConfirm(it) } },
                enabled = isValid,
            ) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}

private fun refRangeText(biomarker: Biomarker): String? = when {
    biomarker.refRangeLow != null && biomarker.refRangeHigh != null ->
        "Reference range: ${biomarker.refRangeLow}–${biomarker.refRangeHigh} ${biomarker.unit}"
    biomarker.refRangeLow != null ->
        "Reference range: ≥${biomarker.refRangeLow} ${biomarker.unit}"
    biomarker.refRangeHigh != null ->
        "Reference range: ≤${biomarker.refRangeHigh} ${biomarker.unit}"
    else -> null
}

@Preview
@Composable
private fun ValueInputDialogPreview() {
    LabRatTheme {
        ValueInputDialog(
            biomarker = Biomarker(
                id = 1,
                name = "Glucose (Fasting)",
                unit = "mg/dL",
                category = BiomarkerCategory.METABOLIC,
                refRangeLow = 70.0,
                refRangeHigh = 99.0,
                isPinned = false,
                isArchived = false,
            ),
            onConfirm = {},
            onDismiss = {},
        )
    }
}