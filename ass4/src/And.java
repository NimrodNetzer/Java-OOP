// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class to represent the logical AND operation between two expressions.
 */
public class And extends BinaryExpression {
    private final Expression expressionOne;
    private final Expression expressionTwo;

    /**
     * Constructs an AND expression with two sub-expressions.
     *
     * @param expressionOne The first sub-expression.
     * @param expressionTwo The second sub-expression.
     */
    public And(Expression expressionOne, Expression expressionTwo) {
        super(expressionOne, expressionTwo);
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Returns the type of this logical operation.
     *
     * @return The string "AND".
     */
    public String getType() {
        return "AND";
    }

    /**
     * Evaluates the AND expression based on variable assignments.
     *
     * @param assignment A map of variable names to their boolean values.
     * @return The result of evaluating the AND expression with the given assignments.
     * @throws Exception If there's an error evaluating either sub-expression.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return expressionOne.evaluate(assignment) && expressionTwo.evaluate(assignment);
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * Retrieves all unique variable names present in the AND expression.
     *
     * @return A list of unique variable names used in the expression.
     */
    public List<String> getVariables() {
        return Stream.of(expressionOne.getVariables(), expressionTwo.getVariables())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns a string representation of the AND expression.
     *
     * @return A string representing the AND expression in the format "(expressionOne & expressionTwo)".
     */
    @Override
    public String toString() {
        return "(" + expressionOne.toString() + " & " + expressionTwo.toString() + ")";
    }

    /**
     * Creates a new AND expression where occurrences of a variable are replaced with a given expression.
     *
     * @param var        The variable to be replaced.
     * @param expression The expression to replace the variable with.
     * @return A new AND expression with the variable replaced by the provided expression.
     */
    public Expression assign(String var, Expression expression) {
        return new And(expressionOne.assign(var, expression), expressionTwo.assign(var, expression));
    }

    /**
     * Converts the AND expression to an equivalent expression using only NAND operations.
     *
     * @return An expression tree using only NAND operations equivalent to the original AND expression.
     */
    public Expression nandify() {
        Expression nandifyExpOne = expressionOne.nandify();
        Expression nandifyExpTwo = expressionTwo.nandify();
        return new Nand(
                new Nand(nandifyExpOne, nandifyExpTwo),
                new Nand(nandifyExpOne, nandifyExpTwo)
        );
    }

    /**
     * Converts the AND expression to an equivalent expression using only NOR operations.
     *
     * @return An expression tree using only NOR operations equivalent to the original AND expression.
     */
    @Override
    public Expression norify() {
        Expression norifyExpOne = expressionOne.norify();
        Expression norifyExpTwo = expressionTwo.norify();
        return new Nor(
                new Nor(norifyExpOne, norifyExpOne),
                new Nor(norifyExpTwo, norifyExpTwo)
        );
    }

    /**
     * Simplifies the AND expression by applying logical simplification rules.
     *
     * @return A simplified version of the AND expression.
     */
    public Expression simplify() {
        Expression firstSimplifiedExp = expressionOne.simplify();
        Expression secondSimplifiedExp = expressionTwo.simplify();

        // Apply simplification rules
        if (isEqual(firstSimplifiedExp, new Val(true))) {
            return secondSimplifiedExp; // x & true = x
        } else if (isEqual(secondSimplifiedExp, new Val(true))) {
            return firstSimplifiedExp; // true & x = x
        } else if (isEqual(firstSimplifiedExp, new Val(false))) {
            return new Val(false); // false & x = false
        } else if (isEqual(secondSimplifiedExp, new Val(false))) {
            return new Val(false); // x & false = false
        } else if (isEqual(firstSimplifiedExp, secondSimplifiedExp)) {
            return firstSimplifiedExp; // x & x = x
        }

        // Default: return a new AND expression with simplified operands
        return new And(firstSimplifiedExp, secondSimplifiedExp);
    }
}
