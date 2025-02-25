import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a text file which has the x and f(x) values to be transformed into an interpolating polynomial: ");
        String userFileString = scanner.nextLine();
        scanner.close();

        double[][] xAndYVals = getUserTextFile(userFileString);

        double[] userXVals =  xAndYVals[0];//{1, 1.5, 0, 2}; //{1, 1.5, 3, 2, 4, 5, 9};
        double[] userYVals =  xAndYVals[1];//{3, 3.25, 3, (double)5/(double)3}; //{1, 1.5, 4, 2, 6, 7, 8};
        double[] coeffLagVals = getLagCoeffecients(userYVals, userXVals);

        displayLagrangeForm(coeffLagVals, userXVals);

        double[][] table = solveDisplayTable(userXVals, userYVals);
        displayNewtonForm(table);
        displaySimplifiedForm(table, userYVals, userXVals);
    }

    public static double[] getLagDenomenators(double[] xValPar) {
        double[] den = new double[xValPar.length];
        for (int i = 0; i < xValPar.length; i++) den[i] = 1;
        double subVal;

        for (int i = 0; i < xValPar.length; i++) {
            for (int j = 0; j < xValPar.length; j++) {
                if (i == j) continue;
                else {
                    subVal = xValPar[i] - xValPar[j];
                    den[i] = den[i] * subVal;
                }
            }
        }
        return den;
    }

    public static double[] getLagCoeffecients(double[] yValPar, double[] xValPar) {
        double[] coeff = new double[yValPar.length];
        double[] den = getLagDenomenators(xValPar);
        for (int i = 0; i < yValPar.length; i++) {
            coeff[i] = yValPar[i] / den[i];
        }
        return coeff;
    }

    public static void displayLagrangeForm(double[] coeffVals, double[] xVals) {
        String output = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        for (int i = 0; i < coeffVals.length; i++) {
            output = output + decimalFormat.format(coeffVals[i]);
            for (int j = 0; j < coeffVals.length; j++) {
                if (i == j) continue;
                else {
                    output = output + "(x-" + xVals[j] + ")";
                }
            }
            output = output + "\n";
            if (i < coeffVals.length - 1) output = output + " + ";
        }
        System.out.println("Interpolating polynomial in Lagrange's form is:");
        System.out.println(output);
    }

    public static double[][] solveDisplayTable(double[] xValPar, double[] yValPar) {
        double[][] table = new double[xValPar.length][xValPar.length + 1];
        for (int i = 0; i < xValPar.length; i++) {
            table[i][0] = xValPar[i];
            table[i][1] = yValPar[i];
        }

        int decending = xValPar.length - 1;
        int increasing = 0;
        for (int j = 0; j < xValPar.length; j++) {
            for (int i = 0; i < decending; i++) {
                table[i][j+2] = (table[i+1][j+1] - table[i][j+1]) / (table[i + 1 + increasing][0] - table[i][0]);
            }
            decending--;
            increasing++;
        }

        //display the table
        System.out.println("Divided Difference Table:");
        System.out.printf("%-9s %-9s %-9s","x:", "y:", "f[...] ->");
        System.out.println();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        int shorten = 0;
        for (int i = 0; i < xValPar.length; i++) {
            for (int j = 0; j < xValPar.length +1 - shorten; j++) {
                System.out.printf("%-6s    ",decimalFormat.format(table[i][j]));
            }
            shorten++;
            System.out.println();
        }

        return table;
    }

    public static void displayNewtonForm(double[][] table) {
        String[] xMultipliers = new String[table.length];
        for (int i = 0; i < table.length; i++) {
            if (i == 0) xMultipliers[0] = "";
            else{
                xMultipliers[i] = xMultipliers[i-1] + "(x-" + table[i-1][0] + ")";
            }
        }

        System.out.println("\nInterpolating polynomial in Newton's form is:");
        String output = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        for (int i = 0; i < table.length; i++) {
            output = output + decimalFormat.format(table[0][i+1])+ xMultipliers[i];
            if (i != table.length -1) output = output + " + \n";
        }
        System.out.println(output);
    }

    public static void displaySimplifiedForm(double[][] table, double[] userYVals, double[] userXVals) {
        System.out.println("\nSimplified polynomial form is:");
        double[] coeffLagVals = getLagCoeffecients(userYVals, userXVals);
        double[][] subTable = new double[table.length][table.length - 1];
        double[][] subTableSimplified = new double[table.length][table.length];
        String subTableString = "";

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (i == j) {
                   continue;
                }
                else {
                  subTableString = subTableString + table[j][0];
                  if (j != table.length -1) subTableString = subTableString + " ";
                }
            }
            String[] tempArray = subTableString.split("\\s+");
            for (int k = 0; k < tempArray.length; k++) {
                subTable[i][k] = Double.parseDouble(tempArray[k]);
            }
            subTableString = "";
        } //fill out subTable

        for (int i = 0; i < subTableSimplified.length; i++) {
            for (int j = 0; j < subTableSimplified.length; j++) {
                if (j == 0) subTableSimplified[i][j] = coeffLagVals[i]; //coeffLagVals is giving correct results
                else {
                    subTableSimplified[i][j] = LagrangeNumeratorCoef.valOfCombinations(subTable[i], j) * coeffLagVals[i];
                }
            }
        }

        //add up all the corresponding coeff values
        double[] sumOfCoeffs = new double[subTableSimplified.length];
        for (int i = 0; i < subTableSimplified.length; i++) {
            for (int j = 0; j < subTableSimplified.length; j++) {
                sumOfCoeffs[i] = sumOfCoeffs[i] + subTableSimplified[j][i];
            }
        }

        String output = "";
        int count = sumOfCoeffs.length - 1;
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        for (int i = 0; i < table.length; i++) {
            output = output + decimalFormat.format(sumOfCoeffs[i]) + "x^" + count;
            if (i != table.length -1) output = output + " + ";
            count--;
        }
        System.out.println(output);
    }

    public static double[][] getUserTextFile(String filePath) {
        String backslash = "\\\\"; //four backslashes are needed to represent a single backslash in a regular expression
        String doubleBackslash = "\\\\\\\\";
        String updatedURL = filePath.replaceAll(backslash, doubleBackslash);
        double[][] values = new double[2][];;

        try (BufferedReader br = new BufferedReader(new FileReader(updatedURL))) {
            List<String> lines = new ArrayList<>();

            // Read each line from the file and store it in the list
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            // Display the content of the list
            String[][] valuesTemp = new String[2][];
            int counter = 0;
            System.out.println("\nHere are your values from your txt file:");
            for (String str : lines) {
                valuesTemp[counter] = str.split("\\s+");
                counter++;
                System.out.println(str);
            }
            System.out.println();

            values = new double[2][valuesTemp[0].length];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < valuesTemp[0].length; j++) {
                    values[i][j] = Double.parseDouble(valuesTemp[i][j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}