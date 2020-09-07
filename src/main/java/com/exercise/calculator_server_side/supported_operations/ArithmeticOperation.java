package com.exercise.calculator_server_side.supported_operations;

/**
 * The object that implements this interface could be specific arithmetic operation.
 */
public interface ArithmeticOperation {
    /**
     * Performs arithmetic operation.
     *
     * @param firstNumber  the first number to operate
     * @param secondNumber the second number to operate
     * @return the result
     */
    String operate(Float firstNumber, Float secondNumber);

    /**
     * @return the type of this operation
     */
    ArType getType();

    /**
     * Converts result to a correct format
     *
     * @param result the result after calculation
     * @return result converted to the String format
     */
    default String getConvertedResult(float result) {
        if ((int) result == result) {
            //if number has only zeros after the floating point
            return String.valueOf((int) result);
        } else {
            return String.valueOf(result);
        }
    }
}
