package com.exercise.calculator_server_side.supported_operations;


import org.springframework.stereotype.Component;

/**
 * Object that operates addition operation of numbers.
 */
@Component
public class AdditionOperation implements ArithmeticOperation {
    /**
     * Adds two numbers.
     *
     * @param firstNumber  the first number to operate
     * @param secondNumber the second number to operate
     * @return sum of two numbers
     */
    @Override
    public String operate(Float firstNumber, Float secondNumber) {
        float result = firstNumber + secondNumber;
        return getConvertedResult(result);
    }

    @Override
    public ArType getType() {
        return ArType.ADD;
    }
}

