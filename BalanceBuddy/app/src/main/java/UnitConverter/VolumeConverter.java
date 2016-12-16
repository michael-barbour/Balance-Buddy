package UnitConverter;

/**
 * Created by robert on 4/11/2016.
 */
public class VolumeConverter {                         // change all these values

    public static double calculate(double baseUnit, String baseUnitType, String convertedUnitType){
                                           //gallon      oz                liter       milliliter         kiloliter      centiliter    deciliter         Converted Units = Rows
        final double[][] conversionTable = {{1,          0.00781262,       .264172,    .000264172,        264.172,       .00264172,    .0264172    },//gallon
                                            {127.998,    1,                33.8132,    .0338135,          33813.5,       .338135,      3.38135     },//oz        Base Units = Cols
                                            {3.78541,    .029574,          1,          .001,              1000,          .01,          .1          },//liter
                                            {3785.41,    29.574,           1000,       1,                 1000000,       10,           100         },//milliliter
                                            {.00378541,  .000029574,       .001,       .000001,           1,             .00001,       .0001       },//kiloliter
                                            { 378.541,   2.9574,           100,        .1,                100000,        1,            10          },//centiliter
                                            { 37.8541,   .29574,           10,         .01,               10000,         0.1,          1           }}; //deciliter

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
        if(unitType.equals("gallon")){
            return 0;
        }
        if(unitType.equals("oz")){
            return 1;
        }
        if(unitType.equals("liter")){
            return 2;
        }
        if(unitType.equals("milliliter")){
            return 3;
        }
        if(unitType.equals("kiloliter")){
            return 4;
        }
        if(unitType.equals("centiliter")){
            return 5;
        }
        if(unitType.equals("deciliter")){
            return 6;
        }
        return -1;
    }




}
