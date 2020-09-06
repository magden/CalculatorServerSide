package com.exercise.calculator_server_side.services;


import com.exercise.calculator_server_side.container.ArithmeticExpressionContainer;
import com.exercise.calculator_server_side.supported_operations.ArType;
import com.exercise.calculator_server_side.supported_operations.ArithmeticOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that responsible for calculation of arithmetic expressions. Holds Map with possible arithmetic
 * operations components.
 * Each component performs specific operation, in order to add new operation, enough just to create new object that
 * implements an ArithmeticOperation.
 */
@Service("CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {

    final Logger logger = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    /*** Map that contains supported arithmetic operations components*/
    private final Map<ArType, ArithmeticOperation> operationsMap = new HashMap<>();


    /**
     * Creates new service. Spring framework injects all objects that implements ArithmeticOperation interface.
     *
     * @param arithmeticOperations Possible Arithmetic operations that injected by Spring framework and puts to
     *                             the operations map (all operations that app supports).
     */
    public CalculatorServiceImpl(List<ArithmeticOperation> arithmeticOperations) {
        arithmeticOperations.forEach(arOp -> operationsMap.put(arOp.getType(), arOp));
    }

    /**
     * By given input creates "ArithmeticExpressionContainer" that contains operators and operands in insertion
     * order. From this container takes in fifo method operation type and rejects( if supports) from operations
     * map appropriate arithmetic operation( bean). Also takes operands in fifo method. Finally operation
     * performs calculation on operands.
     *
     * @param input the input from client
     * @return the result after calculation or a failure reason
     */
    @Override
    public String calculate(String input) {
        try {
            logger.info("Received arithmetic expression: " + input);
            ArithmeticExpressionContainer arithmeticExpressionContainer = new ArithmeticExpressionContainer(input);
            validateArithmeticExpression(arithmeticExpressionContainer);
            //supports only one operation, for a calculation 2 and more should to loop over the operators queue
            //and operate them with numbers from operands queue and last result
            ArType arType = arithmeticExpressionContainer.removeNextOperator();
            Float operand1 = arithmeticExpressionContainer.removeNextOperand();
            Float operand2 = arithmeticExpressionContainer.removeNextOperand();
            ArithmeticOperation arOperation = operationsMap.get(arType);
            if (arOperation != null) {
                String result = arOperation.operate(operand1, operand2);
                logger.info("Result after calculation: " + operand1 + "" + arType.toString()
                        + "" + operand2 + "=" + result);
                return result;
            } else {
                String failureMsg = "Operation '" + arType.toString() + "' is not " + "supported.";
                logger.error(failureMsg);
                return failureMsg;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * @param arithmeticExpressionContainer the arithmetic expression to validate
     */
    private void validateArithmeticExpression(ArithmeticExpressionContainer arithmeticExpressionContainer) {
        logger.info("Preparing to calculate arithmetic expression with operands queue:\n " +
                arithmeticExpressionContainer.getOperandsQueue() + "\nAnd operations queue:\n" + arithmeticExpressionContainer.getOperatorsQueue());
        //supports only one operation
        if (arithmeticExpressionContainer.getOperatorsQueue().size() > 1) {
            logger.error("Supports only one operation.");
            throw new RuntimeException("Supports only one operation.");
        }
        if (arithmeticExpressionContainer.getOperandsQueue().size() < 2) {
            logger.error("Expression must contain at least 2 operands.");
            throw new RuntimeException("Expression must contain at least 2 operands.");
        }
    }

}
