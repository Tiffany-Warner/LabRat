# Current Feature

Phase 1.1 — Navigation Shell (Bottom Nav + Placeholder Screens)

## Status

In Progress

## Goals

Add bottom navigation with 4 tabs and placeholder screens for each. This completes Phase 1.1.

### Deliverables

- `ui/navigation/Routes.kt` — sealed class with route constants for all 4 tabs
- `ui/navigation/LabRatNavHost.kt` — NavHost wiring all routes to their screens
- `ui/dashboard/DashboardScreen.kt` — placeholder
- `ui/biomarkers/BiomarkersScreen.kt` — placeholder
- `ui/history/HistoryScreen.kt` — placeholder
- `ui/settings/SettingsScreen.kt` — placeholder
- `MainActivity.kt` — hosts the Scaffold with `BottomNavigationBar` and `LabRatNavHost`

### Tab Order

1. Dashboard
2. Biomarkers
3. History
4. Settings

### Notes

- Each placeholder screen: centered `Text` with the screen name, nothing else
- Use `NavigationBar` / `NavigationBarItem` (Material 3)
- Routes as a sealed class with `object` entries, each with a `route: String`, `label: String`, and `icon: ImageVector`
- Use standard M3 icons: `Home`, `Science` (or `Biotech`), `History`, `Settings`
- No ViewModels yet — placeholders are stateless composables

## References

- @context/specs/phase1-1-project-setup-spec.md
- @context/coding-standards.md

## History

- Initial Android Studio project created
- Core dependencies added (Hilt, Room, Navigation, Vico, Serialization)
- Hilt wired up (`LabRatApplication`, `MainActivity`)
- Project structure scaffolded to match clean architecture layout
- LabRat Material 3 theme applied (Color, Type, Shape, Theme)