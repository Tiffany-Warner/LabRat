# Phase 3.1a — Biomarkers List & Pin Toggle

## Overview

Replace the placeholder Biomarkers tab with a real screen showing all non-archived biomarkers. Users can pin/unpin biomarkers from here, which controls what appears on the Dashboard.

## Requirements

### Screen (`ui/biomarkers/BiomarkersScreen.kt`)

- Replace the placeholder from Phase 1.1
- `LazyColumn` with all non-archived biomarkers
- Grouped by `BiomarkerCategory` with sticky section headers (same style as `BiomarkerPickerScreen`)
- Each row shows:
  - Biomarker name
  - Unit (subtitle)
  - Pin/unpin icon button (right side) — filled icon when pinned, outlined when not
- Tapping the pin icon toggles `isPinned` in the database
- No other interactions for now — no editing, archiving, or navigation to detail

### ViewModel (`ui/biomarkers/BiomarkersViewModel.kt`)

- Loads all non-archived biomarkers via `BiomarkerRepository.getAll()`
- Filter out archived biomarkers client-side
- `UiState` sealed interface: `Loading`, `Success(biomarkers: List<Biomarker>)`
- `togglePin(biomarkerId: Long)` — looks up the biomarker, flips `isPinned`, calls `repository.update()`

### Repository changes

- Verify `BiomarkerRepository.update()` exists and updates all fields including `isPinned`
- No new repository methods needed

## Notes

- The screen receives only state and callbacks — ViewModel is hoisted to `LabRatNavHost`
- Reuse the `CategoryHeader` composable from `BiomarkerPickerScreen` — extract it to a shared `ui/components/` location if it isn't already
- After pinning a biomarker here, the Dashboard should reactively show it since `DashboardViewModel` observes pinned biomarkers via Flow
- Use `Icons.Filled.PushPin` for pinned and `Icons.Outlined.PushPin` for unpinned

## References

- @context/coding-standards.md
- @context/specs/phase3-1-dashboard-spec.md
