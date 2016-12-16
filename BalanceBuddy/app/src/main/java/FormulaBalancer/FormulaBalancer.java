package FormulaBalancer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.fraction.Fraction;

/**
 * Formula Balancer
 *
 * @author Michael Barbour
 *
 * Accepts a formula as a string then solves it using the Gauss-Jordan method.
 * 				Formula string constraints:
 * 						-Element names must be capitalized correctly.
 * 						-No compounds may have amounts inputed.
 * 						-Reactants and Products must be separated with a "="
 * 						-Compounds on the same side of the equation must be separated with a "+"
 *
 * 				Example Formula:  "C3H8 + O2 = CO2 + H2O"
 */
public class FormulaBalancer {

    /**
     * Balance-  The main method in formula balancer.  Accepts an unbalanced chemical equation and returns the equation balanced.
     * 					C3H11 + O2 = CO2 + H2O should return 2 C3H11 + 17 O2 = 6 CO2 + 22 H2O
     *
     * @param formula - String containing a balanced or unbalanced chemical equation.  The algorithm will then try to solve it.
     * @return String containing the formula in a string with the correct Coefficients
     */
    public String balance (String formula){
        try {
            // Separate string into reactants and products
            String reactantsString = formula.substring(0, formula.indexOf("="));
            String productsString = formula.substring(formula.indexOf("=") + 1, formula.length());

            // Trim WhiteSpace out of string
            reactantsString = reactantsString.trim();
            productsString = productsString.trim();

            // Create ArrayLists containing each element
            ArrayList<String> reactants = new ArrayList<String>();
            ArrayList<String> products = new ArrayList<String>();

            createArrayList(reactantsString, reactants);
            createArrayList(productsString, products);

            // Copy ArrayList for Balanced Equation Reconstruction

            ArrayList<String> reactantStringForFinal = new ArrayList<String>(reactants);
            ArrayList<String> productStringForFinal = new ArrayList<String>(products);

            // Add 1 to substances without subscripts
            addCoefficients(reactants);
            addCoefficients(products);

            // Extract all of the elements out of the list (each one will correspond to a row in the matrix)
            ArrayList<String> elements = new ArrayList<String>();
            extractElements(elements, reactants);

            // Creates n x n + 1 matrix to be solved via gauss-jordan reduction
            int size = reactants.size() + products.size();                // Compounds in array
            int rows = elements.size();
            double matrix[][] = new double[0][0];

            if (rows + 1 == size) {
                matrix = new double[rows][size];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < size; j++) {
                        matrix[i][j] = 0;
                    }
                }
            }

