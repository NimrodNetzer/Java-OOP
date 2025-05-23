// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class to represent the NAND logic expression.
 */
public class Nand extends BinaryExpression {
    private final Expression expressionOne;
    private final Expression expressionTwo;

    /**
     * Constructs a NAND expression with two sub-expressions.
     *
     * @param expressionOne The first sub-expression of the NAND operation.
     * @param expressionTwo The second sub-expression of the NAND operation.
     */
    public Nand(Expression expressionOne, Expression expressionTwo) {
        super(expressionOne, expressionTwo);
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Returns the type of this expression, which is "NAND".
     *
     * @return The string "NAND".
     */
    public String getType() {
        return "NAND";
    }

    /**
     * Evaluates the NAND expression using the variable values provided in the assignment.
     * Computes !(expressionOne && expressionTwo).
     *
     * @param assignment A map containing variable names and their boolean values.
     * @return The result of evaluating the NAND expression with the given assignment.
     * @throws Exception If the evaluation encounters an error or if variables are not properly assigned.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return !((expressionOne.evaluate(assignment)) && (expressionTwo.evaluate(assignment)));
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * Returns a list of the variables present in this NAND expression.
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
     * Returns a string representation of the NAND expression.
     * Format: "(expressionOne A expressionTwo)".
     *
     * @return A string representation of the NAND expression.
     */
    public String toString() {
        if (expressionOne == null || expressionTwo == null) {
            return "null exception";
        }
        return "(" + expressionOne.toString() + " A " + expressionTwo.toString() + ")";
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
     * the logical NAND operation. For NAND, returns itself.
     *
     * @return This NAND expression.
     */
    public Expression nandify() {
        return this;
    }

    /**
     * Returns the expression tree resulting from converting all operations to
     * the logical NOR operation.
     *
     * @return A NOR expression equivalent to this NAND expression.
     */
    public Expression norify() {
        Expression norifyExpOne = expressionOne.norify();
        Expression norifyExpTwo = expressionTwo.norify();
        return new Nor(
                new Nor(new Nor(norifyExpOne, norifyExpOne), new Nor(norifyExpTwo, norifyExpTwo)),
                new Nor(new Nor(norifyExpOne, norifyExpOne), new Nor(norifyExpTwo, norifyExpTwo)));
    }

    /**
     * Returns a simplified version of the NAND expression.
     *
     * @return A simplified NAND expression.
     */
    public Expression simplify() {
        Expression simplifiedOne = expressionOne.simplify();
        Expression simplifiedTwo = expressionTwo.simplify();

        // Apply simplification rules
        if (isEqual(simplifiedOne, new Val(true))) {
            return new Not(simplifiedTwo); // True A x = ~x
        } else if (isEqual(simplifiedTwo, new Val(true))) {
            return new Not(simplifiedOne); // x A true = ~x
        } else if (isEqual(simplifiedOne, new Val(false))) {
            return new Val(true); // False A x = True
        } else if (isEqual(simplifiedTwo, new Val(false))) {
            return new Val(true); // x A False = True
        } else if (isEqual(simplifiedOne, simplifiedTwo)) {
            return new Not(simplifiedOne); // x A x = ~x
        }
        // default: return a new NAND expression with simplified operands
        return new Nand(simplifiedOne, simplifiedTwo);
    }
}
