package factorial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides recursive factorial calculation for the Factorial Calculator application.
 * <p>
 * Contains pure business logic with no console I/O or input validation.
 * Uses {@link BigInteger} to avoid overflow for larger non-negative inputs.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class FactorialCalculator {

    /** Factorial of 0 and 1 is defined as 1. */
    public static final int FACTORIAL_BASE_CASE = 1;

    private final List<String> recursionSteps;

    /**
     * Creates a new {@code FactorialCalculator} instance.
     */
    public FactorialCalculator() {
        this.recursionSteps = new ArrayList<>();
    }

    /**
     * Calculates the factorial of a non-negative integer using recursion.
     *
     * @param number the non-negative integer
     * @return the factorial of {@code number}
     * @throws IllegalArgumentException if {@code number} is negative
     */
    public BigInteger calculateFactorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }

        recursionSteps.clear();
        return factorialRecursive(number);
    }

    /**
     * Recursively computes factorial and records each step for display.
     *
     * @param number the current value in the recursion
     * @return factorial of {@code number}
     */
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

    /**
     * Returns an unmodifiable view of the recursion steps from the last calculation.
     *
     * @return list of recursion step descriptions
     */
    public List<String> getRecursionSteps() {
        return Collections.unmodifiableList(recursionSteps);
    }

    /**
     * Checks whether recursion steps are available from a prior calculation.
     *
     * @return {@code true} if steps exist; {@code false} otherwise
     */
    public boolean hasRecursionSteps() {
        return !recursionSteps.isEmpty();
    }
}
