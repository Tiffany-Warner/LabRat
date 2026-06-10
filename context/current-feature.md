# Current Feature

Phase 3.1a — Biomarkers List & Pin Toggle

## Status

Completed

## Goals

Replace the placeholder Biomarkers tab with a real screen showing all non-archived biomarkers. Users can pin/unpin biomarkers from here, which controls what appears on the Dashboard.

### Deliverables

**Screen** (`ui/biomarkers/BiomarkersScreen.kt`)
- `LazyColumn` with all non-archived biomarkers
- Grouped by `BiomarkerCategory` with sticky section headers (same style as `BiomarkerPickerScreen`)
- Each row: biomarker name, unit (subtitle), pin/unpin icon button (right side)
- Filled pin icon when pinned, outlined when not
- Tapping pin icon toggles `isPinned` in the database

**ViewModel** (`ui/biomarkers/BiomarkersViewModel.kt`)
- Loads all non-archived biomarkers via `BiomarkerRepository.getAll()`
- Filters out archived biomarkers client-side
- `UiState` sealed interface: `Loading`, `Success(biomarkers: List<Biomarker>)`
- `togglePin(biomarkerId: Long)` — looks up biomarker, flips `isPinned`, calls `repository.update()`

**Shared component** (`ui/components/CategoryHeader.kt`)
- Extract `CategoryHeader` composable from `BiomarkerPickerScreen` to shared location
- Reuse in both `BiomarkersScreen` and `BiomarkerPickerScreen`

## Notes

- Screen receives only state and callbacks — ViewModel hoisted to `LabRatNavHost`
- After pinning here, Dashboard reactively updates via Flow observation
- Use `Icons.Filled.PushPin` for pinned, `Icons.Outlined.PushPin` for unpinned

## References

- @context/specs/phase3-1a-biomarkers-list-spec.md
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
- BiomarkerPickerScreen implemented — searchable, grouped by category with sticky headers, checkmark for already-added biomarkers
- ValueInputDialog implemented — ref range helper text, positive-number validation, Add/Cancel
- ViewModel hoisted out of composables; navigation driven via SharedFlow (entryAdded, navigateUp)
- Search query resets on addEntry so picker opens clean each time
- Routes/NavRoutes renamed to TabRoutes/FullScreenRoutes
- Typography migrated from Google Fonts provider to bundled Inter font files
- AddLabResultViewModelTest (unit) and ValueInputDialogTest (instrumented) added
- mockk, mockk-android, kotlinx-coroutines-test added to version catalog
- Dashboard screen implemented — BiomarkerSummaryCard grid, status dots, trend arrows, loading/empty/success states
- GetPinnedBiomarkerSummariesUseCase — status (10% borderline) and trend logic in domain layer
- BiomarkerStatus, BiomarkerTrend, PinnedBiomarkerSummary, BiomarkerEntryRecord domain models added
- Room join query for date-ordered entry history (BiomarkerEntryWithDate projection)
- BiomarkersScreen implemented — LazyColumn with sticky category headers, pin/unpin icon button per row
- BiomarkersViewModel observes getAll() Flow, filters archived client-side, togglePin() persists via repository.update()
- CategoryHeader extracted from BiomarkerPickerScreen to shared ui/components/ and reused in both screens