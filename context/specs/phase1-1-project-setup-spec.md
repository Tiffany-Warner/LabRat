# Phase 1.1 — Project Setup & App Shell

## Overview

Initialize the LabRat Android project with all core dependencies, theming, and bottom navigation shell. No real features yet — just a solid foundation every future phase builds on.

## Requirements

- Create new Android project (Kotlin, Jetpack Compose, min SDK 26)
- Add all core dependencies to `build.gradle.kts`: Hilt, Room, Navigation Compose, Material 3, Vico charts, Kotlin Serialization
- Configure Hilt — `@HiltAndroidApp` on `LabRatApplication`, `@AndroidEntryPoint` on `MainActivity`
- Define LabRat brand theme in `ui/theme/` (colors, typography, shapes)
- Dark mode support from the start — `dynamicColor = false`, use custom palette
- Bottom navigation with 4 tabs: Dashboard, Biomarkers, History, Settings
- Each tab shows a placeholder screen with a centered `Text` label for now
- Define all navigation routes as a sealed class in `ui/navigation/Routes.kt`

## References

- @context/project-schema.md
- @context/coding-standards.md
