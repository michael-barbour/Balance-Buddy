package UnitConverter;

/**
 * 	Unit Converter
 * 	@author: Michael Barbour
 *
 * 	Description:  This class accepts a base unit and converts it to a different unit of measurement
 * 				  The class will work with Scientific Units as well as a few customary units
 *
 *  Preconditions:  A base unit number (double)
 *  				The base unit type (String. bUnitType:  "km", "C", "kL", "g")
 *  				The desired base unit type (String. cUnitType)
 *
 *  Postconditions:	Returns a double that is the base unit converted to the type requested from cUnitType
 */

public class TempConverter {
    //private double baseUnit = 0;
    //private String baseUnitType = "";
    //private double convertedUnit = 0;
    //private String convertedUnitType = "";
    //private String measure;

    public TempConverter(){

    }

    public static double calculate(double bUnit, String bUnitType, String cUnitType) {
        //baseUnit = bUnit;
        //baseUnitType = bUnitType;
        //convertedUnitType = cUnitType;

        double convertedUnit = convertTemp(bUnit, bUnitType, cUnitType);

        return convertedUnit;
    }

    private static double convertTemp(double baseUnit, String baseUnitType, String convertedUnitType){
        double convertedUnit = 0;

        if(baseUnitType.equals("C") && convertedUnitType.equals("K")){
            convertedUnit = conversionCelsiusToKelvin(baseUnit);
        }
        if(baseUnitType.equals("C") && convertedUnitType.equals("F")){
            convertedUnit = conversionCelsiusToFahrenheit(baseUnit);
        }
        if(baseUnitType.equals("F") && convertedUnitType.equals("K")){
            convertedUnit = conversionFahrenheitToCelsius(baseUnit);
            convertedUnit = conversionCelsiusToKelvin(convertedUnit);
        }
        if(baseUnitType.equals("F") && convertedUnitType.equals("C")){
            convertedUnit = conversionFahrenheitToCelsius(baseUnit);
        }
        if(baseUnitType.equals("K") && convertedUnitType.equals("G")){
            convertedUnit = conversionKelvinToCelsius(baseUnit);
            convertedUnit = conversionCelsiusToFahrenheit(convertedUnit);
        }
        if(baseUnitType.equals("K") && convertedUnitType.equals("C")){
            convertedUnit = conversionKelvinToCelsius(baseUnit);
        }

        return convertedUnit;
    }

    private static double conversionCelsiusToKelvin(double baseUnit){
        double convertedUnit = baseUnit + 273.15;
        return convertedUnit;
    }

    private static double conversionKelvinToCelsius(double baseUnit){
        double convertedUnit = baseUnit - 273.15;
        return convertedUnit;
    }

    private static double conversionFahrenheitToCelsius(double baseUnit){
        double convertedUnit = (baseUnit - 32) / 1.8;
        return convertedUnit;
    }

    private static double conversionCelsiusToFahrenheit(double baseUnit){
        double convertedUnit = (baseUnit * 1.8) + 32;
        return convertedUnit;
    }
}