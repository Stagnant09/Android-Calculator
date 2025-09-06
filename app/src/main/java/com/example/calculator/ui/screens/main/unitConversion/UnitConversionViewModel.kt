package com.example.calculator.ui.screens.main.unitConversion

import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.calculator.models.UnitType

class UnitConversionViewModel : CustomViewModel<UnitConversionContract.State, UnitConversionContract.Event, UnitConversionContract.Effect>, ViewModel() {

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

                is UnitType.AreaUnitType -> {
                    val base = UnitType.AreaConverter.convert(value, unit, UnitType.AreaUnitType.SQUARE_METER)
                    _uiState.value.copy(
                        squareMeter = base.toString(),
                        squareCentimeter = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_CENTIMETER
                        ).toString(),
                        squareMillimeter = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_MILLIMETER
                        ).toString(),
                        squareKilometer = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_KILOMETER
                        ).toString(),
                        hectare = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.HECTARE
                        ).toString(),
                        acre = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.ACRE
                        ).toString(),
                        squareFoot = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_FOOT
                        ).toString(),
                        squareInch = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_INCH
                        ).toString(),
                        squareYard = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_YARD
                        ).toString(),
                        squareMile = UnitType.AreaConverter.convert(
                            base,
                            UnitType.AreaUnitType.SQUARE_METER,
                            UnitType.AreaUnitType.SQUARE_MILE
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

                is UnitType.AngleUnitType -> {
                    val base = UnitType.AngleConverter.convert(value, unit, UnitType.AngleUnitType.RADIAN)
                    _uiState.value.copy(
                        radian = base.toString(),
                        degree = UnitType.AngleConverter.convert(
                            base,
                            UnitType.AngleUnitType.RADIAN,
                            UnitType.AngleUnitType.DEGREE
                        ).toString(),
                        gradian = UnitType.AngleConverter.convert(
                            base,
                            UnitType.AngleUnitType.RADIAN,
                            UnitType.AngleUnitType.GRADIAN
                        ).toString(),
                        milliradian = UnitType.AngleConverter.convert(
                            base,
                            UnitType.AngleUnitType.RADIAN,
                            UnitType.AngleUnitType.MILLIRADIAN
                        ).toString(),
                        turn = UnitType.AngleConverter.convert(
                            base,
                            UnitType.AngleUnitType.RADIAN,
                            UnitType.AngleUnitType.TURN
                        ).toString(),
                    )
                }

                is UnitType.VolumeUnitType -> {
                    val base = UnitType.VolumeConverter.convert(value, unit, UnitType.VolumeUnitType.LITER)
                    _uiState.value.copy(
                        liter = base.toString(),
                        milliliter = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.MILLILITER
                        ).toString(),
                        cubicMeter = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.CUBIC_METER
                        ).toString(),
                        cubicFoot = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.CUBIC_FOOT
                        ).toString(),
                        cubicInch = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.CUBIC_INCH
                        ).toString(),
                        usGallon = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.US_GALLON
                        ).toString(),
                        usCup = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.US_CUP
                        ).toString(),
                        usPint = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.US_PINT
                        ).toString(),
                        usQuart = UnitType.VolumeConverter.convert(
                            base,
                            UnitType.VolumeUnitType.LITER,
                            UnitType.VolumeUnitType.US_QUART
                        ).toString(),
                    )
                }

                is UnitType.TemperatureUnitType -> {
                    val base = UnitType.TemperatureConverter.convert(value, unit, UnitType.TemperatureUnitType.CELSIUS)
                    _uiState.value.copy(
                        celsius = base.toString(),
                        fahrenheit = UnitType.TemperatureConverter.convert(
                            base,
                            UnitType.TemperatureUnitType.CELSIUS,
                            UnitType.TemperatureUnitType.FAHRENHEIT
                        ).toString(),
                        kelvin = UnitType.TemperatureConverter.convert(
                            base,
                            UnitType.TemperatureUnitType.CELSIUS,
                            UnitType.TemperatureUnitType.KELVIN
                        ).toString(),
                    )
                }

                is UnitType.SpeedUnitType -> {
                    val base = UnitType.SpeedConverter.convert(value, unit, UnitType.SpeedUnitType.METERS_PER_SECOND)
                    _uiState.value.copy(
                        metersPerSecond = base.toString(),
                        kilometersPerHour = UnitType.SpeedConverter.convert(
                            base,
                            UnitType.SpeedUnitType.METERS_PER_SECOND,
                            UnitType.SpeedUnitType.KILOMETERS_PER_HOUR
                        ).toString(),
                        milesPerHour = UnitType.SpeedConverter.convert(
                            base,
                            UnitType.SpeedUnitType.METERS_PER_SECOND,
                            UnitType.SpeedUnitType.MILES_PER_HOUR
                        ).toString(),
                        knot = UnitType.SpeedConverter.convert(
                            base,
                            UnitType.SpeedUnitType.METERS_PER_SECOND,
                            UnitType.SpeedUnitType.KNOT
                        ).toString(),
                    )
                }

                is UnitType.PressureUnitType -> {
                    val base = UnitType.PressureConverter.convert(value, unit, UnitType.PressureUnitType.PASCAL)
                    _uiState.value.copy(
                        pascal = base.toString(),
                        atm = UnitType.PressureConverter.convert(
                            base,
                            UnitType.PressureUnitType.PASCAL,
                            UnitType.PressureUnitType.ATM
                        ).toString(),
                        bar = UnitType.PressureConverter.convert(
                            base,
                            UnitType.PressureUnitType.PASCAL,
                            UnitType.PressureUnitType.BAR
                        ).toString(),
                        psi = UnitType.PressureConverter.convert(
                            base,
                            UnitType.PressureUnitType.PASCAL,
                            UnitType.PressureUnitType.PSI
                        ).toString(),
                        torr = UnitType.PressureConverter.convert(
                            base,
                            UnitType.PressureUnitType.PASCAL,
                            UnitType.PressureUnitType.TORR
                        ).toString(),
                    )
                }

                is UnitType.EnergyUnitType -> {
                    val base = UnitType.EnergyConverter.convert(value, unit, UnitType.EnergyUnitType.JOULE)
                    _uiState.value.copy(
                        joule = base.toString(),
                        calorie = UnitType.EnergyConverter.convert(
                            base,
                            UnitType.EnergyUnitType.JOULE,
                            UnitType.EnergyUnitType.CALORIE
                        ).toString(),
                        kilowattHour = UnitType.EnergyConverter.convert(
                            base,
                            UnitType.EnergyUnitType.JOULE,
                            UnitType.EnergyUnitType.KILOWATT_HOUR
                        ).toString(),
                        electronvolt = UnitType.EnergyConverter.convert(
                            base,
                            UnitType.EnergyUnitType.JOULE,
                            UnitType.EnergyUnitType.ELECTRONVOLT
                        ).toString(),
                    )
                }

                is UnitType.PowerUnitType -> {
                    val base = UnitType.PowerConverter.convert(value, unit, UnitType.PowerUnitType.WATT)
                    _uiState.value.copy(
                        watt = base.toString(),
                        kilowatt = UnitType.PowerConverter.convert(
                            base,
                            UnitType.PowerUnitType.WATT,
                            UnitType.PowerUnitType.KILOWATT
                        ).toString(),
                        horsepower = UnitType.PowerConverter.convert(
                            base,
                            UnitType.PowerUnitType.WATT,
                            UnitType.PowerUnitType.HORSEPOWER
                        ).toString(),
                    )
                }

                is UnitType.CurrentUnitType -> {
                    val base = UnitType.CurrentConverter.convert(value, unit, UnitType.CurrentUnitType.AMPERE)
                    _uiState.value.copy(
                        ampere = base.toString(),
                        milliampere = UnitType.CurrentConverter.convert(
                            base,
                            UnitType.CurrentUnitType.AMPERE,
                            UnitType.CurrentUnitType.MILLIAMPERE
                        ).toString(),
                        microampere = UnitType.CurrentConverter.convert(
                            base,
                            UnitType.CurrentUnitType.AMPERE,
                            UnitType.CurrentUnitType.MICROAMPERE
                        ).toString(),
                    )
                }

                is UnitType.StorageUnitType -> {
                    val base = UnitType.StorageConverter.convert(value, unit, UnitType.StorageUnitType.BIT)
                    _uiState.value.copy(
                        bit = base.toString(),
                        byte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.BYTE
                        ).toString(),
                        kilobit = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.KILOBIT
                        ).toString(),
                        kilobyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.KILOBYTE
                        ).toString(),
                        megabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.MEGABYTE
                        ).toString(),
                        gigabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.GIGABYTE
                        ).toString(),
                        terabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.TERABYTE
                        ).toString(),
                        petabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.PETABYTE
                        ).toString(),
                        exabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.EXABYTE
                        ).toString(),
                        zettabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.ZETTABYTE
                        ).toString(),
                        yottabyte = UnitType.StorageConverter.convert(
                            base,
                            UnitType.StorageUnitType.BIT,
                            UnitType.StorageUnitType.YOTTABYTE
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