package UnitConverter;

/**
 * Created by robert on 4/11/2016.
 */
public class MassConverter {
                                            // the way to read this is over and then down (ex.)
                                            // convert  kg to centigram = 1000000
    public static double calculate(double baseUnit, String baseUnitType, String convertedUnitType){
                                          // G      Kg         Oz         mg            deci          centi         hectogram     decagram         Converted Units = Rows
        final double[][] conversionTable = {{1,     1000,      28.35,     0.001,        .1,           0.01,         100,          10      },//G
                                            {.001,  1,         .02834,    .000001,      .0001,        .00001,       .1,           0.01     },//Kg  Base Units = Cols
                                            {.0353, 35.3,      1,         .000035274,   0.0035274,    0.00035274,   3.5274,       0.35274  },//oz
                                            {1000,  1000000,   28349.5,   1,            100,          10,           100000,       10000    },//mg
                                            {10,    10000,     283.495,   0.01,         1,            0.1,          1000,         100      },//deci
                                            {100,   100000,    2834.95,   .1,           10,           1,            10000,        1000     },//centi
                                            {.01,   10,        0.283495,  .00001,       0.001,        0.0001,       1,            0.01    },//hecto
                                            {.1,    100,       2.83495,   .0001,        0.01,         0.001,        1000,         1        }}; //deca

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
        if(unitType.equals("g")){
            return 0;
        }
        if(unitType.equals("kg")){
            return 1;
        }
        if(unitType.equals("oz")){
            return 2;
        }
        if(unitType.equals("mg")){
            return 3;
        }
        if(unitType.equals("decigram")){
            return 4;
        }
        if(unitType.equals("centigram")){
            return 5;
        }
        if(unitType.equals("hectogram")){
            return 6;
        }
        if(unitType.equals("decagram")){
            return 7;
        }
        return -1;
    }




}
