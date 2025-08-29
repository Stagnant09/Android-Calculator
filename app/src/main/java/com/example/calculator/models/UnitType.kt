package com.example.calculator.models

import kotlin.math.PI

private const val WATT_IN_WATT = 1f
private const val KILOWATT_IN_WATT = 1000f
private const val HORSEPOWER_IN_WATT = 745.7f

sealed interface UnitType {

    object LengthConverter {

        // Conversion factors to 1 Meter (as per your original data class)
        private const val NANOMETER_IN_METERS = 0.000000001f
        private const val MICROMETER_IN_METERS = 0.000001f
        private const val MILLIMETER_IN_METERS = 0.001f
        private const val CENTIMETER_IN_METERS = 0.01f
        private const val DECIMETER_IN_METERS = 0.1f
        private const val FOOT_IN_METERS = 0.3048f
        private const val INCH_IN_METERS = 0.0254f
        private const val METER_IN_METERS = 1.0f // Base unit
        private const val YARD_IN_METERS = 0.9144f
        private const val KILOMETER_IN_METERS = 1000.0f
        private const val MILE_IN_METERS = 1609.34f
        private const val NAUTICAL_MILE_IN_METERS = 1852.0f

        // --- Conversion Functions: From Unit to Meters ---

        fun nanometersToMeters(nanometers: Float): Float = nanometers * NANOMETER_IN_METERS
        fun micrometersToMeters(micrometers: Float): Float = micrometers * MICROMETER_IN_METERS
        fun millimetersToMeters(millimeters: Float): Float = millimeters * MILLIMETER_IN_METERS
        fun centimetersToMeters(centimeters: Float): Float = centimeters * CENTIMETER_IN_METERS
        fun decimetersToMeters(decimeters: Float): Float = decimeters * DECIMETER_IN_METERS
        fun feetToMeters(feet: Float): Float = feet * FOOT_IN_METERS
        fun inchesToMeters(inches: Float): Float = inches * INCH_IN_METERS
        fun metersToMeters(meters: Float): Float = meters * METER_IN_METERS // or simply: meters
        fun yardsToMeters(yards: Float): Float = yards * YARD_IN_METERS
        fun kilometersToMeters(kilometers: Float): Float = kilometers * KILOMETER_IN_METERS
        fun milesToMeters(miles: Float): Float = miles * MILE_IN_METERS
        fun nauticalMilesToMeters(nauticalMiles: Float): Float =
            nauticalMiles * NAUTICAL_MILE_IN_METERS

        // --- Conversion Functions: From Meters to Unit ---

        fun metersToNanometers(meters: Float): Float = meters / NANOMETER_IN_METERS
        fun metersToMicrometers(meters: Float): Float = meters / MICROMETER_IN_METERS
        fun metersToMillimeters(meters: Float): Float = meters / MILLIMETER_IN_METERS
        fun metersToCentimeters(meters: Float): Float = meters / CENTIMETER_IN_METERS
        fun metersToDecimeters(meters: Float): Float = meters / DECIMETER_IN_METERS
        fun metersToFeet(meters: Float): Float = meters / FOOT_IN_METERS
        fun metersToInches(meters: Float): Float = meters / INCH_IN_METERS
        fun metersToMetersInverse(meters: Float): Float =
            meters / METER_IN_METERS // or simply: meters

        fun metersToYards(meters: Float): Float = meters / YARD_IN_METERS
        fun metersToKilometers(meters: Float): Float = meters / KILOMETER_IN_METERS
        fun metersToMiles(meters: Float): Float = meters / MILE_IN_METERS
        fun metersToNauticalMiles(meters: Float): Float = meters / NAUTICAL_MILE_IN_METERS

