package com.example.calculator.ui.screens.main.unitConversion

import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.UnitType
import com.example.calculator.models.UnitType.AngleUnitType
import com.example.calculator.models.UnitType.LengthUnitType

sealed interface UnitConversionContract {

    sealed interface Event : CustomEvent {
        sealed interface TextFieldEdit : Event {
            object Clear : TextFieldEdit
            data class Input(val value: String, val unit: Enum<*>) : TextFieldEdit
        }
    }

    data class State(
        // Length
        val nanometer: String = "0.0",
        val micrometer: String = "0.0",
        val millimeter: String = "0.0",
        val centimeter: String = "0.0",
        val decimeter: String = "0.0",
        val foot: String = "0.0",
        val inch: String = "0.0",
        val meter: String = "0.0",
        val yard: String = "0.0",
        val kilometer: String = "0.0",
        val mile: String = "0.0",
        val nauticalMile: String = "0.0",

        // Angle
        val degree: String = "0.0",
        val radian: String = "0.0",
        val gradian: String = "0.0",
        val milliradian: String = "0.0",
        val turn: String = "0.0",

        // Area
        val squareKilometer: String = "0.0",
        val squareMeter: String = "0.0",
        val squareCentimeter: String = "0.0",
        val squareMillimeter: String = "0.0",
        val hectare: String = "0.0",
        val acre: String = "0.0",
        val squareMile: String = "0.0",
        val squareYard: String = "0.0",
        val squareFoot: String = "0.0",
        val squareInch: String = "0.0",

        // Volume
        val liter: String = "0.0",
        val milliliter: String = "0.0",
        val cubicCentimeter: String = "0.0",
        val cubicMillimeter: String = "0.0",
        val usGallon: String = "0.0",
        val usQuart: String = "0.0",
        val usPint: String = "0.0",
        val usCup: String = "0.0",
        val usFluidOunce: String = "0.0",
        val cubicFoot: String = "0.0",
        val cubicInch: String = "0.0",
        val cubicMeter: String = "0.0",

        // Mass
        val kilogram: String = "0.0",
        val gram: String = "0.0",
        val milligram: String = "0.0",
        val microgram: String = "0.0",
        val metricTon: String = "0.0",
        val pound: String = "0.0",
        val ounce: String = "0.0",
        val stone: String = "0.0",

        // Temperature
        val celsius: String = "0.0",
        val fahrenheit: String = "0.0",
        val kelvin: String = "0.0",

        // Time
        val second: String = "0.0",
        val minute: String = "0.0",
        val hour: String = "0.0",
        val day: String = "0.0",
        val week: String = "0.0",

        // Speed
        val metersPerSecond: String = "0.0",
        val kilometersPerHour: String = "0.0",
        val milesPerHour: String = "0.0",
        val knot: String = "0.0",

        // Pressure
        val pascal: String = "0.0",
        val bar: String = "0.0",
        val psi: String = "0.0",
        val atm: String = "0.0",
        val torr: String = "0.0",

        // Energy
        val joule: String = "0.0",
        val calorie: String = "0.0",
        val kilowattHour: String = "0.0",
        val electronvolt: String = "0.0",

        // Power
        val watt: String = "0.0",
        val kilowatt: String = "0.0",
        val horsepower: String = "0.0",

        // Current
        val ampere: String = "0.0",
        val milliampere: String = "0.0",
        val microampere: String = "0.0",

        // Voltage
        val volt: String = "0.0",
        val millivolt: String = "0.0",
        val kilovolt: String = "0.0",

        // Resistance
        val ohm: String = "0.0",
        val kiloohm: String = "0.0",
        val megaohm: String = "0.0",
    ) : CustomState

}

fun UnitConversionContract.State.toUnitMap(): Map<UnitType, String> {
    return mapOf<UnitType, String>(
        // Length
        LengthUnitType.NANOMETER to nanometer,
        LengthUnitType.MICROMETER to micrometer,
        LengthUnitType.MILLIMETER to millimeter,
        LengthUnitType.CENTIMETER to centimeter,
        LengthUnitType.DECIMETER to decimeter,
        LengthUnitType.FOOT to foot,
        LengthUnitType.INCH to inch,
        LengthUnitType.METER to meter,
        LengthUnitType.YARD to yard,
        LengthUnitType.KILOMETER to kilometer,
        LengthUnitType.MILE to mile,
        LengthUnitType.NAUTICAL_MILE to nauticalMile,

        // Angle
        AngleUnitType.DEGREE to degree,
        AngleUnitType.RADIAN to radian,
        AngleUnitType.GRADIAN to gradian,
        AngleUnitType.MILLIRADIAN to milliradian,
        AngleUnitType.TURN to turn

        // ...repeat for other unit types
    )
}
