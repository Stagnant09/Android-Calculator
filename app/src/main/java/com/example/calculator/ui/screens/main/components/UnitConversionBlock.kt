package com.example.calculator.ui.screens.main.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.calculator.models.UnitType
import com.example.calculator.models.unitName
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionContract
import com.example.calculator.ui.utils.toTitleCase
import kotlin.reflect.KClass

fun <T : Enum<T>> enumClassesFromKClass(kClass: KClass<T>): List<KClass<out T>> {
    return kClass.java.enumConstants.map { it::class }
}

/** A unit conversion block is a block of labels and inputs
 * so that the user can convert from one unit to another
 */
@Composable
fun UnitConversionBlock(
    unitType: KClass<out Enum<*>>,
    onEventSent: (UnitConversionContract.Event.TextFieldEdit) -> Unit,
    allValues: Map<UnitType, String>
) {
    var expanded by remember { mutableStateOf(false) }

    val entries: List<Enum<*>> = unitType.java.enumConstants.toList()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(8.dp)
    ) {
        // Header row with arrow + title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = unitName(unitType.simpleName ?: "Unit"),
                fontWeight = FontWeight.Bold
            )
        }

        if (expanded) {
            Spacer(Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
                .verticalScroll(scrollState)
            ) {
                for (unit in entries) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Box( // Use a Box to control the alignment of the Text within the weighted cell
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = unit.name.toTitleCase()
                            )
                        }
                        Log.d("UnitConversionBlock", "unit: $unit$ value: ${allValues[unit as UnitType] ?: ""}")
                        OutlinedTextField(
                            value = allValues[unit as UnitType] ?: "",
                            onValueChange = { newValue ->
                                onEventSent(
                                    UnitConversionContract.Event.TextFieldEdit.Input(
                                        newValue, unit
                                    )
                                )
                            },
                            textStyle = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.weight(2f).height(48.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }
                }
            }
        }
    }
}