        /**
         * Generic conversion function.
         * Converts a value from a specified 'fromUnit' to a specified 'toUnit'.
         */
        fun convert(value: Float, fromUnit: LengthUnitType, toUnit: LengthUnitType): Float {
            if (fromUnit == toUnit) return value

            // 1. Convert 'fromUnit' to meters (the base unit)
            val valueInMeters = when (fromUnit) {
                LengthUnitType.NANOMETER -> nanometersToMeters(value)
                LengthUnitType.MICROMETER -> micrometersToMeters(value)
                LengthUnitType.MILLIMETER -> millimetersToMeters(value)
                LengthUnitType.CENTIMETER -> centimetersToMeters(value)
                LengthUnitType.DECIMETER -> decimetersToMeters(value)
                LengthUnitType.FOOT -> feetToMeters(value)
                LengthUnitType.INCH -> inchesToMeters(value)
                LengthUnitType.METER -> metersToMeters(value)
                LengthUnitType.YARD -> yardsToMeters(value)
                LengthUnitType.KILOMETER -> kilometersToMeters(value)
                LengthUnitType.MILE -> milesToMeters(value)
                LengthUnitType.NAUTICAL_MILE -> nauticalMilesToMeters(value)
            }

            // 2. Convert from meters to 'toUnit'
            return when (toUnit) {
                LengthUnitType.NANOMETER -> metersToNanometers(valueInMeters)
                LengthUnitType.MICROMETER -> metersToMicrometers(valueInMeters)
                LengthUnitType.MILLIMETER -> metersToMillimeters(valueInMeters)
                LengthUnitType.CENTIMETER -> metersToCentimeters(valueInMeters)
                LengthUnitType.DECIMETER -> metersToDecimeters(valueInMeters)
                LengthUnitType.FOOT -> metersToFeet(valueInMeters)
                LengthUnitType.INCH -> metersToInches(valueInMeters)
                LengthUnitType.METER -> metersToMetersInverse(valueInMeters) // or simply valueInMeters
                LengthUnitType.YARD -> metersToYards(valueInMeters)
                LengthUnitType.KILOMETER -> metersToKilometers(valueInMeters)
                LengthUnitType.MILE -> metersToMiles(valueInMeters)
                LengthUnitType.NAUTICAL_MILE -> metersToNauticalMiles(valueInMeters)
            }
        }
    }

    // Enum to represent the units, useful for the generic convert function
    enum class LengthUnitType() : UnitType {
        NANOMETER, MICROMETER, MILLIMETER, CENTIMETER, DECIMETER,
        FOOT, INCH, METER, YARD, KILOMETER, MILE, NAUTICAL_MILE
    }

    object AngleConverter {

        // Conversion factors to 1 Radian
        private const val DEGREE_IN_RADIANS = PI.toFloat() / 180.0f
        private const val GRADIAN_IN_RADIANS = PI.toFloat() / 200.0f
        private const val MILLIRADIAN_IN_RADIANS = 0.001f
        private const val TURN_IN_RADIANS = 2.0f * PI.toFloat()
        private const val RADIAN_IN_RADIANS = 1.0f // Base unit

        // --- From Unit to Radians ---
        fun degreesToRadians(degrees: Float): Float = degrees * DEGREE_IN_RADIANS
        fun gradiansToRadians(gradians: Float): Float = gradians * GRADIAN_IN_RADIANS
        fun milliradiansToRadians(milliradians: Float): Float =
            milliradians * MILLIRADIAN_IN_RADIANS

        fun turnsToRadians(turns: Float): Float = turns * TURN_IN_RADIANS
        fun radiansToRadians(radians: Float): Float = radians // or radians * RADIAN_IN_RADIANS

        // --- From Radians to Unit ---
        fun radiansToDegrees(radians: Float): Float = radians / DEGREE_IN_RADIANS
        fun radiansToGradians(radians: Float): Float = radians / GRADIAN_IN_RADIANS
        fun radiansToMilliradians(radians: Float): Float = radians / MILLIRADIAN_IN_RADIANS
        fun radiansToTurns(radians: Float): Float = radians / TURN_IN_RADIANS

