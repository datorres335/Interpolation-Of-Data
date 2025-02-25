import java.util.Arrays;

public class LagrangeNumeratorCoef {
    static double value = 0;

    public static double valOfCombinations(double[] numbers, int r) {
        value = 0;
        double[] data = new double[r];
        generateCombinationsUtil(numbers, data, 0, numbers.length - 1, 0, r);
        //System.out.println(value);
        return value;
    }

    private static void generateCombinationsUtil(double[] numbers, double[] data, int start, int end, int index, int r) {
        if (index == r) {
            //System.out.println(Arrays.toString(Arrays.copyOfRange(data, 0, r)));
            double multiplyVal = 1;
            for (double i : data) {
                multiplyVal *= i;
            }
            value += (multiplyVal *-1);
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = numbers[i];
            generateCombinationsUtil(numbers, data, i + 1, end, index + 1, r);
        }
    }

    public static void main(String[] args) {
        double[] numbers = {-2,-1, 1};
        int r = 2;
        valOfCombinations(numbers, r);
    }
}
