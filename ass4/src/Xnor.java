// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a logical expression XNOR (exclusive NOR).
 */
public class Xnor extends BinaryExpression {
    private final Expression expressionOne;
    private final Expression expressionTwo;

    /**
     * Constructs Xnor object with two sub-expressions.
     *
     * @param expressionOne The first sub-expression.
     * @param expressionTwo The second sub-expression.
     */
    public Xnor(Expression expressionOne, Expression expressionTwo) {
        super(expressionOne, expressionTwo);
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Returns the type identifier for this expression (XNOR).
     *
     * @return The type identifier "XNOR".
     */
    public String getType() {
        return "XNOR";
    }

    /**
     * Evaluates the XNOR expression using the variable values provided in the assignment.
     *
     * @param assignment A map of variable names to their boolean values.
     * @return The evaluated boolean result of the XNOR operation.
     * @throws Exception If an error occurs during evaluation.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return ((expressionOne.evaluate(assignment)) == (expressionTwo.evaluate(assignment)));
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * Returns a list of variables present in the XNOR expression.
     *
     * @return A list of variable names.
     */
    public List<String> getVariables() {
        return Stream.of(expressionOne.getVariables(), expressionTwo.getVariables())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns a string representation of the XNOR expression.
     *
     * @return A string representing the XNOR expression.
     */
    public String toString() {
        return "(" + expressionOne.toString() + " # " + expressionTwo.toString() + ")";
    }

    /**
     * Returns a new expression where occurrences of a specific variable are replaced with the provided expression.
     *
     * @param var        The variable name to replace.
     * @param expression The expression to replace with.
     * @return A new XNOR expression with substitutions.
     */
    public Expression assign(String var, Expression expression) {
        return new Xnor(expressionOne.assign(var, expression), expressionTwo.assign(var, expression));
    }

    /**
     * Converts the XNOR expression to an equivalent expression using only NAND operations.
     *
     * @return An equivalent expression using only NAND operations.
     */
    public Expression nandify() {
        Expression nandifyExpOne = expressionOne.nandify();
        Expression nandifyExpTwo = expressionTwo.nandify();
        return new Nand(
                new Nand(new Nand(nandifyExpOne, nandifyExpOne), new Nand(nandifyExpTwo, nandifyExpTwo)),
                new Nand(nandifyExpOne, nandifyExpTwo)
        );
    }

    /**
     * Converts the XNOR expression to an equivalent expression using only NOR operations.
     *
     * @return An equivalent expression using only NOR operations.
     */
    public Expression norify() {
        Expression norifyExpOne = expressionOne.norify();
        Expression norifyExpTwo = expressionTwo.norify();
        return new Nor(
                new Nor(norifyExpOne, new Nor(norifyExpOne, norifyExpTwo)),
                new Nor(norifyExpTwo, new Nor(norifyExpOne, norifyExpTwo)));
    }

    /**
     * Simplifies the XNOR expression.
     *
     * @return A simplified version of the current XNOR expression.
     */
    public Expression simplify() {
        Expression simplifiedOne = expressionOne.simplify();
        Expression simplifiedTwo = expressionTwo.simplify();

        // Simplification rules for XNOR
        if (simplifiedOne.equals(simplifiedTwo)) {
            return new Val(true); // If both sides are identical, XNOR simplifies to true
        }
        return new Xnor(simplifiedOne, simplifiedTwo);
    }
}