        fun convert(value: Float, fromUnit: AngleUnitType, toUnit: AngleUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInRadians = when (fromUnit) {
                AngleUnitType.DEGREE -> degreesToRadians(value)
                AngleUnitType.GRADIAN -> gradiansToRadians(value)
                AngleUnitType.MILLIRADIAN -> milliradiansToRadians(value)
                AngleUnitType.TURN -> turnsToRadians(value)
                AngleUnitType.RADIAN -> radiansToRadians(value)
            }
            return when (toUnit) {
                AngleUnitType.DEGREE -> radiansToDegrees(valueInRadians)
                AngleUnitType.GRADIAN -> radiansToGradians(valueInRadians)
                AngleUnitType.MILLIRADIAN -> radiansToMilliradians(valueInRadians)
                AngleUnitType.TURN -> radiansToTurns(valueInRadians)
                AngleUnitType.RADIAN -> valueInRadians // or radiansToRadians(valueInRadians)
            }
        }
    }

    enum class AngleUnitType : UnitType {
        DEGREE, RADIAN, GRADIAN, MILLIRADIAN, TURN
    }

    object AreaConverter {

        // Conversion factors to 1 Square Meter (m²)
        private const val SQUARE_KILOMETER_IN_SQ_METERS = 1000.0f * 1000.0f
        private const val SQUARE_CENTIMETER_IN_SQ_METERS = 0.01f * 0.01f
        private const val SQUARE_MILLIMETER_IN_SQ_METERS = 0.001f * 0.001f
        private const val HECTARE_IN_SQ_METERS = 10000.0f // 100m * 100m
        private const val ACRE_IN_SQ_METERS = 4046.86f
        private const val SQUARE_MILE_IN_SQ_METERS = 1609.34f * 1609.34f
        private const val SQUARE_YARD_IN_SQ_METERS = 0.9144f * 0.9144f
        private const val SQUARE_FOOT_IN_SQ_METERS = 0.3048f * 0.3048f
        private const val SQUARE_INCH_IN_SQ_METERS = 0.0254f * 0.0254f
        private const val SQUARE_METER_IN_SQ_METERS = 1.0f // Base Unit

        // --- From Unit to Square Meters ---
        fun squareKilometersToSquareMeters(sqKm: Float): Float =
            sqKm * SQUARE_KILOMETER_IN_SQ_METERS

        fun squareCentimetersToSquareMeters(sqCm: Float): Float =
            sqCm * SQUARE_CENTIMETER_IN_SQ_METERS

        // ... (add all other units: mm², ha, ac, mi², yd², ft², in²)
        fun squareMetersToSquareMeters(sqM: Float): Float = sqM // Base

        // --- From Square Meters to Unit ---
        fun squareMetersToSquareKilometers(sqM: Float): Float = sqM / SQUARE_KILOMETER_IN_SQ_METERS
        fun squareMetersToSquareCentimeters(sqM: Float): Float =
            sqM / SQUARE_CENTIMETER_IN_SQ_METERS
        // ... (add all other units)

