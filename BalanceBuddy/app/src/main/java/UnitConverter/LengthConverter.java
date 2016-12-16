package UnitConverter;

/**
 * Created by robert on 4/11/2016.
 */
public class LengthConverter {                         // the way to read this is over and then down (ex.)
                                                       // convert in to ft. so go over 0 down 1 = 0.08333334

    public static double calculate(double baseUnit, String baseUnitType, String convertedUnitType){
                                           //in             ft            yard(s)          meter        kilo              milli            centi                  Converted Units = Rows
        final double[][] conversionTable = {{1,             12,           36,              39.3701,     39370.1,           .0393701,        .393701  },//in
                                            {0.0833333334,  1,            3,               3.28084,     3208.84,           .00328084,       .0328084 },//ft        Base Units = Cols
                                            {0.0277778,     .333333,      1,               1.09361,     1093.61,           .00109361,       .0109361 },//yard(s)
                                            {.0254,         .3048,        .9144,           1,           1000,              .001,            .01      },//meter
                                            {.0000254,      .0003048,     .0009144,        .001,        1,                 1000000,         .00001   },//kilo
                                            {25.4,          304.8,        914.4,           1000,        1000000,           1,               10       },//milli
                                            {2.54,          30.48,        91.44,           100,         100000,            .1,              1        }}; //centi

        double convertedUnit;
        double factor;
        int baseUnitInt;
        int convUnitInt;

        baseUnitInt = tokenize(baseUnitType);
        convUnitInt = tokenize(convertedUnitType);

        factor = conversionTable[convUnitInt][baseUnitInt];

        convertedUnit = convert(baseUnit, factor);

        return convertedUnit;
    }

    public static double convert(double baseUnit, double factor){
        return baseUnit * factor;
    }

    public static int tokenize(String unitType){
        if(unitType.equals("in")){
            return 0;
        }
        if(unitType.equals("ft")){
            return 1;
        }
        if(unitType.equals("yard(s)")){
            return 2;
        }
        if(unitType.equals("meter")){
            return 3;
        }
        if(unitType.equals("kilometer")){
            return 4;
        }
        if(unitType.equals("centimeter")){
            return 5;
        }
        if(unitType.equals("millimeter")){
            return 6;
        }
        return -1;
    }




}
