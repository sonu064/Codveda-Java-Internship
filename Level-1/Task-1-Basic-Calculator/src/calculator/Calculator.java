package calculator;

/**
 * Provides reusable arithmetic operations for the Basic Calculator application.
 * <p>
 * This class contains pure business logic with no console I/O or input validation.
 * All methods operate on {@code double} values and return computed results.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class Calculator {

    private static final double PERCENTAGE_DIVISOR = 100.0;

    /**
     * Default constructor. Creates a new {@code Calculator} instance.
     */
    public Calculator() {
        // Stateless service — constructor provided for explicit initialization in Main.
    }

    /**
     * Adds two numbers.
     *
     * @param firstOperand  the first number
     * @param secondOperand the second number
     * @return the sum of {@code firstOperand} and {@code secondOperand}
     */
    public double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    /**
     * Subtracts the second number from the first.
     *
     * @param firstOperand  the minuend
     * @param secondOperand the subtrahend
     * @return the difference of {@code firstOperand} and {@code secondOperand}
     */
    public double subtract(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    /**
     * Multiplies two numbers.
     *
     * @param firstOperand  the first factor
     * @param secondOperand the second factor
     * @return the product of {@code firstOperand} and {@code secondOperand}
     */
    public double multiply(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }

    /**
     * Divides the first number by the second.
     *
     * @param dividend the number to be divided
     * @param divisor  the number to divide by
     * @return the quotient of {@code dividend} divided by {@code divisor}
     * @throws ArithmeticException if {@code divisor} is zero
     */
    public double divide(double dividend, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return dividend / divisor;
    }

    /**
     * Computes the remainder of integer division semantics for floating-point operands.
     *
     * @param dividend the number to be divided
     * @param divisor  the divisor
     * @return the remainder of {@code dividend} modulo {@code divisor}
     * @throws ArithmeticException if {@code divisor} is zero
     */
    public double modulus(double dividend, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Modulus by zero is not allowed.");
        }
        return dividend % divisor;
    }

    /**
     * Raises a base number to the power of an exponent.
     *
     * @param base     the base value
     * @param exponent the exponent value
     * @return {@code base} raised to the power of {@code exponent}
     */
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Computes the square root of a non-negative number.
     *
     * @param number the non-negative number
     * @return the square root of {@code number}
     * @throws ArithmeticException if {@code number} is negative
     */
    public double squareRoot(double number) {
        if (number < 0.0) {
            throw new ArithmeticException("Square root of a negative number is not allowed.");
        }
        return Math.sqrt(number);
    }

    /**
     * Calculates a percentage of a given value.
     * <p>
     * Example: {@code percentage(200, 15)} returns {@code 30.0} (15% of 200).
     * </p>
     *
     * @param value      the base value
     * @param percentage the percentage to apply
     * @return the computed percentage amount
     */
    public double percentage(double value, double percentage) {
        return value * (percentage / PERCENTAGE_DIVISOR);
    }
}