        fun convert(value: Float, fromUnit: AreaUnitType, toUnit: AreaUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInSqMeters = when (fromUnit) {
                AreaUnitType.SQUARE_KILOMETER -> squareKilometersToSquareMeters(value)
                AreaUnitType.SQUARE_CENTIMETER -> squareCentimetersToSquareMeters(value)
                AreaUnitType.SQUARE_MILLIMETER -> value * SQUARE_MILLIMETER_IN_SQ_METERS
                AreaUnitType.HECTARE -> value * HECTARE_IN_SQ_METERS
                AreaUnitType.ACRE -> value * ACRE_IN_SQ_METERS
                AreaUnitType.SQUARE_MILE -> value * SQUARE_MILE_IN_SQ_METERS
                AreaUnitType.SQUARE_YARD -> value * SQUARE_YARD_IN_SQ_METERS
                AreaUnitType.SQUARE_FOOT -> value * SQUARE_FOOT_IN_SQ_METERS
                AreaUnitType.SQUARE_INCH -> value * SQUARE_INCH_IN_SQ_METERS
                AreaUnitType.SQUARE_METER -> value // Base
            }
            return when (toUnit) {
                AreaUnitType.SQUARE_KILOMETER -> valueInSqMeters / SQUARE_KILOMETER_IN_SQ_METERS
                AreaUnitType.SQUARE_CENTIMETER -> valueInSqMeters / SQUARE_CENTIMETER_IN_SQ_METERS
                AreaUnitType.SQUARE_MILLIMETER -> valueInSqMeters / SQUARE_MILLIMETER_IN_SQ_METERS
                AreaUnitType.HECTARE -> valueInSqMeters / HECTARE_IN_SQ_METERS
                AreaUnitType.ACRE -> valueInSqMeters / ACRE_IN_SQ_METERS
                AreaUnitType.SQUARE_MILE -> valueInSqMeters / SQUARE_MILE_IN_SQ_METERS
                AreaUnitType.SQUARE_YARD -> valueInSqMeters / SQUARE_YARD_IN_SQ_METERS
                AreaUnitType.SQUARE_FOOT -> valueInSqMeters / SQUARE_FOOT_IN_SQ_METERS
                AreaUnitType.SQUARE_INCH -> valueInSqMeters / SQUARE_INCH_IN_SQ_METERS
                AreaUnitType.SQUARE_METER -> valueInSqMeters // Base
            }
        }
    }

    enum class AreaUnitType : UnitType {
        SQUARE_KILOMETER, SQUARE_METER, SQUARE_CENTIMETER, SQUARE_MILLIMETER,
        HECTARE, ACRE, SQUARE_MILE, SQUARE_YARD, SQUARE_FOOT, SQUARE_INCH
    }

    object VolumeConverter {
        private const val LITER_IN_CUBIC_METERS = 0.001f
        private const val MILLILITER_IN_CUBIC_METERS = 0.000001f
        private const val CUBIC_CENTIMETER_IN_CUBIC_METERS = 0.000001f
        private const val CUBIC_MILLIMETER_IN_CUBIC_METERS = 0.000000001f
        private const val US_GALLON_IN_CUBIC_METERS = 0.00378541f
        private const val US_QUART_IN_CUBIC_METERS = 0.000946353f
        private const val US_PINT_IN_CUBIC_METERS = 0.000473176f
        private const val US_CUP_IN_CUBIC_METERS = 0.000236588f
        private const val US_FLUID_OUNCE_IN_CUBIC_METERS = 0.0000295735f
        private const val CUBIC_FOOT_IN_CUBIC_METERS = 0.0283168f
        private const val CUBIC_INCH_IN_CUBIC_METERS = 0.0000163871f
        private const val CUBIC_METER_IN_CUBIC_METERS = 1.0f


        fun convert(value: Float, fromUnit: VolumeUnitType, toUnit: VolumeUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInCubicMeters = when (fromUnit) {
                VolumeUnitType.LITER -> value * LITER_IN_CUBIC_METERS
                VolumeUnitType.MILLILITER -> value * MILLILITER_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_CENTIMETER -> value * CUBIC_CENTIMETER_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_MILLIMETER -> value * CUBIC_MILLIMETER_IN_CUBIC_METERS
                VolumeUnitType.US_GALLON -> value * US_GALLON_IN_CUBIC_METERS
                VolumeUnitType.US_QUART -> value * US_QUART_IN_CUBIC_METERS
                VolumeUnitType.US_PINT -> value * US_PINT_IN_CUBIC_METERS
                VolumeUnitType.US_CUP -> value * US_CUP_IN_CUBIC_METERS
                VolumeUnitType.US_FLUID_OUNCE -> value * US_FLUID_OUNCE_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_FOOT -> value * CUBIC_FOOT_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_INCH -> value * CUBIC_INCH_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_METER -> value
            }
            return when (toUnit) {
                VolumeUnitType.LITER -> valueInCubicMeters / LITER_IN_CUBIC_METERS
                VolumeUnitType.MILLILITER -> valueInCubicMeters / MILLILITER_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_CENTIMETER -> valueInCubicMeters / CUBIC_CENTIMETER_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_MILLIMETER -> valueInCubicMeters / CUBIC_MILLIMETER_IN_CUBIC_METERS
                VolumeUnitType.US_GALLON -> valueInCubicMeters / US_GALLON_IN_CUBIC_METERS
                VolumeUnitType.US_QUART -> valueInCubicMeters / US_QUART_IN_CUBIC_METERS
                VolumeUnitType.US_PINT -> valueInCubicMeters / US_PINT_IN_CUBIC_METERS
                VolumeUnitType.US_CUP -> valueInCubicMeters / US_CUP_IN_CUBIC_METERS
                VolumeUnitType.US_FLUID_OUNCE -> valueInCubicMeters / US_FLUID_OUNCE_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_FOOT -> valueInCubicMeters / CUBIC_FOOT_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_INCH -> valueInCubicMeters / CUBIC_INCH_IN_CUBIC_METERS
                VolumeUnitType.CUBIC_METER -> valueInCubicMeters
            }
        }
    }

