# AcademyTest — Android (Kotlin / Jetpack Compose)

Android port of the original SwiftUI `AcademyTest` app. Same functionality: a sorted list of items with a name and a favorite state, a detail view, and changes to items reflected automatically across the whole app.

## Features

- Sorted list of items (alphabetical, then by creation order)
- Add a new item
- Toggle favorite state from the list or the detail screen
- Delete an item via swipe on the list or from the detail screen
- Item detail screen
- Empty state when there are no items
- `@Preview` for every composable

## Architecture

The project mirrors the structure of the original SwiftUI codebase:

```
model/
  Item.kt                 // immutable data class (id, creationIndex, name, isFavorite)
ViewModels/
  ItemsListViewModel.kt   // holds the items, sorting, add/toggle/delete logic
  AddItemViewModel.kt     // transient state for the "add item" form
views/
  ItemsListView.kt        // list screen + add-item dialog trigger
  ItemRowView.kt          // single row in the list
  ItemDetailView.kt       // detail screen
  FavoriteButton.kt       // shared favorite star button
  AddItemView.kt          // "add item" dialog
navigation/
  AppNavHost.kt           // NavHost wiring list -> detail, shared ViewModel instance
```

### Notes on the SwiftUI → Compose translation

- SwiftUI's `@Observable` reference types became an immutable `Item` data class plus a `ItemsListViewModel` exposing `StateFlow`, since Compose favors state hoisting over mutable shared objects. Updating an item means replacing it in the list with `.copy(...)`, not mutating a property in place.
- `@Bindable` / `@Binding` (two-way bindings) became explicit `value` + `onChange`/`onToggle` callbacks (state hoisting), e.g. in `FavoriteButton`.
- `NavigationSplitView` became a `NavHost` with two routes (`list`, `detail/{itemId}`) sharing a single `ItemsListViewModel` instance (created once in `AppNavHost` and passed down), which is what keeps favorite/delete changes in sync between the list and the detail screen.
- SwiftUI's `Form` became a plain `Column` with `Row`s and `HorizontalDivider`s, since Compose has no direct `Form` equivalent.
- The `.sheet` + `AddItemView` pattern became a Material 3 `AlertDialog`.
- `.onDelete` on the SwiftUI `List` became `SwipeToDismissBox` per row.

## Requirements

- Android Studio (current stable)
- compileSdk / targetSdk 37, minSdk 24

## Use of AI

I used an AI assistant as a pair-programming/reference tool throughout this exercise, mainly to:

- Map SwiftUI concepts to their closest Jetpack Compose equivalent (e.g. `@Observable` → `StateFlow`, `@Binding` → state hoisting, `NavigationSplitView` → `NavHost`) before writing the actual code myself in Android Studio.
- Debug specific build errors as they came up (unresolved references from a package name mismatch, missing `material-icons-extended` dependency, the `compileSdk` bump required by newer AndroidX libraries, a layout bug where the swipe-to-delete background bled through the row because the row content wasn't opaque).

All code was typed and run by me in Android Studio, verified against the running app and the Compose Previews. I evaluated each suggestion against how the original SwiftUI code behaved rather than accepting it at face value — a couple of times that meant asking for the reasoning behind a suggested fix, or requesting the AI double-check a claim (e.g. exact dependency version numbers) I wasn't confident was accurate.
