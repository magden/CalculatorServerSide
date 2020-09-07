package com.exercise.calculator_server_side.supported_operations;

import org.springframework.stereotype.Component;

/**
 * Object that operates subtraction operation of second number from first number.
 */
@Component
public class SubtractionOperation implements ArithmeticOperation {
    /**
     * Operates subtraction of second number from first number
     *
     * @param firstNumber  the first number to operate
     * @param secondNumber the second number to operate
     * @return subtraction of second number from first number
     */
    @Override
    public String operate(Float firstNumber, Float secondNumber) {
        float result = firstNumber - secondNumber;
        return getConvertedResult(result);
    }

    @Override
    public ArType getType() {
        return ArType.SUB;
    }
}