    enum class VolumeUnitType : UnitType { LITER, MILLILITER, CUBIC_CENTIMETER, CUBIC_MILLIMETER, US_GALLON, US_QUART, US_PINT, US_CUP, US_FLUID_OUNCE, CUBIC_FOOT, CUBIC_INCH, CUBIC_METER }

    object MassConverter {
        private const val GRAM_IN_KG = 0.001f
        private const val MILLIGRAM_IN_KG = 0.000001f
        private const val MICROGRAM_IN_KG = 0.000000001f
        private const val METRIC_TON_IN_KG = 1000.0f
        private const val POUND_IN_KG = 0.453592f
        private const val OUNCE_IN_KG = 0.0283495f
        private const val STONE_IN_KG = 6.35029f
        private const val KILOGRAM_IN_KG = 1.0f


        fun convert(value: Float, fromUnit: MassUnitType, toUnit: MassUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInKg = when (fromUnit) {
                MassUnitType.GRAM -> value * GRAM_IN_KG
                MassUnitType.MILLIGRAM -> value * MILLIGRAM_IN_KG
                MassUnitType.MICROGRAM -> value * MICROGRAM_IN_KG
                MassUnitType.METRIC_TON -> value * METRIC_TON_IN_KG
                MassUnitType.POUND -> value * POUND_IN_KG
                MassUnitType.OUNCE -> value * OUNCE_IN_KG
                MassUnitType.STONE -> value * STONE_IN_KG
                MassUnitType.KILOGRAM -> value
            }
            return when (toUnit) {
                MassUnitType.GRAM -> valueInKg / GRAM_IN_KG
                MassUnitType.MILLIGRAM -> valueInKg / MILLIGRAM_IN_KG
                MassUnitType.MICROGRAM -> valueInKg / MICROGRAM_IN_KG
                MassUnitType.METRIC_TON -> valueInKg / METRIC_TON_IN_KG
                MassUnitType.POUND -> valueInKg / POUND_IN_KG
                MassUnitType.OUNCE -> valueInKg / OUNCE_IN_KG
                MassUnitType.STONE -> valueInKg / STONE_IN_KG
                MassUnitType.KILOGRAM -> valueInKg
            }
        }
    }

    enum class MassUnitType : UnitType { KILOGRAM, GRAM, MILLIGRAM, MICROGRAM, METRIC_TON, POUND, OUNCE, STONE }

    object TemperatureConverter {

        // --- Direct Conversion Functions ---
        fun celsiusToFahrenheit(celsius: Float): Float = (celsius * 9.0f / 5.0f) + 32.0f
        fun fahrenheitToCelsius(fahrenheit: Float): Float = (fahrenheit - 32.0f) * 5.0f / 9.0f
        fun celsiusToKelvin(celsius: Float): Float = celsius + 273.15f
        fun kelvinToCelsius(kelvin: Float): Float = kelvin - 273.15f
        fun fahrenheitToKelvin(fahrenheit: Float): Float =
            celsiusToKelvin(fahrenheitToCelsius(fahrenheit))

