# Jetpack Compose Scientific Calculator Suite

A modern **scientific calculator app** built with **Jetpack Compose** and **Kotlin**, supporting standard, advanced, and domain-specific mathematical operations.  
This app demonstrates **clean UI structure**, **modular design**, and **state management** with `ViewModel` and `StateFlow`.

---

## 📱 Features

### 🔢 Core Calculator
- Standard operations: `+`, `−`, `×`, `÷`, `%`, `=`, `C`
- Advanced functions: `√`, `x²`, `^`, `log`, `ln`, `()`
- Decimal support
- Grid-based dynamic layout system

### Unit Conversion
- Various unit types: length, area, mass, temperature, computer storage etc.

### 🧮 Equations Solver
- Solve **quadratic equations** (`ax² + bx + c = 0`)
- Solve **cubic equations** (via depressed form + Cardano’s method)
- Real and complex roots

### 📐 Triangle Calculator
- Interactive triangle with **draggable vertices**
- Real-time updates of:
  - Side lengths
  - Angles (degrees or radians)
  - Trigonometric values (sin, cos, tan)
  - Area, perimeter
  - Centroid, incenter, circumcenter, orthocenter
- Canvas grid with live geometry rendering

### 🔢 Matrix Algebra
- Operations on matrices:
  - Addition
  - Multiplication
  - Scalar multiplication
  - Transpose
  - Inverse
  - Determinant
- Linear combination of matrices (`λ₁A + λ₂B`)
- Grid input system with editable matrix dimensions
- Results displayed in modal dialogs

### 💱 Currency Converter (WIP)
- Live exchange rates from [Frankfurter API](https://www.frankfurter.app/)  
- Convert between currencies in real-time
- Searchable list of currencies
- Reactive flow-based updates

### 📊 Mathematical & Scientific Constants
- Predefined constants with **symbols**, **names**, and **values**:
  - Mathematical: `π`, `e`, `φ`, ζ(3), Catalan’s constant, etc.
  - Scientific: Planck’s constant, Avogadro’s number, fine-structure constant, etc.
- Search option with easy copy-to-clipboard support

---

## 🛠 Tech Stack

- **Jetpack Compose** — modern UI toolkit  
- **ViewModel + StateFlow** — reactive state management  
- **Kotlin Coroutines & Flow** — async and reactive programming  
- **Material3 (Compose)** — UI components  
- **Retrofit + Gson** — networking & JSON parsing  
- **OkHttp Interceptor** — API call logging  
- **Clean Architecture** — Repository → Interactor → ViewModel → UI  

---

## 🚀 Architecture Overview

- `MainScreen.kt` → Core calculator UI  
- `EquationsScreen.kt` → Quadratic & cubic equation solver  
- `TriangleInteractive.kt` → Interactive triangle calculator with draggable vertices  
- `MatrixScreen.kt` → Matrix algebra with dynamic grid input  
- `CurrencyScreen.kt` → Currency exchange rates & conversion  
- `ConstantsScreen.kt` → Mathematical & scientific constants library  
- `Grid.kt` → Flexible composable for grid-based layouts  

---

## 🎨 Example: Interactive Triangle

```kotlin
TriangleCanvas(
    vertices = verticesState,
    showDegrees = true,
    viewModel = triangleViewModel
)
```
Features:
- Drag vertices A, B, C
- Watch side lengths, angles, and centers update in real-time
- Live grid rendering

🔄 Grid Orientation

The Grid composable supports multiple orientations:
- `UP_TO_DOWN_LEFT_TO_RIGHT`
- `UP_TO_DOWN_RIGHT_TO_LEFT`
- `DOWN_TO_UP_LEFT_TO_RIGHT`
- `DOWN_TO_UP_RIGHT_TO_LEFT`
```kotlin
Grid( rows = 5, columns = 5, orientation = GridOrientation.DOWN_TO_UP_RIGHT_TO_LEFT, content = operationsGrid(...) )
```

🌐 API Integration

- Currency conversion powered by Frankfurter API
- Uses Retrofit + Moshi for JSON parsing
- Repository + Interactor pattern for clean separation of concerns
