package com.example.calculator.ui.screens.main.triangleCalculator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.math.sqrt


/** In this screen there is a triangle that can be modified in terms of angles and sides
 * On the top there is a live depiction of that triangle, being modified as the user edits the
 * values of the angles and sides. Below, there are labels and fields that can be edited to
 * modify the angles and sides of the triangle. Finally, there is a label that displays the
 * area of the triangle and the perimeter.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriangleScreen(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        navigateToMain = {},
        navigateToUnitConversion = {},
        drawerState = drawerState
    ) { // Here goes the triangle etc. }

        var a by remember { mutableStateOf(5.0) }
        var b by remember { mutableStateOf(6.0) }
        var c by remember { mutableStateOf(7.0) }

        val isValid = a + b > c && a + c > b && b + c > a
        val perimeter = a + b + c
        val area = if (isValid) {
            val s = perimeter / 2.0
            sqrt(s * (s - a) * (s - b) * (s - c))
        } else 0.0

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Menu),
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Triangle Calculator",
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        ) { contentPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VSpacer(48)
                TriangleCanvas(a = a, b = b, c = c)

                VSpacer(12)
                Text("Sides", style = MaterialTheme.typography.titleMedium)

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    @Composable
                    fun field(
                        label: String,
                        value: Double,
                        onChange: (Double) -> Unit,
                        m: Modifier
                    ) {
                        OutlinedTextField(
                            value = if (value.isNaN()) "" else value.toString(),
                            onValueChange = { onChange(it.toDoubleOrNull() ?: value) },
                            label = { Text(label) },
                            modifier = m
                        )
                    }
                    field(
                        "a (BC)", a, { a = it }, Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    field(
                        "b (AC)", b, { b = it }, Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    field(
                        "c (AB)", c, { c = it }, Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                }

                VSpacer(8)

                /*Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    @Composable
                    fun field(
                        label: String,
                        value: Double,
                        onChange: (Double) -> Unit,
                        m: Modifier
                    ) {
                        OutlinedTextField(
                            value = if (value.isNaN()) "" else value.toString(),
                            onValueChange = { onChange(it.toDoubleOrNull() ?: value) },
                            label = { Text(label) },
                            modifier = m
                        )
                    }
                    field(
                        "Angle A", a, { a = it }, Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    field(
                        "Angle B", b, { b = it }, Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                    field(
                        "Angle C", c, { c = it }, Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                }*/

                Text("Perimeter: ${"%.2f".format(perimeter)}")
                Text("Area: ${if (isValid) "%.2f".format(area) else "—"}")
                if (!isValid) Text("Triangle inequality not satisfied", color = Color.Red)
            }
        }
    }
}

