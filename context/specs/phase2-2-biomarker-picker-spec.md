# Phase 2.2 — Biomarker Picker Screen

## Overview

A searchable, categorized list of all biomarkers. The user picks one, enters a value, and returns to the Add Lab Result screen with the entry added.

## Requirements

### Screen (`ui/addresult/BiomarkerPickerScreen.kt`)

- Full screen (not a bottom sheet)
- Search bar at the top — filters biomarkers by name in real time
- Results grouped by `BiomarkerCategory` with sticky section headers
- Each row shows biomarker name and unit
- Already-added biomarkers (passed in via shared ViewModel) shown with a checkmark and muted style — still tappable to re-add
- Tapping a biomarker opens `ValueInputDialog`

### Value Input Dialog (`ui/addresult/ValueInputDialog.kt`)

- Shows biomarker name, unit, and reference range as helper text
- Numeric input field (positive numbers only)
- "Add" button — disabled until input is valid
- "Cancel" dismisses the dialog
- On confirm: calls `addEntry()` on the shared ViewModel and navigates back to Add Lab Result screen

### ViewModel

- Reuse the shared `AddLabResultViewModel` scoped to the nav back stack
- Expose `allBiomarkers: Flow<List<Biomarker>>` and a `searchQuery` state
- Filter biomarkers client-side based on `searchQuery`

## Notes

- Archived biomarkers should not appear in the picker
- Sticky headers require `LazyColumn` with `stickyHeader {}` — not a `LazyVerticalGrid`

## References

- @context/coding-standards.md
- @context/specs/phase2-1-add-lab-result-screen-spec.md
