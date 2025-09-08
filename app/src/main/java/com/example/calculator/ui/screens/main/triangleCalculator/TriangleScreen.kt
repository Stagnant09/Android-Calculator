package com.example.calculator.ui.screens.main.triangleCalculator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch
import kotlin.math.acos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

private enum class EditMode { SIDES, ANGLES }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriangleScreen(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToTriangleInfo: () -> Unit,
    navigateToEquations: () -> Unit,
    navigateToMatrix: () -> Unit,
    navigateToInteractive: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = {},
        navigateToConstants = navigateToConstants,
        navigateToEquations = navigateToEquations,
        navigateToMatrix = navigateToMatrix,
        drawerState = drawerState
    ) {
        // --- numeric model state (the "truth") ---
        var a by remember { mutableStateOf(5.0) }
        var b by remember { mutableStateOf(6.0) }
        var c by remember { mutableStateOf(7.0) }

        var Adeg by remember { mutableStateOf(0.0) } // angle at A, degrees
        var Bdeg by remember { mutableStateOf(0.0) }
        var Cdeg by remember { mutableStateOf(0.0) }

        // which input the user last touched (prevents feedback loops)
        var lastEdited by remember { mutableStateOf(EditMode.SIDES) }

        // --- helpers ---
        fun validTriangle(a: Double, b: Double, c: Double) = a + b > c && a + c > b && b + c > a

        fun safeAcos(v: Double) = acos(v.coerceIn(-1.0, 1.0))

        fun updateAnglesFromSides() {
            if (!validTriangle(a, b, c)) return
            // law of cosines
            val A = Math.toDegrees(safeAcos(((b * b + c * c - a * a) / (2 * b * c))))
            val B = Math.toDegrees(safeAcos(((a * a + c * c - b * b) / (2 * a * c))))
            val C = 180.0 - A - B
            // programmatic update of angle numerics
            Adeg = A
            Bdeg = B
            Cdeg = C
        }

        fun updateSidesFromAngles() {
            // Normalize angles so they sum to 180
            val sumAngles = Adeg + Bdeg + Cdeg
            if (sumAngles <= 0.0) return
            val norm = 180.0 / sumAngles
            val An = Adeg * norm
            val Bn = Bdeg * norm
            val Cn = Cdeg * norm

            // Law of sines => sides proportional to sin(angle)
            val ra = sin(Math.toRadians(An))
            val rb = sin(Math.toRadians(Bn))
            val rc = sin(Math.toRadians(Cn))
            val sumR = ra + rb + rc
            if (sumR <= 0.0) return

            // scale so the perimeter stays close to what it was (keeps overall size)
            val oldPerimeter = a + b + c
            val scale = if (oldPerimeter > 1e-6) oldPerimeter / sumR else 6.0 / sumR

            a = ra * scale
            b = rb * scale
            c = rc * scale

            // update angles to normalized values (so UI reflects the normalization)
            Adeg = An
            Bdeg = Bn
            Cdeg = Cn
        }

        // initialize angles once from initial sides
        LaunchedEffect(Unit) {
            updateAnglesFromSides()
        }

        val isValid = validTriangle(a, b, c)
        val perimeter = a + b + c
        val area = if (isValid) {
            val s = perimeter / 2.0
            sqrt(max(0.0, s * (s - a) * (s - b) * (s - c)))
        } else 0.0

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Menu),
                                contentDescription = "Menu"
                            )
                        }
                    },
                    title = { Text(text = "Triangle Calculator") },
                    actions = {
                        IconButton(onClick = {
                            navigateToTriangleInfo()
                        }) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Info),
                                contentDescription = "Info",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { contentPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VSpacer(64)
                TriangleCanvas(a = a, b = b, c = c)

                VSpacer(12)
                Text("Sides", style = MaterialTheme.typography.titleMedium)

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    NumberField(
                        label = "a (BC)",
                        value = a,
                        onValueChange = { newA ->
                            // user changed a
                            lastEdited = EditMode.SIDES
                            a = newA
                            // recompute angles from new sides
                            updateAnglesFromSides()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    NumberField(
                        label = "b (AC)",
                        value = b,
                        onValueChange = { newB ->
                            lastEdited = EditMode.SIDES
                            b = newB
                            updateAnglesFromSides()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    NumberField(
                        label = "c (AB)",
                        value = c,
                        onValueChange = { newC ->
                            lastEdited = EditMode.SIDES
                            c = newC
                            updateAnglesFromSides()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                }

                VSpacer(8)
                Text("Angles (°)", style = MaterialTheme.typography.titleMedium)

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    NumberField(
                        label = "∠A",
                        value = Adeg,
                        onValueChange = { newAdeg ->
                            lastEdited = EditMode.ANGLES
                            Adeg = newAdeg
                            // when angles change, normalize & update sides immediately
                            updateSidesFromAngles()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    NumberField(
                        label = "∠B",
                        value = Bdeg,
                        onValueChange = { newBdeg ->
                            lastEdited = EditMode.ANGLES
                            Bdeg = newBdeg
                            updateSidesFromAngles()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    NumberField(
                        label = "∠C",
                        value = Cdeg,
                        onValueChange = { newCdeg ->
                            lastEdited = EditMode.ANGLES
                            Cdeg = newCdeg
                            updateSidesFromAngles()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                }

                VSpacer(8)
                Text("Perimeter: ${"%.2f".format(perimeter)}")
                Text("Area: ${if (isValid) "%.2f".format(area) else "—"}")
                if (!isValid) Text("Triangle inequality not satisfied", color = Color.Red)
                VSpacer(24)
                Row {
                    Button(
                        onClick = {
                            navigateToInteractive()
                        }
                    ) {
                        Text("Switch to Interactive Mode")
                    }
                }
            }
        }
    }
}

/**
 * NumberField is friendly to partial input:
 *  - keeps a string while user edits (so backspace/partial numbers work)
 *  - only parses to Double when parse succeeds
 *  - updates the shown text when the numeric value changes programmatically,
 *    but only when the field is NOT focused (so we don't clobber the user's typing)
 */
@Composable
fun NumberField(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(if (value.isFinite()) "%.2f".format(value) else "") }
    var isFocused by remember { mutableStateOf(false) }

    // When the external numeric value changes (programmatic update), refresh the displayed text
    // only when the field is not being edited (not focused).
    LaunchedEffect(value, isFocused) {
        if (!isFocused) {
            text = if (value.isFinite()) "%.2f".format(value) else ""
        }
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            // only call numeric update when parse succeeds (allows partial input)
            newText.toDoubleOrNull()?.let { onValueChange(it) }
        },
        label = { Text(label) },
        modifier = modifier.onFocusChanged { isFocused = it.isFocused },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun TriangleCanvas(
    a: Double, // side opposite A  (BC)
    b: Double, // side opposite B  (AC)
    c: Double, // side opposite C  (AB)
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .background(Color(0xFF1E1E1E))
        .padding(16.dp),
    strokeColor: Color = Color.Cyan,
    labelColor: Color = Color.White
) {
    Canvas(modifier = modifier) {
        fun validTriangle(a: Double, b: Double, c: Double) =
            a + b > c && a + c > b && b + c > a

        if (!validTriangle(a, b, c)) {
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 42f
                }
                drawText("Enter valid sides", size.width / 2f, size.height / 2f, paint)
            }
            return@Canvas
        }

        // Build triangle in local space with B=(0,0), C=(a,0)
        val Ax = ((b * b) - (c * c) + (a * a)) / (2 * a)
        val AySq = (b * b) - (Ax * Ax)
        val Ay = if (AySq <= 0.0) 0.0 else kotlin.math.sqrt(AySq)

        val B0 = Offset(0f, 0f)
        val C0 = Offset(a.toFloat(), 0f)
        val A0 = Offset(Ax.toFloat(), Ay.toFloat())

        val minX = minOf(A0.x, B0.x, C0.x)
        val maxX = maxOf(A0.x, B0.x, C0.x)
        val minY = minOf(A0.y, B0.y, C0.y)
        val maxY = maxOf(A0.y, B0.y, C0.y)

        val triW = maxX - minX
        val triH = maxY - minY

        val margin = 24f
        val scale = min(
            (size.width - 2 * margin) / max(1f, triW),
            (size.height - 2 * margin) / max(1f, triH)
        )

        fun toCanvas(p: Offset): Offset {
            val px = (p.x - minX) * scale + margin
            val pyMathUp = (p.y - minY) * scale + margin
            val py = size.height - pyMathUp
            return Offset(px, py)
        }

        val A = toCanvas(A0)
        val B = toCanvas(B0)
        val C = toCanvas(C0)

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(A.x, A.y); lineTo(B.x, B.y); lineTo(C.x, C.y); close()
        }
        drawPath(path, strokeColor, style = Stroke(width = 4f))

        fun mid(p: Offset, q: Offset) = Offset((p.x + q.x) / 2f, (p.y + q.y) / 2f)
        val midAB = mid(A, B)
        val midBC = mid(B, C)
        val midCA = mid(C, A)

        fun drawLabel(text: String, at: Offset) {
            drawContext.canvas.nativeCanvas.drawText(
                text,
                at.x, at.y,
                android.graphics.Paint().apply {
                    color = labelColor.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 36f
                    isAntiAlias = true
                }
            )
        }
        drawLabel("a", midBC - Offset(48f, 48f))
        drawLabel("b", midCA - Offset(-48f, 48f))
        drawLabel("c", midAB - Offset(48f, 48f))

        // angle calculations for possible future display (not mandatory here)
        fun angleAt(p: Offset, q: Offset, r: Offset): Double {
            val v1x = (p.x - q.x).toDouble()
            val v1y = (p.y - q.y).toDouble()
            val v2x = (r.x - q.x).toDouble()
            val v2y = (r.y - q.y).toDouble()
            val dot = v1x * v2x + v1y * v2y
            val n1 = hypot(v1x, v1y)
            val n2 = hypot(v2x, v2y)
            val cos = (dot / (n1 * n2)).coerceIn(-1.0, 1.0)
            return acos(cos)
        }

        val angA = angleAt(B, A, C)
        val angB = angleAt(C, B, A)
        val angC = angleAt(A, C, B)

        // TODO: add markers logic here
    }
}

@Preview(showBackground = true)
@Composable
fun TriangleScreenPreview() {
    TriangleScreen(
        navigateToMain = {},
        navigateToUnitConversion = {},
        navigateToConstants = {},
        navigateToTriangleInfo = {},
        navigateToEquations = {},
        navigateToMatrix = {},
        navigateToInteractive = {}
        )
}
