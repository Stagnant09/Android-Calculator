package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer

/** A modal that contains a search bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialog(
    onInputChange: (String) -> Unit,
    positiveAction: (String) -> Unit,
    negativeAction: () -> Unit,
    placeholder: String = "Search..."
) {
    var query by remember { mutableStateOf("") }
    //var expanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = negativeAction) {
        Surface(
            modifier = Modifier.height(160.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = query,
                            onQueryChange = {
                                query = it
                                onInputChange(it)
                            },
                            onSearch = {
                                positiveAction(query)
                                //expanded = false
                            },
                            expanded = false,
                            onExpandedChange = {  },
                            placeholder = { Text(placeholder) },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null)
                            },
                            trailingIcon = {
                                if (query.isNotEmpty()) {
                                    IconButton(onClick = {
                                        query = ""
                                        onInputChange("")
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "Clear")
                                    }
                                }
                            },
                            colors = SearchBarDefaults.inputFieldColors(),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                    },
                    expanded = false,
                    onExpandedChange = {  },
                    modifier = Modifier.fillMaxWidth(),
                    shape = SearchBarDefaults.inputFieldShape,
                    colors = SearchBarDefaults.colors(),
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp,
                    windowInsets = WindowInsets(0)
                ) {
                    // Optional suggestion items go here
                }

                VSpacer(16)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = negativeAction) {
                        Text("Cancel")
                    }
                    HSpacer(8)
                    Button(onClick = { positiveAction(query) }) {
                        Text("Search")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchDialogPreview() {
    SearchDialog(onInputChange = {}, positiveAction = {}, negativeAction = {})
}
