// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class to represent the logical expression NOR.
 */
public class Nor extends BinaryExpression {
    private final Expression expressionOne;
    private final Expression expressionTwo;

    /**
     * Constructs a NOR expression with two sub-expressions.
     *
     * @param expressionOne The first sub-expression of the NOR operation.
     * @param expressionTwo The second sub-expression of the NOR operation.
     */
    public Nor(Expression expressionOne, Expression expressionTwo) {
        super(expressionOne, expressionTwo);
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Returns the type of this expression, which is "NOR".
     *
     * @return The string "NOR".
     */
    public String getType() {
        return "NOR";
    }

    /**
     * Evaluates the NOR expression using the variable values provided in the assignment.
     * Computes !(expressionOne || expressionTwo).
     *
     * @param assignment A map containing variable names and their boolean values.
     * @return The result of evaluating the NOR expression with the given assignment.
     * @throws Exception If the evaluation encounters an error or if variables are not properly assigned.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return !((expressionOne.evaluate(assignment)) || (expressionTwo.evaluate(assignment)));
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * Returns a list of the variables present in this NOR expression.
     *
     * @return A list of variable names as strings.
     */
    public List<String> getVariables() {
        return Stream.of(expressionOne.getVariables(), expressionTwo.getVariables())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns a string representation of the NOR expression.
     * Format: "(expressionOne V expressionTwo)".
     *
     * @return A string representation of the NOR expression.
     */
    public String toString() {
        if (expressionOne == null || expressionTwo == null) {
            return "null exception";
        }
        return "(" + expressionOne + " V " + expressionTwo + ")";
    }

    /**
     * Creates a new expression in which all occurrences of the variable "var"
     * are replaced with the provided expression.
     *
     * @param var        The variable name to be replaced.
     * @param expression The expression to replace the variable with.
     * @return A new expression with the variable replaced.
     */
    public Expression assign(String var, Expression expression) {
        return new Xnor(expressionOne.assign(var, expression), expressionTwo.assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from converting all operations to
     * the logical NAND operation.
     *
     * @return A NAND expression equivalent to this NOR expression.
     */
    public Expression nandify() {
        Expression nandifyExpOne = expressionOne.nandify();
        Expression nandifyExpTwo = expressionTwo.nandify();
        return new Nand(
                new Nand((new Nand(nandifyExpOne, nandifyExpOne)), new Nand(nandifyExpTwo, nandifyExpTwo)),
                new Nand((new Nand(nandifyExpOne, nandifyExpOne)), new Nand(nandifyExpTwo, nandifyExpTwo))
        );
    }

    /**
     * Returns the expression tree resulting from converting all operations to
     * the logical NOR operation. For NOR, returns itself.
     *
     * @return This NOR expression.
     */
    public Expression norify() {
        return this;
    }

    /**
     * Returns a simplified version of the NOR expression.
     *
     * @return A simplified NOR expression.
     */
    @Override
    public Expression simplify() {
        Expression simplifiedExpOne = expressionOne.simplify();
        Expression simplifiedExpTwo = expressionTwo.simplify();

        if (isEqual(simplifiedExpOne, new Val(true))) {
            return new Val(false); // True V x = false
        } else if (isEqual(simplifiedExpTwo, new Val(true))) {
            return new Val(false); // x V true = false
        } else if (isEqual(simplifiedExpOne, new Val(false))) {
            return new Not(simplifiedExpTwo); // False V x = ~(x)
        } else if (isEqual(simplifiedExpTwo, new Val(false))) {
            return new Not(simplifiedExpOne); // x V False = ~(x)
        } else if (isEqual(simplifiedExpOne, simplifiedExpTwo)) {
            return new Not(simplifiedExpOne); // x V x = ~(x)
        }
        return new Nor(simplifiedExpOne, simplifiedExpTwo);
    }
}