            if (rows + 1 < size) {
                System.out.println("PING");
                matrix = new double[size][size + 1];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size + 1; j++) {
                        if (i >= rows && i == j) {
                            matrix[i][j] = 1;
                        } else matrix[i][j] = 0;
                    }
                }
            }

            if (rows + 1 > size) {
                System.out.println("DING");
                matrix = new double[rows + 1][rows + 2];
                for (int i = 0; i < rows + 1; i++) {
                    for (int j = 0; j < rows + 1; j++) {
                        if (i >= size && i == j) {
                            matrix[i][j] = 1;
                        } else matrix[i][j] = 0;
                    }
                    matrix[i][rows + 1] = 1;
                }
            }

            print_matrix(matrix);

            addToMatrixR(reactants, elements, matrix);                                // Populate Matrix
            addToMatrixP(products, elements, matrix, reactants.size());

            print_matrix(matrix);

            double[] answer = gauss_jordan(matrix);                    // Use Gauss-Jordan Reduction to solve for final Ratios

            System.out.println(Arrays.toString(answer));

            int[] denominators = new int[answer.length];                // Create array of denominators
            findDenoms(answer, denominators);

            int lcm = lcm(denominators);                                // Find the least common multiple of all answers' denominators

            int[] values = reduceDoubles(answer, lcm);                    // Convert Double array to Int Array

            int gcf = findGCF(values);                                    // Find GCF of values then divide all values by that
            reduceInts(values, gcf);

            //		System.out.println(Arrays.toString(values));
            //		System.out.println(reactantStringForFinal.toString());
            //		System.out.println(productStringForFinal.toString());
            String balancedEquation = constructFinalEquation(reactantStringForFinal, productStringForFinal, values);

            return balancedEquation;
        }
        catch(Exception e){
            return "Input Error";
        }
    }

    private void print_matrix(double[][] matrix) {
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    /**
     * Constructs the final equation from inputed strings and final coefficients.
     *
     * @param reactantStringForFinal
     * @param productStringForFinal
     * @param values
     * @return	Final equation
     */
    private String constructFinalEquation(ArrayList<String> reactantStringForFinal, ArrayList<String> productStringForFinal, int[] values){
        String balancedEquation = "";

        for(int i = 0; i < values.length; i++){
            if(i < reactantStringForFinal.size() - 1){
                if(values[i] > 1) balancedEquation += values[i];
                balancedEquation += reactantStringForFinal.get(i) + " + ";
            }
            else if(i < reactantStringForFinal.size()){
                if(values[i] > 1) balancedEquation += values[i];
                balancedEquation += reactantStringForFinal.get(i) + " = ";
            }
            else if(i - reactantStringForFinal.size() < productStringForFinal.size() - 1){
                if(values[i] > 1) balancedEquation += values[i];
                balancedEquation += productStringForFinal.get(i - reactantStringForFinal.size()) + " + ";
            }
            else if(i - reactantStringForFinal.size() < productStringForFinal.size()){
                if(values[i] > 1) balancedEquation += values[i];
                balancedEquation += productStringForFinal.get(i - reactantStringForFinal.size());
            }
        }

        return balancedEquation;
    }

    /**
     * finds the GCF of a list of inputed integer values
     *
     * @param values - integer array
     * @return  Greatest common factor of integer array
     */
    private int findGCF(int[] values) {
        int a = values[0];
        int b = values[1];
        int temp;
        int gcf = Integer.MAX_VALUE;
        for(int i = 2; i < values.length; i++){
            temp = gcd(a,b);
            if (temp < gcf)
                gcf = temp;
            a = b;
            b = values[i];
        }
        return gcf;
    }

    /**
     * Reduce integer array by the GCF
     *
     * @param values
     * @param gcf
     */
    private void reduceInts(int[] values, int gcf) {
        for(int i = 0; i < values.length; i++){
            values[i] /= gcf;
        }
    }

    /**
     * Converts an array of doubles to integer equivalencies by multiplying them all by the LCM of their denominators
     *
     * @param answer - Array of Doubles
     * @param lcm   -  	Least Common Multiple of all of the doubles.  All values will be multiplied by this to return an int value
     */
    private static int[] reduceDoubles(double[] answer, int lcm){
        int[] values = new int[answer.length];
        for(int i = 0; i < answer.length; i++){
            answer[i] = Math.abs(answer[i]);
            answer[i] *= lcm;
            values[i] = Math.round((int)answer[i]);
        }
        return values;
    }

    /**
     * Finds all of the denominators from parameter d and return puts them into parameter j
     *
     * @param d
     * @param j
     */
    private static void findDenoms(double[] d, int[] j) {
        Fraction fraction;
        for(int i = 0; i < d.length; i++){
            fraction = new Fraction(d[i]);
            j[i] = fraction.getDenominator();
        }
    }

    /**
     * Finds the least common multiple of the values in the inputed double Array
     * @param denominators - an array of ints (will be modified)
     */
    private int lcm(int[] denominators) {
        int a = denominators[0];
        int b = denominators[1];
        int temp = 0;
        int lcm = 0;
        for(int i = 2; i < denominators.length; i++){
            temp = lcm(a,b);
            if (temp > lcm)
                lcm = temp;
            a = b;
            b = denominators[i];
        }

        return lcm;
    }

    /**
     * Finds the least common multiple of two different numbers.
     *
     * @param a
     * @param b
     * @return
     */
    public static int lcm(double a, double b){
        return (int)(a * b / gcd(a,b));
    }

    /**
     * Returns the Greatest Common Denominator using Euclid's algorithm
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcd(double a, double b){
        double temp;
        while(b != 0){
            temp = b;
            b = a % b;
            a = temp;
        }
        return (int) a;
    }

    /**
     *  gauss_jordan: Solves a n,n+1 matrix for all of the variables.  Returns variables
     *   			  in a 1xn array where 0,0 is a; 0,1 is b; and so forth.
     * @param ma - matrix of size n by n + 1
     * @return an array list of size n
     */
    public double[] gauss_jordan(double[][] ma){
        double[][] matrix = new double[ma.length][ma[0].length];  			//Copy array
        for(int i = 0; i < ma.length; i++){
            for(int j = 0; j < ma[0].length; j++){
                matrix[i][j] = ma[i][j];
            }
        }

        double[] answer = new double[ma.length + 1];							//Create answer array
        for(int i = 0; i < answer.length; i++){
            answer[i] = -1;
        }

        int col;
        double factor;
        double[] temp;
        double scale;

        for(int row = 0; row < matrix.length; row++){
            col = row;                                    //cell identity on diagonal
            for(int x = row + 1; x < matrix.length; x++){
                if(Math.abs(matrix[row][col]) < Math.abs(matrix[x][col])){
                    temp = matrix[row];
                    matrix[row] = matrix[x];
                    matrix[x] = temp;
                }
            }

            if (matrix[row][col] == 0){			// Equation Deemed Unsolvable
                return null;
            }
            factor = 1.0 / matrix[row][col];              // diagonal's inverse (make diagonal 1)

            for(int y = 0; y < matrix[0].length; y++) {
                matrix[row][y] *= factor;                 		// Mulitiply row by diagonal's inverse
            }

            for(int x = row + 1; x < matrix.length; x++){
                scale = matrix[x][col]/matrix[row][col];		// Calculate scale for row multiplication
                for(int y = 0; y < matrix[0].length; y++){
                    matrix[x][y] -= scale*matrix[row][y];
                }
            }
        }

        for(int row = matrix.length - 1; row > -1; row--){
            col = row;                               			//cell identity on diagonal
            for(int x = (row - 1); x > -1; x--){
                scale = matrix[x][col] / matrix[row][col];			//Divide by 0 error
                for(int y = 0; y < matrix[0].length; y++){
                    matrix[x][y] -= scale*matrix[row][y];
                }
            }
        }

        for(int row = 0; row < matrix.length; row++){
            answer[row] = matrix[row][matrix[0].length - 1];
        }
        answer[answer.length - 1] = 1;

        print_matrix(matrix);

        return answer;
    }

    /**
     * Adds compounds to the matrix so they can be reduced via gauss-jordan:
     * 					Matrix rows correspond to elements
     * 					Matrix columns correspond to compounds
     *
     * @param terms	- ArrayList of elements from problem (string form)
     * @param elements - ArrayList of elements from problem (string form)
     * @param matrix - a n by n + 1 matrix (will be modified)
     * @param size - int number of reactant compounds
     */
    private void addToMatrixP(ArrayList<String> terms, ArrayList<String> elements, double[][] matrix, int size) {
        for(int i = 0; i < terms.size(); i++){
            for (int j = 0; j < elements.size(); j++){
                String currentE = elements.get(j);
                Pattern p1 = Pattern.compile("([A-Z])(\\d+)");
                String comp = terms.get(i);
                Matcher m1 = p1.matcher(comp);
                while(m1.find()){
                    if(m1.group(1).equals(currentE)){
                        matrix [j][i + size] = -1 * Integer.parseInt(m1.group(2)); 		//Find coefficient for element
                    }
                }
                Pattern p2 = Pattern.compile("([A-Z])([a-z])(\\d+)");
                Matcher m2 = p2.matcher(comp);
                while(m2.find()){
                    String foundE = m2.group(1) + m2.group(2);
                    if(foundE.equals(currentE)){
                        matrix [j][i + size] = -1 * Integer.parseInt(m2.group(3));
                    }
                }
            }
        }
    }

    /**
     * Adds compounds to the matrix so they can be reduced via gauss-jordan:
     * 					Matrix rows correspond to elements
     * 					Matrix columns correspond to compounds
     *
     * @param terms	- ArrayList of elements from problem (string form)
     * @param elements - ArrayList of elements from problem (string form)
     * @param matrix - a n by n + 1 matrix (will be modified)
     */
    private void addToMatrixR(ArrayList<String> terms, ArrayList<String> elements, double[][] matrix) {
        for(int i = 0; i < terms.size(); i++){
            for (int j = 0; j < elements.size(); j++){
                String currentE = elements.get(j);
                Pattern p1 = Pattern.compile("([A-Z])(\\d+)");
                String comp = terms.get(i);
                Matcher m1 = p1.matcher(comp);
                while(m1.find()){
                    if(m1.group(1).equals(currentE)){
                        matrix [j][i] = Integer.parseInt(m1.group(2)); 		//Find coefficient for element
                    }
                }
                Pattern p2 = Pattern.compile("([A-Z])([a-z])(\\d+)");
                Matcher m2 = p2.matcher(comp);
                while(m2.find()){
                    String foundE = m2.group(1) + m2.group(2);
                    if(foundE.equals(currentE)){
                        matrix [j][i] = Integer.parseInt(m2.group(3));
                    }
                }
            }
        }
    }

    /**
     * Uses Pattern class to search for substrings that resemble elements.
     * 				Elements may look like A-Z followed by a number or
     * 				A-Z followed by a-z followed by a number
     * @param elements		Empty ArrayList that will host elements (will be modified).
     * @param terms			ArrayList containing compounds.
     */
    private void extractElements(ArrayList<String> elements, ArrayList<String> terms) {
        for (int i = 0; i < terms.size(); i++){
            // match single uppercase letter, or uppercase with 1 or 2 lowercase
            Pattern p1 = Pattern.compile("([A-Z])(\\d+)");
            String comp = terms.get(i);
            Matcher m1 = p1.matcher(comp);
            while (m1.find()) {
                if (!elements.contains(m1.group(1)))
                {
                    elements.add(m1.group(1));
                }
            }
            Pattern p2 = Pattern.compile("([A-Z])([a-z])(\\d+)");
            Matcher m2 = p2.matcher(comp);
            while (m2.find()) {
                if (!elements.contains(m2.group(1) + "" + m2.group(2)))
                {
                    elements.add(m2.group(1) + "" + m2.group(2));
                }
            }
            if (terms.get(i).length() == 1){
                String e = terms.get(i);
                terms.set(i, e + "1");
            }
        }
    }

    /**
     * Looks through compounds and adds 1 after elements that have a coefficient but do not show them.
     * @param terms
     */
    private void addCoefficients(ArrayList<String> terms) {
        for (int i = 0; i < terms.size(); i++){
            String x = terms.get(i);
            for (int j = 0; j < x.length() - 1; j++){
                if (!Character.isDigit(x.charAt(j)) && x.charAt(j + 1) == ')'){
                    x = x.substring(0, j + 1) + "1" + x.substring(j + 1, x.length());
                    break;
                }
                if ((Character.isUpperCase(x.charAt(j)) && !Character.isDigit(j + 1) && Character.isUpperCase(x.charAt(j + 1)))){
                    x = x.substring(0, j + 1) + "1" + x.substring(j + 1, x.length());
                }
                else if (j == x.length() - 2 && Character.isUpperCase(x.charAt(j + 1))){
                    x = x + "1";
                }
                if (Character.isUpperCase(x.charAt(j)) && Character.isLowerCase(x.charAt(j + 1))){
                    if (j != x.length() - 2){
                        if (Character.isUpperCase(x.charAt(j + 2)) || x.charAt(j + 2) == '('){
                            x = x.substring(0, j + 2) + "1" + x.substring(j + 2, x.length());
                        }
                    }
                    else if (j == x.length() - 2){
                        x = x + "1";
                    }
                }
            }
            terms.set(i, x);
        }
    }

    /**
     * Creates an array list from either the reactant or product substring.  The arraylist will contain compounds
     * 				stored as they were inputed
     * @param eqString - Substring of formula.  Either the reactants or products
     * @param terms - ArrayList where the compounds are to be stored (will be modified)
     */
    public void createArrayList(String eqString, ArrayList<String> terms) {
        int pos = 0;
        for (int i = 0; i < eqString.length(); i++){
            if (i == eqString.length() - 1){
                String x = eqString.substring(pos, eqString.length());
                x = x.trim();
                terms.add(x);
            }
            if (Character.toString(eqString.charAt(i)).equals("+")){
                String x = eqString.substring(pos, i);
                x = x.trim();
                pos = i + 1;
                terms.add(x);
            }
        }
    }
}