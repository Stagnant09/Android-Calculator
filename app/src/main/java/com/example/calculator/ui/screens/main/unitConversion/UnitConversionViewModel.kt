package com.example.calculator.ui.screens.main.unitConversion

import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.calculator.models.UnitType

class UnitConversionViewModel : CustomViewModel<UnitConversionContract.State, UnitConversionContract.Event>, ViewModel() {

    private var _uiState = MutableStateFlow(UnitConversionContract.State())
    val uiState: StateFlow<UnitConversionContract.State> = _uiState.asStateFlow()

    override fun setState(state: UnitConversionContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: UnitConversionContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: UnitConversionContract.Event) {
        when (event) {
            is UnitConversionContract.Event.TextFieldEdit.Input -> {
                editAllValues(event.unit, event.value)
            }
            else -> {

            }
        }
    }

    /** This function has as a responsibility to update all the values
     * when there is some change in the UI-set values of the fields.
     * E.g. if the user changes the value of the 'meter' field, the value
     * of the 'centimeter' field should be updated etc.
     */
    private fun editAllValues(unit: Enum<*>, newValue: String) {
        val value = newValue.toFloatOrNull() ?: 0f

        setState(
            when (unit) {
                is com.example.calculator.models.UnitType.LengthUnitType -> {
                    val base = UnitType.LengthConverter.convert(value, unit, UnitType.LengthUnitType.METER)
                    _uiState.value.copy(
                        nanometer = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.NANOMETER
                        ).toString(),
                        micrometer = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.MICROMETER
                        ).toString(),
                        millimeter = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.MILLIMETER
                        ).toString(),
                        centimeter = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.CENTIMETER
                        ).toString(),
                        decimeter = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.DECIMETER
                        ).toString(),
                        foot = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.FOOT
                        ).toString(),
                        inch = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.INCH
                        ).toString(),
                        meter = base.toString(),
                        yard = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.YARD
                        ).toString(),
                        kilometer = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.KILOMETER
                        ).toString(),
                        mile = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.MILE
                        ).toString(),
                        nauticalMile = UnitType.LengthConverter.convert(
                            base,
                            UnitType.LengthUnitType.METER,
                            UnitType.LengthUnitType.NAUTICAL_MILE
                        ).toString(),
                    )
                }

                is UnitType.MassUnitType -> {
                    val base = UnitType.MassConverter.convert(value, unit, UnitType.MassUnitType.KILOGRAM)
                    _uiState.value.copy(
                        kilogram = base.toString(),
                        gram = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.GRAM
                        ).toString(),
                        milligram = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.MILLIGRAM
                        ).toString(),
                        microgram = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.MICROGRAM
                        ).toString(),
                        metricTon = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.METRIC_TON
                        ).toString(),
                        pound = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.POUND
                        ).toString(),
                        ounce = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.OUNCE
                        ).toString(),
                        stone = UnitType.MassConverter.convert(
                            base,
                            UnitType.MassUnitType.KILOGRAM,
                            UnitType.MassUnitType.STONE
                        ).toString(),
                    )
                }

                is UnitType.TimeUnitType -> {
                    val base = UnitType.TimeConverter.convert(value, unit, UnitType.TimeUnitType.SECOND)
                    _uiState.value.copy(
                        second = base.toString(),
                        minute = UnitType.TimeConverter.convert(
                            base,
                            UnitType.TimeUnitType.SECOND,
                            UnitType.TimeUnitType.MINUTE
                        ).toString(),
                        hour = UnitType.TimeConverter.convert(
                            base,
                            UnitType.TimeUnitType.SECOND,
                            UnitType.TimeUnitType.HOUR
                        ).toString(),
                        day = UnitType.TimeConverter.convert(
                            base,
                            UnitType.TimeUnitType.SECOND,
                            UnitType.TimeUnitType.DAY
                        )
                            .toString(),
                        week = UnitType.TimeConverter.convert(
                            base,
                            UnitType.TimeUnitType.SECOND,
                            UnitType.TimeUnitType.WEEK
                        ).toString(),
                    )
                }

                // …repeat this pattern for AreaUnit, VolumeUnit, TemperatureUnit, SpeedUnit, PressureUnit,
                // EnergyUnit, PowerUnit, CurrentUnit, VoltageUnit, ResistanceUnit, AngleUnit …

                else -> _uiState.value
            }
        )


    }

}