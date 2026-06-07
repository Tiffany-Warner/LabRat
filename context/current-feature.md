# Current Feature

Phase 2.2 — Biomarker Picker Screen

## Status

In-Progress

## Goals

A searchable, categorized list of all biomarkers. The user picks one, enters a value, and returns to the Add Lab Result screen with the entry added.

### Deliverables

**Screen** (`ui/addresult/BiomarkerPickerScreen.kt`)
- Full screen (not a bottom sheet)
- Search bar at the top — filters biomarkers by name in real time
- Results grouped by `BiomarkerCategory` with sticky section headers
- Each row shows biomarker name and unit
- Already-added biomarkers shown with a checkmark and muted style — still tappable to re-add
- Tapping a biomarker opens `ValueInputDialog`

**Value Input Dialog** (`ui/addresult/ValueInputDialog.kt`)
- Shows biomarker name, unit, and reference range as helper text
- Numeric input field (positive numbers only)
- "Add" button — disabled until input is valid
- "Cancel" dismisses the dialog
- On confirm: calls `addEntry()` on the shared ViewModel and navigates back

**ViewModel**
- Reuse shared `AddLabResultViewModel` scoped to the nav back stack
- Expose `allBiomarkers: Flow<List<Biomarker>>` and `searchQuery` state
- Filter biomarkers client-side based on `searchQuery`

## Notes

- Archived biomarkers must not appear in the picker
- Sticky headers require `LazyColumn` with `stickyHeader {}` — not `LazyVerticalGrid`
- `BiomarkerEntryDraft` is already in `AddLabResultModels.kt` — import from there

## References

- @context/specs/phase2-2-biomarker-picker-spec.md
- @context/coding-standards.md

## History

- Initial Android Studio project created
- Core dependencies added (Hilt, Room, Navigation, Vico, Serialization)
- Hilt wired up (`LabRatApplication`, `MainActivity`)
- Project structure scaffolded to match clean architecture layout
- LabRat Material 3 theme applied (Color, Type, Shape, Theme)
- Bottom navigation shell — 4 tabs (Dashboard, Biomarkers, History, Settings) with placeholder screens
- Domain models defined (`Biomarker`, `BiomarkerCategory`, `LabResult`, `BiomarkerEntry`)
- Repository interfaces defined (`BiomarkerRepository`, `LabResultRepository`, `BiomarkerEntryRepository`)
- Room database layer implemented — entities, TypeConverter, DAOs, AppDatabase, mappers, repository impls, Hilt module
- 109 biomarkers seeded across all 13 categories, verified via Database Inspector
- Add Lab Result screen implemented — date picker, lab name, notes, entry list with remove, error display
- SaveLabResultUseCase with atomic @Transaction DAO insert for LabResult + BiomarkerEntry rows
- FAB on Dashboard navigates to Add Lab Result; NavRoutes sealed class added for non-tab destinations
- BiomarkerEntryDraft and AddLabResultUiState extracted to AddLabResultModels.kt for Phase 2.2 reuse
- LabResultDaoTest added