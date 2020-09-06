package com.exercise.calculator_server_side.supportde_operations;

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
}