        fun kelvinToFahrenheit(kelvin: Float): Float = celsiusToFahrenheit(kelvinToCelsius(kelvin))

        fun convert(value: Float, fromUnit: TemperatureUnitType, toUnit: TemperatureUnitType): Float {
            if (fromUnit == toUnit) return value

            // It's often easiest to convert to a common intermediate (like Celsius) then to the target.
            val valueInCelsius = when (fromUnit) {
                TemperatureUnitType.CELSIUS -> value
                TemperatureUnitType.FAHRENHEIT -> fahrenheitToCelsius(value)
                TemperatureUnitType.KELVIN -> kelvinToCelsius(value)
            }

            return when (toUnit) {
                TemperatureUnitType.CELSIUS -> valueInCelsius
                TemperatureUnitType.FAHRENHEIT -> celsiusToFahrenheit(valueInCelsius)
                TemperatureUnitType.KELVIN -> celsiusToKelvin(valueInCelsius)
            }
        }
    }

    enum class TemperatureUnitType : UnitType {
        CELSIUS, FAHRENHEIT, KELVIN
    }

    // ----------------------------------------------

    object TimeConverter {
        private const val SECOND_IN_SECONDS = 1f
        private const val MINUTE_IN_SECONDS = 60f
        private const val HOUR_IN_SECONDS = 3600f
        private const val DAY_IN_SECONDS = 86400f
        private const val WEEK_IN_SECONDS = 604800f


        fun convert(value: Float, fromUnit: TimeUnitType, toUnit: TimeUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInSeconds = when (fromUnit) {
                TimeUnitType.SECOND -> value
                TimeUnitType.MINUTE -> value * MINUTE_IN_SECONDS
                TimeUnitType.HOUR -> value * HOUR_IN_SECONDS
                TimeUnitType.DAY -> value * DAY_IN_SECONDS
                TimeUnitType.WEEK -> value * WEEK_IN_SECONDS
            }
            return when (toUnit) {
                TimeUnitType.SECOND -> valueInSeconds
                TimeUnitType.MINUTE -> valueInSeconds / MINUTE_IN_SECONDS
                TimeUnitType.HOUR -> valueInSeconds / HOUR_IN_SECONDS
                TimeUnitType.DAY -> valueInSeconds / DAY_IN_SECONDS
                TimeUnitType.WEEK -> valueInSeconds / WEEK_IN_SECONDS
            }
        }
    }
    enum class TimeUnitType : UnitType { SECOND, MINUTE, HOUR, DAY, WEEK }

    object SpeedConverter {
        private const val MPS_IN_MPS = 1f
        private const val KPH_IN_MPS = 1000f / 3600f
        private const val MPH_IN_MPS = 1609.34f / 3600f
        private const val KNOT_IN_MPS = 1852f / 3600f


        fun convert(value: Float, fromUnit: SpeedUnitType, toUnit: SpeedUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInMps = when (fromUnit) {
                SpeedUnitType.METERS_PER_SECOND -> value
                SpeedUnitType.KILOMETERS_PER_HOUR -> value * KPH_IN_MPS
                SpeedUnitType.MILES_PER_HOUR -> value * MPH_IN_MPS
                SpeedUnitType.KNOT -> value * KNOT_IN_MPS
            }
            return when (toUnit) {
                SpeedUnitType.METERS_PER_SECOND -> valueInMps
                SpeedUnitType.KILOMETERS_PER_HOUR -> valueInMps / KPH_IN_MPS
                SpeedUnitType.MILES_PER_HOUR -> valueInMps / MPH_IN_MPS
                SpeedUnitType.KNOT -> valueInMps / KNOT_IN_MPS
            }
        }
    }
    enum class SpeedUnitType : UnitType { METERS_PER_SECOND, KILOMETERS_PER_HOUR, MILES_PER_HOUR, KNOT }

