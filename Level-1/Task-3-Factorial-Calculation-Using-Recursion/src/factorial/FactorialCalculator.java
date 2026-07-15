package factorial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactorialCalculator {


    public static final int FACTORIAL_BASE_CASE = 1;

    private final List<String> recursionSteps;


    public FactorialCalculator() {
        this.recursionSteps = new ArrayList<>();
    }

    public BigInteger calculateFactorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }

        recursionSteps.clear();
        return factorialRecursive(number);
    }


    private BigInteger factorialRecursive(int number) {
        if (number == 0) {
            recursionSteps.add("0! = 1  (base case)");
            return BigInteger.ONE;
        }

        if (number == FACTORIAL_BASE_CASE) {
            recursionSteps.add("1! = 1  (base case)");
            return BigInteger.ONE;
        }

        BigInteger subResult = factorialRecursive(number - 1);
        BigInteger result = BigInteger.valueOf(number).multiply(subResult);
        recursionSteps.add(String.format("%d! = %d × %d! = %s", number, number, number - 1, result));
        return result;
    }


    public List<String> getRecursionSteps() {
        return Collections.unmodifiableList(recursionSteps);
    }


    public boolean hasRecursionSteps() {
        return !recursionSteps.isEmpty();
    }
}
