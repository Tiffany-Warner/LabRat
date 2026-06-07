# Phase 2.1 — Add Lab Result Screen

## Overview

A screen where users create a new lab result entry — date, optional lab name, optional notes, and a list of biomarker values they've added. Saving is disabled until at least one value is added.

## Requirements

### Screen (`ui/addresult/AddLabResultScreen.kt`)

- Accessible via FAB on the Dashboard
- Fields:
  - Date picker (defaults to today) — use `DatePickerDialog`
  - Lab name text field (optional, e.g. "Quest Diagnostics")
  - Notes text field (optional, multiline)
- "Add Values" button — navigates to Biomarker Picker screen (Phase 2.2)
- List of added biomarker entries below with value and unit
- Each entry row has a remove (×) button
- "Save" button — disabled until at least one biomarker entry exists
- "Cancel" button — dismisses without saving

### ViewModel (`ui/addresult/AddLabResultViewModel.kt`)

- `UiState` holds: date, labName, notes, `pendingEntries: List<BiomarkerEntryDraft>`
- `BiomarkerEntryDraft` — a lightweight model: biomarkerId, biomarkerName, unit, value
- Exposes: `updateDate()`, `updateLabName()`, `updateNotes()`, `removeEntry()`
- Exposes: `addEntry(draft: BiomarkerEntryDraft)` — called by Biomarker Picker on return
- `saveLabResult()` — delegates to `SaveLabResultUseCase`, navigates back on success

### Use Case (`domain/usecase/SaveLabResultUseCase.kt`)

- Takes a `LabResult` + `List<BiomarkerEntry>`
- Saves both atomically in a single Room transaction
- Returns `Result<Unit>`

## Notes

- The Biomarker Picker (Phase 2.2) is a separate screen — navigate to it and return with the selected entry
- Use a shared ViewModel scoped to the nav back stack to pass the selected entry back

## References

- @context/coding-standards.md
- @context/specs/phase1-3-room-database-spec.md
