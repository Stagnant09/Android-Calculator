# Jetpack Compose Simple Scientific Calculator

A modern scientific calculator app built with **Jetpack Compose** and **Kotlin**, supporting both standard and advanced mathematical operations. This app demonstrates clean UI structure, modular design, and state management using a `ViewModel`.

## 📱 Features

- Standard operations: `+`, `−`, `×`, `÷`, `%`, `=`, `C`
- Advanced math: `√`, `x²`, `^`, `log`, `ln`, `()`
- Decimal support
- Grid-based dynamic layout system
- Composable UI using Material 3 components

## 🛠 Tech Stack

- **Jetpack Compose**: UI toolkit for building native Android interfaces.
- **ViewModel + StateFlow**: Manages and observes UI state.
- **Kotlin Coroutines**: Efficient state management.
- **Material3 (Compose)**: UI components.

## 🚀 Architecture

- `MainScreen.kt`  
  The main UI screen composed of the expression display and the grid of buttons.

- `MainScreenViewModel.kt`  
  Handles logic for operations, input, evaluation, and state transitions.

- `Grid.kt`  
  A flexible composable to layout buttons in a grid with orientation support.

## 🔄 Grid Orientation

Supports multiple grid directions:
- `UP_TO_DOWN_LEFT_TO_RIGHT`
- `UP_TO_DOWN_RIGHT_TO_LEFT`
- `DOWN_TO_UP_LEFT_TO_RIGHT`
- `DOWN_TO_UP_RIGHT_TO_LEFT`

## 🎨 UI Example

```kotlin
Grid(
    rows = 5,
    columns = 5,
    orientation = GridOrientation.DOWN_TO_UP_RIGHT_TO_LEFT,
    content = operationsGrid(...)
)
```
