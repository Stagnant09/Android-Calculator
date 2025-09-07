package com.example.calculator.ui.screens.main.matrix.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.screens.main.components.ScreenBase
import com.example.calculator.ui.utils.VSpacer

/** A screen that displays information about matrix operations
 * such as formulas and more.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixInfoScreen(
    goBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ScreenBase(
        scrollState = scrollState,
        drawerState = drawerState,
        scope = scope,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = goBack
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text(text = "Info : Matrix Algebra") },
            )
        },
        title = "Info : Matrix Algebra",
    ) {
        MatrixInfoContent()
    }
}

@Composable
fun MatrixInfoContent() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        // --- Definition ---
        Text("📘 Definition", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "A matrix is a rectangular array of numbers arranged in rows and columns. " +
                    "An m×n matrix has m rows and n columns."
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Addition ---
        Text("➕ Addition", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Two matrices A and B of the same size can be added element-wise:\n\n" +
                    "(A + B)[i , j] = A[i , j] + B[i , j]"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Scalar Multiplication ---
        Text("✖ Scalar Multiplication", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Multiplying a matrix A by a scalar λ scales every element:\n\n" +
                    "(λA)[i , j] = λ × A[i , j]"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Matrix Product ---
        Text("✖ Matrix Product", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "If A is an m×n matrix and B is an n×p matrix, their product AB is an m×p matrix defined by:\n\n" +
                    "(AB)[i , j] = Σ (A[i , k] × B[k , j]) for k = 1..n"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Transpose ---
        Text("🔄 Transpose", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "The transpose of A, written Aᵀ, flips rows and columns:\n\n" +
                    "Aᵀ[i , j] = A[j , i]"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Determinant ---
        Text("|A| Determinant", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "The determinant is a scalar value computed from a square matrix."
        )
        VSpacer(8)
        Text(
            "General definition: For an n×n matrix A = [aᵢⱼ], the determinant is\n\n" +
                    "det(A) = Σ (sgn(σ) · a₁,σ(1) · a₂,σ(2) · ... · aₙ,σ(n))\n\n" +
                    "where the sum runs over all permutations σ of {1,…,n}, " +
                    "and sgn(σ) is +1 for even permutations and -1 for odd permutations."
        )
        VSpacer(8)
        Text(
            "For a 2×2 matrix (A = [[a, b], [c, d]]):\n\n" +
                    "det(A) = a·d – b·c"
        )
        VSpacer(8)
        Text(
            "For a 3×3 matrix (A = [[a, b, c], [d, e, f], [g, h, i]]):\n\n" +
                    "det(A) = a(ei – fh) – b(di – fg) + c(dh – eg)"
        )
        VSpacer(8)
        Text(
            "For larger matrices, it is computed recursively using minors and cofactors."
        )
        Spacer(modifier = Modifier.height(20.dp))

        // --- Inverse ---
        Text("A⁻¹ Inverse", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "The inverse of a square matrix A (if it exists) is defined as:\n\n" +
                    "A × A⁻¹ = I\n\n" +
                    "where I is the identity matrix."
        )
        Text(
            "For a 2×2 matrix:\n\n" +
                    "A⁻¹ = (1/det(A)) × [[d, -b], [-c, a]]"
        )

        // --- Important Properties of the determinant and inverse ---
        VSpacer(20)
        Text("Important Properties", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        VSpacer(8)
        Text(
            "• det(Aᵀ) = det(A)\n\n" +
                    "• If A and B are square matrices of the same size, det(A·B) = det(A)·det(B)\n\n" +
                    "• det(c·A) = cⁿ · det(A), where c is a scalar and A is n×n\n\n" +
                    "• If two rows (or two columns) of A are equal, then det(A) = 0\n\n" +
                    "• Swapping two rows (or two columns) multiplies the determinant by –1\n\n" +
                    "• Adding a multiple of one row to another row does not change the determinant\n\n" +
                    "• If A is invertible, then det(A⁻¹) = 1 / det(A)\n\n" +
                    "• det(I) = 1, where I is the identity matrix\n\n" +
                    "• Iff det(A) = 0, then A is singular and has no inverse"
        )
        VSpacer(24)
    }
}


@Preview(showBackground = true)
@Composable
fun MatrixInfoScreenPreview() {
    MatrixInfoScreen(
        goBack = {}
    )
}