    object PressureConverter {
        private const val PASCAL_IN_PASCAL = 1f
        private const val BAR_IN_PASCAL = 100000f
        private const val PSI_IN_PASCAL = 6894.76f
        private const val ATM_IN_PASCAL = 101325f
        private const val TORR_IN_PASCAL = 133.322f


        fun convert(value: Float, fromUnit: PressureUnitType, toUnit: PressureUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInPa = when (fromUnit) {
                PressureUnitType.PASCAL -> value
                PressureUnitType.BAR -> value * BAR_IN_PASCAL
                PressureUnitType.PSI -> value * PSI_IN_PASCAL
                PressureUnitType.ATM -> value * ATM_IN_PASCAL
                PressureUnitType.TORR -> value * TORR_IN_PASCAL
            }
            return when (toUnit) {
                PressureUnitType.PASCAL -> valueInPa
                PressureUnitType.BAR -> valueInPa / BAR_IN_PASCAL
                PressureUnitType.PSI -> valueInPa / PSI_IN_PASCAL
                PressureUnitType.ATM -> valueInPa / ATM_IN_PASCAL
                PressureUnitType.TORR -> valueInPa / TORR_IN_PASCAL
            }
        }
    }
    enum class PressureUnitType : UnitType { PASCAL, BAR, PSI, ATM, TORR }

    object EnergyConverter {
        private const val JOULE_IN_JOULE = 1f
        private const val CALORIE_IN_JOULE = 4.184f
        private const val KILOWATT_HOUR_IN_JOULE = 3.6e6f
        private const val ELECTRONVOLT_IN_JOULE = 1.602e-19f


        fun convert(value: Float, fromUnit: EnergyUnitType, toUnit: EnergyUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInJoule = when (fromUnit) {
                EnergyUnitType.JOULE -> value
                EnergyUnitType.CALORIE -> value * CALORIE_IN_JOULE
                EnergyUnitType.KILOWATT_HOUR -> value * KILOWATT_HOUR_IN_JOULE
                EnergyUnitType.ELECTRONVOLT -> value * ELECTRONVOLT_IN_JOULE
            }
            return when (toUnit) {
                EnergyUnitType.JOULE -> valueInJoule
                EnergyUnitType.CALORIE -> valueInJoule / CALORIE_IN_JOULE
                EnergyUnitType.KILOWATT_HOUR -> valueInJoule / KILOWATT_HOUR_IN_JOULE
                EnergyUnitType.ELECTRONVOLT -> valueInJoule / ELECTRONVOLT_IN_JOULE
            }
        }
    }
    enum class EnergyUnitType : UnitType { JOULE, CALORIE, KILOWATT_HOUR, ELECTRONVOLT }

    object PowerConverter {
        private const val WATT_IN_WATT = 1f
        private const val KILOWATT_IN_WATT = 1000f
        private const val HORSEPOWER_IN_WATT = 745.7f


        fun convert(value: Float, fromUnit: PowerUnitType, toUnit: PowerUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInWatt = when (fromUnit) {
                PowerUnitType.WATT -> value
                PowerUnitType.KILOWATT -> value * KILOWATT_IN_WATT
                PowerUnitType.HORSEPOWER -> value * HORSEPOWER_IN_WATT
            }
            return when (toUnit) {
                PowerUnitType.WATT -> valueInWatt
                PowerUnitType.KILOWATT -> valueInWatt / KILOWATT_IN_WATT
                PowerUnitType.HORSEPOWER -> valueInWatt / HORSEPOWER_IN_WATT
            }
        }
    }
    enum class PowerUnitType : UnitType { WATT, KILOWATT, HORSEPOWER }

    object CurrentConverter {
        private const val AMPERE_IN_AMPERE = 1f
        private const val MILLIAMPERE_IN_AMPERE = 0.001f
        private const val MICROAMPERE_IN_AMPERE = 0.000001f


