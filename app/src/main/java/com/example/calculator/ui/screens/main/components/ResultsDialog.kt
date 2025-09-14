package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.models.Constant
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer

/** A modal that contains results of a search
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsDialog(
    copyAction: (String) -> Unit,
    negativeAction: () -> Unit,
    results: List<Constant>
) {
    val height = results.size * 120
    var query by remember { mutableStateOf("") }

    Dialog(onDismissRequest = negativeAction) {
        Surface(
            modifier = Modifier.height(height.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn {
                    items(results.size) { index ->
                        val result = results[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = result.symbol, modifier = Modifier.weight(1f))
                            Text(text = result.name, modifier = Modifier.weight(4f))
                            Text(text = "%.5f".format(result.value), modifier = Modifier.weight(2f))

                            TextButton(onClick = { copyAction("${result.symbol} = ${result.value}") }) {
                                Text("Copy")
                            }
                        }
                    }
                }

                VSpacer(16)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = negativeAction) {
                        Text("OK")
                    }
                    HSpacer(8)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ResultsDialogPreview() {
    SearchDialog(onInputChange = {}, positiveAction = {}, negativeAction = {})
}
