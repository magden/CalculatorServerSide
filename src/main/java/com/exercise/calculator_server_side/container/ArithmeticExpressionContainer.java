package com.exercise.calculator_server_side.container;

import com.exercise.calculator_server_side.supportde_operations.ArType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A container designed for holding an arithmetic expression's elements, that converted from the input String.
 * Contains the queue of the valid operands and queue of the valid operators that extracted from the input.
 */
public class ArithmeticExpressionContainer {

    final Logger logger = LoggerFactory.getLogger(ArithmeticExpressionContainer.class);

    private final Queue<Float> operands = new LinkedList<>();
    private final Queue<ArType> operators = new LinkedList<>();
    private String input = "";

    /**
     * Initializes a newly created object from input string to ArithmeticExpressionContainer.
     *
     * @param input the input to extract elements
     * @throws RuntimeException if failed convert input string to ArithmeticExpressionContainer element
     */
    public ArithmeticExpressionContainer(String input) {
        setInput(input);
        extractDataFromInput();
    }

    /**
     * Extract arithmetic expression's elements from the input String and puts them to the appropriate data
     * structures. Scans from the first character until the end in this logic: first element must be number, second
     * arithmetic operation, third number, fourth arithmetic operation, etc. Pointers(" elementStart" and
     * "nextIndex") help to define where operand/operation starts and ends.
     * Example: input - "1+2-3". Add '1' to operands queue, add '+' to operation queue, add '2' to operands
     * queue, add '-' to operation queue add '3' to operands queue.
     *
     * @throws RuntimeException if some property of input String
     *                          has a wrong format of arithmetic expression
     */
    private void extractDataFromInput() {
        validateInput();
        int elementStart = 0;
        int nextIndex = 1;
        //"clean"- white spaces less
        String cleanInput = input.replaceAll("\\s+", "");
        boolean firstIteration = true;
        while (nextIndex < cleanInput.length()) {
            //correct template of arithmetic expression is: 'operand,operation,operand,operation, etc...'
            // first iteration add to: operand, operation, operand. After that: operation, operand, operation,
            // operand, etc...
            if (firstIteration) {
                //add to operands queue
                nextIndex = addOperandAndReturnNextIndex(elementStart, nextIndex, cleanInput);
            }
            firstIteration = false;
            //add to operation queue
            nextIndex = addOperationAndReturnNextIndex(nextIndex, cleanInput);
            elementStart = nextIndex;
            nextIndex++;
            if (nextIndex > cleanInput.length()) {
                throw new RuntimeException("After an arithmetic operation '" + this.operators.peek() + "' must " +
                        "be operand.");
            }
            //add to operands queue
            nextIndex = addOperandAndReturnNextIndex(elementStart, nextIndex, cleanInput);
        }
    }


    /**
     * Extract operation from the input, and puts him to the operands data structure.
     *
     * @param nextIndex  index that points to char that could be potential operation
     * @param cleanInput the clean input from the user
     * @return next index after the operation character
     */
    private int addOperationAndReturnNextIndex(int nextIndex, String cleanInput) {
        if (nextIndex >= cleanInput.length()) {
            throw new RuntimeException("After an operand '" + this.operands.peek() + "' must be arithmetic " +
                    "operation.");
        }
        //adds next character after the number( supposed to be operator) to the operators queue
        String operation = String.valueOf(cleanInput.charAt(nextIndex));
        operators.add(ArType.fromString(operation));
        //after adding operator to the queue will check next char to find next operand
        nextIndex++;
        return nextIndex;
    }

    /**
     * Extract operand from the input and puts him to the operands data structure.
     * By parsing each character from the start index until char is not part of the number, after that adds this
     * number to operands data structure.
     *
     * @param start      the first index that points to char that could be potential first char of the number
     * @param nextIndex  the pointer that runs over the input string until char is not part of the number
     * @param cleanInput the  clean input from user
     * @return index that points to next char that is not part of number
     */
    private int addOperandAndReturnNextIndex(int start, int nextIndex, String cleanInput) {
        //if operand is negative number
        if (cleanInput.charAt(start) == '-') {
            nextIndex++;
            if (nextIndex > cleanInput.length()) {
                throw new RuntimeException("Wrong expression, after '-' must be number.");
            }
        }
        String numberRegex = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
        //looping over all characters until next character isn't part of the number, or input string was end
        while (nextIndex <= cleanInput.length() && cleanInput.substring(start, nextIndex).matches(numberRegex)) {
            nextIndex++;
        }
        nextIndex--;
        try {
            Float operand = Float.parseFloat(cleanInput.substring(start, nextIndex));
            operands.add(operand);
            return nextIndex;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException("Failed convert '" + cleanInput.substring(start, nextIndex + 1) + "' to  " +
                    "number.");
        }
    }

    /**
     * Validates if input is correct.
     */
    private void validateInput() {
        if (input.length() < 3) {
            throw new RuntimeException("Wrong arithmetic expression, correct format 'operand1 operation " +
                    "operand2' ");
        }
    }

    /**
     * @return Oldest arithmetic operator's type from the operators queue
     */
    public ArType removeNextOperator() {
        return operators.remove();
    }

    /**
     * @return Oldest arithmetic operand from the operators queue
     */
    public Float removeNextOperand() {
        return operands.remove();
    }

    /**
     * @return the operators queue
     */
    public Queue<ArType> getOperatorsQueue() {
        return operators;
    }

    /**
     * @param input the input String
     */
    private void setInput(String input) {
        this.input = input;
    }

    /**
     * @return the operands queue
     */
    public Queue<Float> getOperandsQueue() {
        return operands;
    }

}

