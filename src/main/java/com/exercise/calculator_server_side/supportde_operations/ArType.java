package com.exercise.calculator_server_side.supportde_operations;

/**
 * Supported arithmetic operations.
 */
public enum ArType {
    SUB("-"), ADD("+"), MUL("*"), DIV("/");

    private final String op;

    ArType(String op) {
        this.op = op;
    }

    /**
     * @param str string that describes arithmetic operation
     * @return the type of the arithmetic operation
     * @throws RuntimeException if operation is not supported
     */
    public static ArType fromString(String str) {
        for (ArType arType : values()) {
            if (arType.toString().equals(str)) {
                return arType;
            }
        }
        throw new RuntimeException("'" + str + "' arithmetic operation is not supported.");
    }

    @Override
    public String toString() {
        return op;
    }

    String getOp() {
        if (op == null) {
            throw new ArithmeticException("Wrong operator.");
        }
        return op;
    }

    public char getChar() {
        return op.charAt(0);
    }
}
