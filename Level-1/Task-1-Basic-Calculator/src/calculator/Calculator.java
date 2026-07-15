package calculator;

public class Calculator {

    private static final double PERCENTAGE_DIVISOR = 100.0;


    public Calculator() {
    }
    public double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    public double subtract(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    public double multiply(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }

    public double divide(double dividend, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return dividend / divisor;
    }

    public double modulus(double dividend, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Modulus by zero is not allowed.");
        }
        return dividend % divisor;
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    public double squareRoot(double number) {
        if (number < 0.0) {
            throw new ArithmeticException("Square root of a negative number is not allowed.");
        }
        return Math.sqrt(number);
    }
    public double percentage(double value, double percentage) {
        return value * (percentage / PERCENTAGE_DIVISOR);
    }
}
