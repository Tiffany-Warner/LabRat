# Current Feature

Phase 2.1 — Add Lab Result Screen

## Status

Complete

## Goals

Build the screen where users create a new lab result entry — date, optional lab name, optional notes, and a list of biomarker values. Saving is disabled until at least one value is added.

### Deliverables

**Screen** (`ui/addresult/AddLabResultScreen.kt`)
- Accessible via FAB on the Dashboard
- Date picker (defaults to today) — `DatePickerDialog`
- Lab name text field (optional)
- Notes text field (optional, multiline)
- "Add Values" button — navigates to Biomarker Picker (Phase 2.2)
- List of added biomarker entries with value, unit, and remove (×) button
- "Save" button — disabled until at least one entry exists
- "Cancel" button — dismisses without saving

**ViewModel** (`ui/addresult/AddLabResultViewModel.kt`)
- `UiState` holds: date, labName, notes, `pendingEntries: List<BiomarkerEntryDraft>`
- `BiomarkerEntryDraft` — lightweight model: biomarkerId, biomarkerName, unit, value
- Exposes: `updateDate()`, `updateLabName()`, `updateNotes()`, `removeEntry()`, `addEntry()`, `saveLabResult()`
- `saveLabResult()` — delegates to `SaveLabResultUseCase`, navigates back on success

**Use Case** (`domain/usecase/SaveLabResultUseCase.kt`)
- Takes a `LabResult` + `List<BiomarkerEntry>`
- Saves both atomically in a single Room transaction
- Returns `Result<Unit>`

## Notes

- Biomarker Picker (Phase 2.2) is a separate screen — navigate to it and return with the selected entry
- Use a shared ViewModel scoped to the nav back stack to pass the selected entry back

## References

- @context/specs/phase2-1-add-lab-result-screen-spec.md
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