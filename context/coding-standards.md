# Coding Standards

## Kotlin

- Use Kotlin idioms — prefer `val` over `var`, data classes, sealed classes, extension functions
- No platform types — always declare explicit nullability (`String?` vs `String`)
- Use `when` expressions over `if/else` chains for multiple branches
- Prefer `object` expressions/declarations for singletons
- Use named arguments for functions with multiple parameters of the same type
- No suppressed warnings without a comment explaining why

## Jetpack Compose

- Composable functions only (no XML layouts)
- Keep composables focused — one job per composable
- Extract reusable UI into standalone `@Composable` functions
- Use `remember` and `derivedStateOf` appropriately — avoid recomputation
- Hoist state up; composables should be as stateless as possible
- Ensure previews are made for each composable
- Use `LaunchedEffect`, `SideEffect`, and `DisposableEffect` correctly — don't misuse them for logic
- Avoid business logic inside composables — delegate to ViewModel

## Architecture

- **MVVM** with clean architecture layering:
  - `ui/` — Composables + ViewModels
  - `domain/` — Use cases, repository interfaces, domain models
  - `data/` — Repository implementations, data sources, mappers
- ViewModels expose `StateFlow<UiState>` — never expose mutable state directly
- Use `UiState` sealed classes or data classes per screen
- One ViewModel per screen/feature
- Use cases are single-responsibility and independently testable

## Dependency Injection

- Use **Hilt** for all DI
- Annotate with `@HiltViewModel`, `@Inject`, `@Module`, `@Provides` appropriately
- Scope correctly: `@Singleton` for app-wide, `@ActivityScoped` / `@ViewModelScoped` as needed
- Never use service locators or manual DI

## Navigation

- Use **Jetpack Navigation Compose**
- Define routes as a sealed class or `object` with `const val` route strings
- Pass only primitive IDs between screens — fetch full objects in the destination ViewModel
- No direct composable-to-composable navigation

## Data Layer

- Use **Room** for local persistence
  - Define `@Entity`, `@Dao`, and `@Database` clearly
  - Use `Flow<T>` return types in DAOs for reactive queries
  - Always run migrations — never use `fallbackToDestructiveMigration` in production
- Use **Retrofit** + **Kotlin Serialization** (or Moshi) for network calls
- Repository pattern: one repository per feature domain
- Map data models to domain models — don't leak Room entities or DTOs into the UI layer

## Coroutines & Flow

- Use `viewModelScope` for ViewModel coroutines
- Use `Dispatchers.IO` for disk/network, `Dispatchers.Default` for CPU-heavy work
- Never use `GlobalScope`
- Prefer `StateFlow` for UI state, `SharedFlow` for one-shot events
- Collect flows in the UI using `collectAsStateWithLifecycle()` (lifecycle-aware)

## File Organization

```
app/src/main/java/com/labrat/
├── ui/
│   ├── [feature]/
│   │   ├── [Feature]Screen.kt
│   │   ├── [Feature]ViewModel.kt
│   │   └── components/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   ├── entity/
│   │   └── AppDatabase.kt
│   ├── remote/
│   │   ├── api/
│   │   └── dto/
│   └── repository/
├── di/
└── util/
```

## Naming

- Classes/Interfaces: `PascalCase`
- Functions/variables: `camelCase`
- Constants: `SCREAMING_SNAKE_CASE`
- Composables: `PascalCase` (they are functions but treated like components)
- Resource IDs: `snake_case`
- Files: match their primary class name

## Theming

- Use **Material 3** (`androidx.compose.material3`)
- Define all colors, typography, and shapes via `MaterialTheme`
- No hardcoded colors — always reference theme tokens (`MaterialTheme.colorScheme.primary`, etc.)
- Support both light and dark mode from the start
- Use `dp` and `sp` — never hardcode pixel values

## Error Handling

- Use `Result<T>` or a sealed `Resource<T>` wrapper for async operations
- Surface errors through UiState — never crash silently
- Display user-friendly messages; log technical details
- Handle network errors, empty states, and loading states explicitly per screen

## Code Quality

- No commented-out code unless explained
- No unused imports or variables
- Keep functions under 40 lines when possible — extract if longer
- Add helper functions liberally with names describing their purpose to maintain readability 
- Write self-documenting code; add KDoc for public APIs and non-obvious logic
- Run `./gradlew lint` and fix warnings before committing
