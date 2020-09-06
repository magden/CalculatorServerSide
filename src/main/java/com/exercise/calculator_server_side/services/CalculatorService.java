package com.exercise.calculator_server_side.services;

/**
 * Performs calculation.
 */
public interface CalculatorService {

    /**
     * Performs calculation of the received arithmetic expression
     *
     * @param calculation the arithmetic expression to calculate
     * @return the result, or error cause
     */
    String calculate(String calculation);
}