@Composable
fun TriangleCanvas(
    a: Double, // side opposite A  (BC)
    b: Double, // side opposite B  (AC)
    c: Double, // side opposite C  (AB)
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f) // square area looks nice; tweak if you like
        .background(Color(0xFF1E1E1E)) // high contrast dark bg
        .padding(16.dp),
    strokeColor: Color = Color.Cyan,
    labelColor: Color = Color.White
) {
    Canvas(modifier = modifier) {
        fun validTriangle(a: Double, b: Double, c: Double) =
            a + b > c && a + c > b && b + c > a

        if (!validTriangle(a, b, c)) {
            // Helpful message when invalid (no NaN area)
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

        // --- 1) Build triangle in a convenient local space ---
        // Place B = (0,0), C = (a,0). Solve for A using law of cosines on side 'a' (BC).
        val Ax = ((b * b) - (c * c) + (a * a)) / (2 * a)           // double
        val Ay = kotlin.math.sqrt((b * b) - (Ax * Ax))         // double

        // Raw (unscaled) points in double
        val B0 = Offset(0f, 0f)
        val C0 = Offset(a.toFloat(), 0f)
        val A0 = Offset(Ax.toFloat(), Ay.toFloat())

        // --- 2) Fit & center in canvas ---
        val minX = minOf(A0.x, B0.x, C0.x)
        val maxX = maxOf(A0.x, B0.x, C0.x)
        val minY = minOf(A0.y, B0.y, C0.y)
        val maxY = maxOf(A0.y, B0.y, C0.y)

        val triW = maxX - minX
        val triH = maxY - minY

        val margin = 24f // px inside the canvas
        val scale = min(
            (size.width - 2 * margin) / triW,
            (size.height - 2 * margin) / triH
        )

        // Flip Y (math Y up, canvas Y down): we’ll scale then invert Y.
        fun toCanvas(p: Offset): Offset {
            val px = (p.x - minX) * scale + margin
            val pyMathUp = (p.y - minY) * scale + margin
            val py = size.height - pyMathUp // flip
            return Offset(px, py)
        }

        val A = toCanvas(A0)
        val B = toCanvas(B0)
        val C = toCanvas(C0)

        // --- 3) Draw triangle path ---
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(A.x, A.y); lineTo(B.x, B.y); lineTo(C.x, C.y); close()
        }
        drawPath(path, strokeColor, style = Stroke(width = 4f))

        // --- 4) Labels at side midpoints ---
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
        // side labels: a=|BC|, b=|AC|, c=|AB|
        drawLabel("a", midBC - Offset(48f, 48f))
        drawLabel("b", midCA - Offset(-48f, 48f))
        drawLabel("c", midAB - Offset(48f, 48f))

        // --- 5) Angles + markers (arc or right-angle square) ---
        fun angleAt(p: Offset, q: Offset, r: Offset): Double {
            // angle at q formed by vectors (p - q) and (r - q)
            val v1x = (p.x - q.x).toDouble()
            val v1y = (p.y - q.y).toDouble()
            val v2x = (r.x - q.x).toDouble()
            val v2y = (r.y - q.y).toDouble()
            val dot = v1x * v2x + v1y * v2y
            val n1 = kotlin.math.hypot(v1x, v1y)
            val n2 = kotlin.math.hypot(v2x, v2y)
            val cos = (dot / (n1 * n2)).coerceIn(-1.0, 1.0)
            return kotlin.math.acos(cos) // radians
        }

        val angA = angleAt(B, A, C)
        val angB = angleAt(C, B, A)
        val angC = angleAt(A, C, B)

        fun radToDeg(x: Double) = x * 180.0 / Math.PI
        fun isRight(rad: Double) = kotlin.math.abs(radToDeg(rad) - 90.0) < 1.0 // tolerance ~1°

        // Helper: draw a small arc at vertex q between rays to p and r
        fun drawAngleArc(p: Offset, q: Offset, r: Offset, sweepRad: Double, color: Color) {
            // pick small radius relative to triangle size
            val radius = 24f

            // start angle: angle of ray (p - q) in canvas coords
            fun atan2Deg(y: Float, x: Float) =
                Math.toDegrees(kotlin.math.atan2(y.toDouble(), x.toDouble()))

            val vStart = Offset(p.x - q.x, p.y - q.y)
            val startDeg = atan2Deg(vStart.y, vStart.x).toFloat()
            val sweepDeg = radToDeg(sweepRad).toFloat()

            val rect = Rect(
                left = q.x - radius, top = q.y - radius,
                right = q.x + radius, bottom = q.y + radius
            )
            drawArc(
                color = color,
                startAngle = startDeg,
                sweepAngle = sweepDeg,
                useCenter = false,
                topLeft = rect.topLeft,
                size = rect.size,
                style = Stroke(width = 4f)
            )
        }

        // Helper: draw a small right-angle square at q along directions toward p and r
        fun drawRightMarker(p: Offset, q: Offset, r: Offset, color: Color) {
            fun norm(v: Offset): Offset {
                val n = sqrt(v.x * v.x + v.y * v.y)
                return if (n == 0f) Offset.Zero else Offset(v.x / n, v.y / n)
            }

            val u = norm(Offset(p.x - q.x, p.y - q.y))
            val v = norm(Offset(r.x - q.x, r.y - q.y))
            val len = 18f
            val p1 = Offset(q.x + u.x * len, q.y + u.y * len)
            val p2 = Offset(q.x + v.x * len, q.y + v.y * len)
            val p3 = Offset(p1.x + v.x * len, p1.y + v.y * len)
            drawLine(color, q, p1, strokeWidth = 4f)
            drawLine(color, q, p2, strokeWidth = 4f)
            drawLine(color, p1, p3, strokeWidth = 4f)
        }

        /*
        // A
        if (isRight(angA)) drawRightMarker(B, A, C, Color.Red)
        else drawAngleArc(B, A, C, angA, Color.Red)
        // B
        if (isRight(angB)) drawRightMarker(C, B, A, Color.Red)
        else drawAngleArc(C, B, A, angB, Color.Red)
        // C
        if (isRight(angC)) drawRightMarker(A, C, B, Color.Red)
        else drawAngleArc(A, C, B, angC, Color.Red)
         */
    }
}

@Preview(showBackground = true)
@Composable
fun TriangleScreenPreview() {
    TriangleScreen(navigateToMain = {}, navigateToUnitConversion = {})
}
