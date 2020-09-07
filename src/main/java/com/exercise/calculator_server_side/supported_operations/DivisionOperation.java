package com.exercise.calculator_server_side.supported_operations;


import org.springframework.stereotype.Component;

/**
 * Object that operates division of firstNumber by secondNumber.
 */
@Component
public class DivisionOperation implements ArithmeticOperation {
    /**
     * Operates division of firstNumber by secondNumber
     *
     * @param firstNumber  the first number to operate
     * @param secondNumber the second number to operate
     * @return division of firstNumber by secondNumber
     */
    @Override
    public String operate(Float firstNumber, Float secondNumber) {
        if (secondNumber == 0) {
            throw new ArithmeticException("Dividing by zero is undefined.");
        }
        float result = firstNumber / secondNumber;
        return getConvertedResult(result);
    }


    @Override
    public ArType getType() {
        return ArType.DIV;
    }
}
