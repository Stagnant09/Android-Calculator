package com.example.calculator.ui.screens.main.triangleCalculator.interactive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class TriangleInteractiveViewModel : CustomViewModel<TriangleInteractiveContract.State, TriangleInteractiveContract.Event, TriangleInteractiveContract.Effect>, ViewModel() {

    private var _uiState = MutableStateFlow(TriangleInteractiveContract.State())
    val uiState: StateFlow<TriangleInteractiveContract.State> = _uiState.asStateFlow()

    private val _effect: Channel<TriangleInteractiveContract.Effect> = Channel()
    val uiEffect: Flow<TriangleInteractiveContract.Effect> = _effect.receiveAsFlow()

    fun setEffect(builder: () -> TriangleInteractiveContract.Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }

    override fun setState(state: TriangleInteractiveContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: TriangleInteractiveContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: TriangleInteractiveContract.Event) {
        when (event) {
            is TriangleInteractiveContract.Event.OnInputChanged -> {
                onInputChanged(event)
            }
        }
    }

    private fun onInputChanged(event: TriangleInteractiveContract.Event.OnInputChanged) {
        val (Ax, Ay, Bx, By, Cx, Cy) = event.coordinates

        // --- Side lengths (using distance formula) ---
        val sideA = hypot(Bx - Cx, By - Cy) // opposite A
        val sideB = hypot(Ax - Cx, Ay - Cy) // opposite B
        val sideC = hypot(Ax - Bx, Ay - By) // opposite C

        // --- Angles (law of cosines) ---
        val angleA = acos(((sideB * sideB) + (sideC * sideC) - (sideA * sideA)) / (2 * sideB * sideC))
        val angleB = acos(((sideA * sideA) + (sideC * sideC) - (sideB * sideB)) / (2 * sideA * sideC))
        val angleC = Math.PI - angleA - angleB

        // --- Trigonometric values ---
        val sinA = sin(angleA)
        val cosA = cos(angleA)
        val tanA = tan(angleA)

        val sinB = sin(angleB)
        val cosB = cos(angleB)
        val tanB = tan(angleB)

        val sinC = sin(angleC)
        val cosC = cos(angleC)
        val tanC = tan(angleC)

        // --- Perimeter & Area (Heron’s formula) ---
        val perimeter = sideA + sideB + sideC
        val s = perimeter / 2
        val area = sqrt(s * (s - sideA) * (s - sideB) * (s - sideC))

        // --- Special points ---
        val centroid = Pair((Ax + Bx + Cx) / 3.0, (Ay + By + Cy) / 3.0)

        val incenter = Pair(
            (sideA * Ax + sideB * Bx + sideC * Cx) / perimeter,
            (sideA * Ay + sideB * By + sideC * Cy) / perimeter
        )

        // Circumcenter (intersection of perpendicular bisectors)
        val d = 2 * (Ax * (By - Cy) + Bx * (Cy - Ay) + Cx * (Ay - By))
        val ux = ((Ax * Ax + Ay * Ay) * (By - Cy) +
                (Bx * Bx + By * By) * (Cy - Ay) +
                (Cx * Cx + Cy * Cy) * (Ay - By)) / d
        val uy = ((Ax * Ax + Ay * Ay) * (Cx - Bx) +
                (Bx * Bx + By * By) * (Ax - Cx) +
                (Cx * Cx + Cy * Cy) * (Bx - Ax)) / d
        val circumcenter = Pair(ux, uy)

        // Orthocenter (intersection of altitudes) – formula with determinant
        val tanA_val = tan(angleA)
        val tanB_val = tan(angleB)
        val tanC_val = tan(angleC)
        val ox = (Ax * tanA_val + Bx * tanB_val + Cx * tanC_val) / (tanA_val + tanB_val + tanC_val)
        val oy = (Ay * tanA_val + By * tanB_val + Cy * tanC_val) / (tanA_val + tanB_val + tanC_val)
        val orthocenter = Pair(ox, oy)

        // --- Update state ---
        setState(
            uiState.value.copy(
                angleA = angleA, angleB = angleB, angleC = angleC,
                sinA = sinA, cosA = cosA, tanA = tanA,
                sinB = sinB, cosB = cosB, tanB = tanB,
                sinC = sinC, cosC = cosC, tanC = tanC,
                sideA = sideA, sideB = sideB, sideC = sideC,
                perimeter = perimeter,
                area = area,
                centroid = centroid,
                incenter = incenter,
                circumcenter = circumcenter,
                orthocenter = orthocenter
            )
        )
    }


}