        fun convert(value: Float, fromUnit: CurrentUnitType, toUnit: CurrentUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInAmpere = when (fromUnit) {
                CurrentUnitType.AMPERE -> value
                CurrentUnitType.MILLIAMPERE -> value * MILLIAMPERE_IN_AMPERE
                CurrentUnitType.MICROAMPERE -> value * MICROAMPERE_IN_AMPERE
            }
            return when (toUnit) {
                CurrentUnitType.AMPERE -> valueInAmpere
                CurrentUnitType.MILLIAMPERE -> valueInAmpere / MILLIAMPERE_IN_AMPERE
                CurrentUnitType.MICROAMPERE -> valueInAmpere / MICROAMPERE_IN_AMPERE
            }
        }
    }
    enum class CurrentUnitType : UnitType { AMPERE, MILLIAMPERE, MICROAMPERE }

    object VoltageConverter {
        private const val VOLT_IN_VOLT = 1f
        private const val MILLIVOLT_IN_VOLT = 0.001f
        private const val KILOVOLT_IN_VOLT = 1000f


        fun convert(value: Float, fromUnit: VoltageUnitType, toUnit: VoltageUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInVolt = when (fromUnit) {
                VoltageUnitType.VOLT -> value
                VoltageUnitType.MILLIVOLT -> value * MILLIVOLT_IN_VOLT
                VoltageUnitType.KILOVOLT -> value * KILOVOLT_IN_VOLT
            }
            return when (toUnit) {
                VoltageUnitType.VOLT -> valueInVolt
                VoltageUnitType.MILLIVOLT -> valueInVolt / MILLIVOLT_IN_VOLT
                VoltageUnitType.KILOVOLT -> valueInVolt / KILOVOLT_IN_VOLT
            }
        }
    }
    enum class VoltageUnitType : UnitType { VOLT, MILLIVOLT, KILOVOLT }

    object ResistanceConverter {
        private const val OHM_IN_OHM = 1f
        private const val KILOOHM_IN_OHM = 1000f
        private const val MEGAOHM_IN_OHM = 1000000f


        fun convert(value: Float, fromUnit: ResistanceUnitType, toUnit: ResistanceUnitType): Float {
            if (fromUnit == toUnit) return value
            val valueInOhm = when (fromUnit) {
                ResistanceUnitType.OHM -> value
                ResistanceUnitType.KILOOHM -> value * KILOOHM_IN_OHM
                ResistanceUnitType.MEGAOHM -> value * MEGAOHM_IN_OHM
            }
            return when (toUnit) {
                ResistanceUnitType.OHM -> valueInOhm
                ResistanceUnitType.KILOOHM -> valueInOhm / KILOOHM_IN_OHM
                ResistanceUnitType.MEGAOHM -> valueInOhm / MEGAOHM_IN_OHM
            }
        }
    }
    enum class ResistanceUnitType : UnitType { OHM, KILOOHM, MEGAOHM }

}

fun unitName(unitType: UnitType) = when (unitType) {
    is UnitType.LengthUnitType -> "Length"
    is UnitType.MassUnitType -> "Mass"
    is UnitType.VolumeUnitType -> "Volume"
    is UnitType.TemperatureUnitType -> "Temperature"
    is UnitType.PowerUnitType -> "Power"
    is UnitType.CurrentUnitType -> "Current"
    is UnitType.VoltageUnitType -> "Voltage"
    is UnitType.ResistanceUnitType -> "Resistance"
    is UnitType.TimeUnitType -> "Time"
    is UnitType.PressureUnitType -> "Pressure"
    is UnitType.AngleUnitType -> "Angle"
    is UnitType.AreaUnitType -> "Area"
    is UnitType.SpeedUnitType -> "Speed"
    is UnitType.EnergyUnitType -> "Energy"
    else -> ""
}

// cuts the last 8 characters i.e. "UnitType"
fun unitName(unit: String) = unit.substring(0, unit.length - 8)