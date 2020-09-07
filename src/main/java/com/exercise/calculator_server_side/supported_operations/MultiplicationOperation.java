package com.exercise.calculator_server_side.supported_operations;


import org.springframework.stereotype.Component;

/**
 * Object that operates multiplication between two numbers.
 */
@Component
public class MultiplicationOperation implements ArithmeticOperation {
    /**
     * Operates multiplication of first number and second number
     *
     * @param firstNumber  the first number to operate
     * @param secondNumber the second number to operate
     * @return multiplication of first number and second number
     */
    @Override
    public String operate(Float firstNumber, Float secondNumber) {
        float result = firstNumber * secondNumber;
        return getConvertedResult(result);
    }

    @Override
    public ArType getType() {
        return ArType.MUL;
    }
}
